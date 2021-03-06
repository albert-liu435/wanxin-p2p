package cn.itcast.wanxinp2p.transaction.service;

import cn.itcast.wanxinp2p.common.domain.*;
import cn.itcast.wanxinp2p.common.util.CodeNoUtil;
import cn.itcast.wanxinp2p.common.util.CommonUtil;
import cn.itcast.wanxinp2p.consumer.model.ConsumerDTO;
import cn.itcast.wanxinp2p.transaction.agent.ConsumerApiAgent;
import cn.itcast.wanxinp2p.transaction.agent.ContentSearchApiAgent;
import cn.itcast.wanxinp2p.transaction.agent.DepositoryAgentApiAgent;
import cn.itcast.wanxinp2p.transaction.common.constant.TransactionErrorCode;
import cn.itcast.wanxinp2p.transaction.common.utils.SecurityUtil;
import cn.itcast.wanxinp2p.transaction.entity.Project;
import cn.itcast.wanxinp2p.transaction.entity.Tender;
import cn.itcast.wanxinp2p.transaction.mapper.ProjectMapper;
import cn.itcast.wanxinp2p.transaction.mapper.TenderMapper;
import cn.itcast.wanxinp2p.transaction.model.ProjectDTO;
import cn.itcast.wanxinp2p.transaction.model.ProjectQueryDTO;
import cn.itcast.wanxinp2p.transaction.model.TenderOverviewDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : xsh
 * @create : 2021-01-22 - 1:42
 * @describe:
 */
@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    private ConsumerApiAgent consumerApiAgent;
    @Autowired
    private ConfigService configService;
    @Autowired
    private DepositoryAgentApiAgent depositoryAgentApiAgent;
    @Autowired
    private ContentSearchApiAgent contentSearchApiAgent;
    @Autowired
    private TenderMapper tenderMapper;

    /**
     * 创建标的
     *
     * @param projectDTO
     * @return
     */
    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        RestResponse<ConsumerDTO> restResponse = consumerApiAgent.getCurrConsumer(SecurityUtil.getUser().getMobile());
        // 设置用户编码
        projectDTO.setUserNo(restResponse.getResult().getUserNo());
        // 设置用户id
        projectDTO.setConsumerId(restResponse.getResult().getId());
        // 生成标的编码
        projectDTO.setProjectNo(CodeNoUtil.getNo(CodePrefixCode.CODE_PROJECT_PREFIX));
        // 标的状态修改
        projectDTO.setProjectStatus(ProjectCode.COLLECTING.getCode());
        // 标的可用状态修改, 未同步
        projectDTO.setStatus(StatusCode.STATUS_OUT.getCode());
        // 设置标的创建时间
        projectDTO.setCreateDate(LocalDateTime.now());
        // 设置还款方式
        projectDTO.setRepaymentWay(RepaymentWayCode.FIXED_REPAYMENT.getCode());
        // 设置标的类型
        projectDTO.setType("NEW");
        Project project = convertProjectDTOToEntity(projectDTO);

        project.setBorrowerAnnualRate(configService.getBorrowerAnnualRate());
        project.setAnnualRate(configService.getAnnualRate());
        // 年化利率(平台佣金，利差)
        project.setCommissionAnnualRate(configService.getCommissionAnnualRate());
        // 债权转让
        project.setIsAssignment(0);
        // 设置标的名字, 姓名+性别+第N次借款
        // 判断男女
        String sex = Integer.parseInt(restResponse.getResult().getIdNumber()
                .substring(16, 17)) % 2 == 0 ? "女士" : "先生";
        // 构造借款次数查询条件
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Project::getConsumerId,
                restResponse.getResult().getId());
        project.setName(restResponse.getResult().getFullname() + sex
                + "第" + (count(queryWrapper) + 1) + "次借款");
        save(project);
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        return projectDTO;
    }

    /**
     * 根据分页条件检索标的信息
     *
     * @param projectQueryDTO
     * @param order
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    @Override
    public PageVO<ProjectDTO> queryProjectsByQueryDTO(ProjectQueryDTO projectQueryDTO, String order, Integer pageNo, Integer pageSize, String sortBy) {

        //带条件
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        // 标的类型
        if (StringUtils.isNotBlank(projectQueryDTO.getType())) {
            queryWrapper.lambda().eq(Project::getType, projectQueryDTO.getType());
        } // 起止年化利率(投资人) -- 区间
        if (null != projectQueryDTO.getStartAnnualRate()) {
            queryWrapper.lambda().ge(Project::getAnnualRate,
                    projectQueryDTO.getStartAnnualRate());
        }
        if (null != projectQueryDTO.getEndAnnualRate()) {
            queryWrapper.lambda().le(Project::getAnnualRate,
                    projectQueryDTO.getStartAnnualRate());
        } // 借款期限 -- 区间
        if (null != projectQueryDTO.getStartPeriod()) {
            queryWrapper.lambda().ge(Project::getPeriod,
                    projectQueryDTO.getStartPeriod());
        }
        if (null != projectQueryDTO.getEndPeriod()) {
            queryWrapper.lambda().le(Project::getPeriod,
                    projectQueryDTO.getEndPeriod());
        } // 标的状态
        if (StringUtils.isNotBlank(projectQueryDTO.getProjectStatus())) {
            queryWrapper.lambda().eq(Project::getProjectStatus,
                    projectQueryDTO.getProjectStatus());
        }
        //分页
        // 构造分页对象
        Page<Project> page = new Page<>(pageNo, pageSize);

        //排序
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortBy)) {
            if (order.toLowerCase().equals("asc")) {
                queryWrapper.orderByAsc(sortBy);
            } else if (order.toLowerCase().equals("desc")) {
                queryWrapper.orderByDesc(sortBy);
            }
        } else {
            queryWrapper.lambda().orderByDesc(Project::getCreateDate);
        }

        //执行查询
        IPage<Project> iPage = page(page, queryWrapper);

        //封装结果
        List<ProjectDTO> projectDTOList = convertProjectEntityListToDTOList(iPage.getRecords());
        return new PageVO<>(projectDTOList, iPage.getTotal(), pageNo, pageSize);

    }

    /**
     * 管理员审核标的信息
     *
     * @param id
     * @param approveStatus
     * @return
     */
    @Override
    public String projectsApprovalStatus(Long id, String approveStatus) {
        //1.根据id查询标的信息并转换为DTO对象
        Project project = getById(id);
        ProjectDTO projectDTO = convertProjectEntityToDTO(project);
        //2.生成流水号(不存在才生成)
        if (StringUtils.isBlank(project.getRequestNo())) {
            projectDTO.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
            update(Wrappers.<Project>lambdaUpdate().set(Project::getRequestNo,
                    projectDTO.getRequestNo()).eq(Project::getId, id));
        }

        //3.通过feign远程访问存管代理服务，把标的信息传输过去
        RestResponse<String> restResponse = depositoryAgentApiAgent.createProject(projectDTO);

        if (DepositoryReturnCode.RETURN_CODE_00000.getCode()
                .equals(restResponse.getResult())) {
            //4.根据结果修改状态
            update(Wrappers.<Project>lambdaUpdate().set(Project::getStatus, Integer.parseInt(approveStatus)).eq(Project::getId, id));
            return "success";
        }

        //5.如果失败就抛异常
        throw new BusinessException(TransactionErrorCode.E_150113);
    }

    @Override
    public PageVO<ProjectDTO> queryProjects(ProjectQueryDTO projectQueryDTO, String order, Integer pageNo, Integer pageSize, String sortBy) {
        RestResponse<PageVO<ProjectDTO>> esResponse = contentSearchApiAgent.queryProjectIndex(projectQueryDTO, pageNo, pageSize, sortBy, order);
        if (!esResponse.isSuccessful()) {
            throw new BusinessException(CommonErrorCode.UNKOWN);
        }
        return esResponse.getResult();
    }

    /**
     * 通过ids获取多个标的
     *
     * @param ids
     * @return
     */
    @Override
    public List<ProjectDTO> queryProjectsIds(String ids) {
        //1. 查询标的信息
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        List<Long> list = new ArrayList<>();
        Arrays.asList(ids.split(",")).forEach(str -> {
            list.add(Long.parseLong(str));
        });
        queryWrapper.lambda().in(Project::getId, list); // .... where  id  in  (1,2,3,4,5)
        List<Project> projects = list(queryWrapper);
        List<ProjectDTO> dtos = new ArrayList<>();
        //2.转换为DTO对象
        for (Project project : projects) {
            ProjectDTO projectDTO = convertProjectEntityToDTO(project);
            // 3. 获取剩余额度
            projectDTO.setRemainingAmount(getProjectRemainingAmount(project));
            //4. 查询出借人数
            projectDTO.setTenderCount(
                    tenderMapper.selectCount(Wrappers.<Tender>lambdaQuery().eq(Tender::getProjectId, project.getId()))
            );
            dtos.add(projectDTO);
        }
        return dtos;
    }

    /**
     * 根据标的id查询投标记录
     *
     * @param id
     * @return
     */
    @Override
    public List<TenderOverviewDTO> queryTendersByProjectId(Long id) {
        List<Tender> tenderList = tenderMapper.selectList(Wrappers.<Tender>lambdaQuery().eq(Tender::getProjectId, id));
        List<TenderOverviewDTO> tenderOverviewDTOList = new ArrayList<>();
        tenderList.forEach(tender -> {
            TenderOverviewDTO tenderOverviewDTO = new TenderOverviewDTO();
            BeanUtils.copyProperties(tender, tenderOverviewDTO);
            tenderOverviewDTO.setConsumerUsername(CommonUtil.hiddenMobile(tenderOverviewDTO.getConsumerUsername()));
            tenderOverviewDTOList.add(tenderOverviewDTO);
        });
        return tenderOverviewDTOList;
    }

    private Project convertProjectDTOToEntity(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return null;
        }
        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);
        return project;
    }

    private List<ProjectDTO> convertProjectEntityListToDTOList(List<Project> projectList) {
        if (projectList == null) {
            return null;
        }
        List<ProjectDTO> dtoList = new ArrayList<>();
        projectList.forEach(project -> {
            ProjectDTO projectDTO = new ProjectDTO();
            BeanUtils.copyProperties(project, projectDTO);
            dtoList.add(projectDTO);
        });
        return dtoList;
    }

    private ProjectDTO convertProjectEntityToDTO(Project project) {
        if (project == null) {
            return null;
        }
        ProjectDTO projectDTO = new ProjectDTO();
        BeanUtils.copyProperties(project, projectDTO);
        return projectDTO;
    }

    /**
     * 获取标的剩余可投额度
     *
     * @param project
     * @return
     */
    private BigDecimal getProjectRemainingAmount(Project project) {
        // 根据标的id在投标表查询已投金额
        List<BigDecimal> decimalList =
                tenderMapper.selectAmountInvestedByProjectId(project.getId());
        // 求和结果集
        BigDecimal amountInvested = new BigDecimal("0.0");
        for (BigDecimal d : decimalList) {
            amountInvested = amountInvested.add(d);
        }
        // 得到剩余额度
        return project.getAmount().subtract(amountInvested);
    }
}
