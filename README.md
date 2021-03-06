## 1.1 项目背景

P2P金融又叫P2P信贷，P2P是 peer-to-peer 或 person-to-person 的简写，意思是个人对个人，P2P金融是指个人与个人间的小额借贷交易，一般需要借助电子商务专业网络平台帮助借贷双方确立借贷关系并完成相关交易手续。

目前，国家对P2P行业的监控与规范性控制越来越严格，出台了很多政策来对其专项整治，P2P平台之前所采用的“资金池模式”与“第三方支付托管”（见下文定义）已经不合规了，国家主张采用“银行存管模式”来规避P2P平台挪用借投人资金的风险，通过银行开发的“银行存管系统”管理投资者的资金，每位P2P平台用户在银行的存管系统内都会有一个独立账号，P2P平台来管理交易，做到资金和交易分开，让P2P平台不能接触到资金，就可以一定程度避免资金被挪用的风险。

> 什么是资金池模式？

此模式下，投资人利用第三方支付/银行的通道先把资金打到平台的银行账户，P2P的平台就池子一样，汇聚了投资人和借款人的资金，这个汇集资金的池子叫做资金池，是P2P平台方最容易跑路的模式。

> 什么是第三方支付托管模式？

此模式下，投资人/借款人除了要在P2P平台注册外，还要在第三方支付平台注册，也就是平台和第三方各有一套账户体系。经过第三方支付的资金托管后，由于资金沉淀发生在第三方支付在银行的备付金账户上，P2P平台运营方只能看到投资人/借款人账户余额的变化及债权匹配关系，不能像资金池那样擅自挪用投资人的钱，但是这里存在安全风险的是第三方支付机构。

> 什么是银行存管模式？

此种模式下，涉及到2套账户体系，P2P平台和银行各一套账户体系。投资人在P2P平台注册后，会同时跳转到银行再开一个电子账户，2个账户间有一一对应的关系。当投资人投资时，资金进入的是平台在银行为投资人开设的二级账户中，每一笔交易，是由银行在投资人与借款人间的交易划转，P2P平台仅能看到信息的流动。

![](http://cdn.xiongsihao.com/202012022315_822.png)

## 1.2 项目概述

万信金融是一款面向互联网大众提供的理财服务和个人消费信贷服务的金融平台，依托大数据风控技术，为用户提供方便、快捷、安心的P2P金融服务。本项目包括交易平台和业务支撑两个部分，交易平台主要实现理财服务，包括：借钱、出借等模块，业务支撑包括：标的管理、对账管理、风控管理等模块。项目采用先进的互联网技术进行研发，保证了P2P双方交易的安全性、快捷性及稳定性。

## 1.3 功能模块

![](http://cdn.xiongsihao.com/202012022317_81.png)

| 功能模块名称 | 功能说明                                                     |
| ------------ | ------------------------------------------------------------ |
| 首页         | 用于快速进入理财服务的入口。                                 |
| 借钱         | 借款人通过借钱模块完成发标操作，首先借款人需要进行身份认证、开户并填写借款 |
| 出借         | 出借人作为投资人从平台上选择要投资的标的进行投资，经过满标复审最终出借成 |
| 发现         | 专为会员提供优质服务，比如积分商城等。                       |
| 我的         | 为借款人和投资人提供个人中心，可快捷查询标的、债权等。       |
| 债权转让     | 债权人通过债权转让功能转让自己的债权。                       |
| 用户管理     | 统一管理B端用户的信息、权限等。                              |
| 对账管理     | 核对p2p平台和银行存管之间的账单是否一致。                    |
| 标的管理     | 对所有标的进行初审、复审等管理操作。                         |
| 风控管理     | 统一管理借款人的认证信息、信用信息等。                       |
| 会员管理     | 统一管理C端用户，包括：会员 信息、积分等。                   |
| 统计分析     | 通过大数据技术对借款、还款、逾期进行统计分析。               |

## 1.4 部分核心业务流程

![](http://cdn.xiongsihao.com/202012022324_592.png)

主要业务逻辑描述：

借款人在平台注册成功后，先要进行绑卡和实名认证来完成开户，然后平台会对借款人进行基础信用审核，审核通过后即可发起借款申请；

平台运营人员会审核借款信息，通过后投资人可以在平台进行充值 投资，待标的满标之后，平台进行满标复审后进行放款，借款人就可以拿到借款的款项，在到期还款日 ，当天平台会自动从借款人的账户中将应还本息划拨到投资人账户。



### 1) 开户业务流程

 开户是指借款用户和投资用户在注册后、交易前都需要在银行存管系统开通个人存管账户，在开户流程 中银行存管系统是一个很重要的系统，它是当前P2P平台最常见的一种模式，为了保证资金不流向P2P 平台，由银行存管系统去管理借款用户和投资用户的资金，P2P平台与银行存管系统进行接口交互为借 款用户和投资用户搭建交易的桥梁。

![](http://cdn.xiongsihao.com/202012022334_437.png)

### 2) 发标业务流程 

P2P行业习惯把平台里某个投资项目称为“标的”，简称“标”。一个标的一般至少包含：描述、借款用途、 借款总额、还款方式、借款利率、借款期限等基本信息。通俗来讲“标的”就是：借款人在P2P平台发起 的借款项目。“发标”就是：借款人在P2P平台申请借款。

![](http://cdn.xiongsihao.com/202012022335_727.png)

![](http://cdn.xiongsihao.com/202012022336_631.png)

1. 用户在前端填写借款信息 

2. 前端请求交易中心保存标的信息 

3. 管理员审核标的信息 

4. 交易中心请求存管代理服务生成交易记录（未同步），并对标的数据进行签名 

5. 存管代理服务携带标的签名数据请求银行存管系统 

6. 银行存管系统保存标的信息，返回同步成功给存管代理服务 

7. 存管代理服务更新交易记录，返回同步成功给交易中心 

8. 交易中心确认同步成功，更新标的信息

 

### 3) 充值业务流程 

用户通过银行存管系统把银行卡中的金额转入到万信金融P2P平台余额中，即为充值。投资人在投标前 需要先充值，就好比你把银行卡中的金额转入到支付宝或微信中是一个道理。

![](http://cdn.xiongsihao.com/202012022337_701.png)

### 4) 投标业务流程 

在P2P平台投资整个环节当中，借款人把自己需要借款的信息发布在平台上，这个需求就是一个借款项目，也是一个标的，投资人对这个借款项目进行投资就叫做投标。借款人发标并通过审核后，投资人就可以在P2P平台看到这些标的信息(可投资项目)，投资人对这个项 目进行投资(出借)就叫做投标。

![](http://cdn.xiongsihao.com/202012022338_451.png)

![](http://cdn.xiongsihao.com/202012022340_502.png)

**第一阶段：投标预览**(图中1.1-1.6)

1. 用户在前端选择要投资的标的 

2. 请求交易中心获取标的基本信息和已投标记录 

3. 交易中心请求用户服务获取借款人基本信息 

4. 交易中心返回投标预览信息给前端 

5. 前端显示投标预览信息，用户填写出借金额 

**第二阶段：用户投标**(图中2.1-2.13)

1. 用户在前端确认投标信息，并请求交易中心保存投标信息 

2. 交易中心保存用户投标信息(未发布) 

3. 交易中心请求存管代理服务对投标数据进行签名，并生成交易记录(未同步) 

4. 存管代理服务携带签名后的投标数据请求银行存管系统 

5. 银行存管系统保存投标信息，并冻结投资人用户余额 

6. 银行存管系统返回处理结果给存管代理服务 

7. 存管代理服务更新交易记录(已同步)，并返回投标成功结果给交易中心 

8. 交易中心更新投标结果后返回给前端 

9. 前端展示投标结果给用户



### 5) 满标放款业务流程 

当一个借款项目的所有借款金额被投资人投资后，由管理员且审核通过，P2P平台会将把所筹资金打入 借款人在平台的账户中。当一个标的已经筹集到了所借的全部资金，即为“满标”。此时P2P平台管理员 会进行审核，审核通过后，P2P平台会把投资人的出借资金打入借款人在平台的账户中，这就叫“放款”，此时借款人贷款成功。

![](http://cdn.xiongsihao.com/202012022339_885.png)

![](http://cdn.xiongsihao.com/202012022342_872.png)

在该业务中会新增一个还款微服务：为平台提供还款计划的生成、执行、记录与归档等功能

**第一阶段：生成放款明细**(图中1-2)

1. 前端向交易中心发起满标复审通过请求 

2. 交易中心汇总标的以及投标信息，形成放款明细 

**第二阶段：放款**(图中3-12)

1. 交易中心请求存管代理服务进行放款操作 

2. 存管代理服务对放款明细进行签名，并保存交易记录为未同步 

3. 存管代理服务请求存管系统进行放款 

4. 存管系统根据放款明细，将多个投资人冻结余额划至借款人账户，并更新投标信息状态为已放款 

5. 返回放款结果给存管代理服务 

6. 存管代理服务更新交易记录为已同步，并返回放款结果给交易中心 

7. 交易中心更新所有投标信息结果为：已放款 

**第三阶段：修改标的业务状态**(图中13-21)

1. 交易中心请求存管代理服务修改标的状态 

2. 存管代理服务对数据进行签名，并保存交易记录为：未同步 

3. 存管代理服务请求存管系统修改标的状态 

4. 存管系统修改标的为状态为：已放款，并返回结果给存管代理服务 

5. 存管代理服务更新交易记录为：已同步，返回修改成功给交易中心 

6. 交易中心收到返回结果，修改标的状态为：还款中

**第四阶段：启动还款**(图中22-25)

1. 交易中心请求还款服务启动还款 

2. 还款服务收到请求后生成还款计划和应收明细，并返回启动还款成功给交易中心 

3. 交易中心返回放款成功给前端 
4. 

### 6) 还款业务流程

当按照借款时约定的还款方式，平台会自动在还款日当天将应还本息从借款人账户划拨到投资人账户。 满标放款审核通过后，就意味着交易已经达成。借款人以后就需要按照借款时约定的还款方式，在还款 日当天将应还本息通过平台归还给投资人，这叫用户还款。借款人应该在临近还款日时，把应还的金额 充值到平台账户中，平台在还款日当天会自动进行扣款。

![](http://cdn.xiongsihao.com/202012022346_803.png)

![](http://cdn.xiongsihao.com/202012022346_710.png)

**第一阶段：生成还款明细**（图中1.1-1.2） 

1. 还款服务每天定时查询到期的还款计划 

2. 根据还款计划生成还款明细，状态为：未同步 

**第二阶段：还款预处理**（图中1.3-1.10） 

1. 还款服务通过feign请求存管代理服务进行还款预处理 

2. 存管代理服务生成签名及数据，并保存交易记录(未同步) 

3. 存管代理服务请求银行存管系统进行资金冻结 

4. 银行存管系统返回预处理冻结结果给存管代理服务 

5. 存管代理服务更新交易记录为：已同步，返回预处理结果给还款服务 

**第三阶段：确认还款**（图中2.1-2.2）

1. 还款服务发送确认还款事务消息(半消息) 

2. 还款服务执行处理本地事务： 

   - 更新还款明细为：已同步 

   - 更新投资人实收明细为：已收

   - 更新还款计划状态为：已还款

3. 还款服务根据本地事务执行结果发生commit或rollback 

![](http://cdn.xiongsihao.com/202012022350_124.png)

**第四阶段：还款成功**（图中2.3-2.9）

1. 还款服务消费消息，并通过feign请求存管代理服务进行确认还款 

2. 存管代理服务生成签名及数据，并保存交易记录（未同步） 

3. 请求银行存管系统进行还款确定 

4. 银行存管系统返回还款成功 

5. 存管代理服务更新交易记录为：已同步，并返回结果给还款服务 

6. 如果这个阶段处理失败，还款服务会重试消费



### 7) 提现业务流程 

投资人在收到标的的回款后，将余额从银行存管系统的虚拟账户中提取到自己绑定的储蓄卡。

![](http://cdn.xiongsihao.com/202012022351_534.png)

![](http://cdn.xiongsihao.com/202012022351_97.png)

**第一阶段：生成请求数据****(图中1.1-1.7)

1. 前端填写提现信息 

2. 前端请求用户中心服务提现 

3. 用户中心服务准备提现数据 

4. 用户中心服务请求存管代理服务生成交易记录（未同步），并对提现数据进行签名 

5. 存管代理服务将签名后的提现数据返回给用户中心 

6. 用户中心将提现数据返回给前端 

**第二阶段：请求提现****(图中2.1-2.8) 

1. 前端携带提现信息请求银行存管系统 

2. 银行存管系统向前端返回提现信息确定页面 

3. 前端确认完成提交提现请求到银行存管系统 

4. 银行存管系统接收提现数据并进行校验，校验交易密码，无误后则将银行卡余额进行扣减 

**第三阶段：提现结果通知**(图中3.1-3.4) 

1. 提现成功后银行存管系统异步通知存管代理服务 

2. 存管代理服务接收到提现成功通知更新交易状态为同步 

3. 存管代理服务通知用户中心服务 

4. 用户中心服务接收到提现成功的消息保存提现信息到用户中心服务

### 8) 身份认证

实现开户业务时，系统只是要求用户填写了个人姓名和身份证信息，并没有进行身份认证和校验。这里面就存在着各种风险，例如：假名，盗用他人身份证等等。

特别是对于借款人来说，系统必须对其身份进行认证和校验，否则会对后续交易产生较大风险。系统要么在开户时进行身份认证和校验，要么在发标(交易的第1步)时进行身份认证和校验，考虑到有些用户即使开户也不一定会进行交易(例如：发标)，所以把身份认证和校验放在发标时实现。更新后的发标流程：

![](http://cdn.xiongsihao.com/202012022355_673.png)

![](http://cdn.xiongsihao.com/202012022356_659.png)

**第一阶段：识别身份证**(图中1.1-1.5)

1. 前端携带身份证图片信息请求用户中心 

2. 用户中心请求百度AI平台进行识别 

3. 返回识别结果给前端 

**第二阶段：获取上传凭证**(图中2.1-2.4)

1. 前端请求用户中心获取上传授权 

2. 用户中心生成上传授权并返回 

**第三阶段：上传身份证**(图中3.1-3.2) 

1. 前端携带上传凭证和照片请求文件服务，文件服务保存照片到七牛云上 

2. 返回上传结果给前端 

**第四阶段：保存身份证信息**(图中3.3-3.5) 

1. 前端请求用户中心保存身份证信息 

2. 用户中心保存身份证照片标识等 

3. 返回前端保存成功

## 1.5 技术架构

万信金融采用当前流行的前后端分离架构开发，由用户层、UI层、微服务层、数据层等部分组成，为 PC、App、H5等客户端用户提供服务。下图是系统的技术架构图：

![](http://cdn.xiongsihao.com/202012022327_404.png)

各模块说明如下：

| **序**号 | 名称         | 功能描述                                                     |
| -------- | :----------- | ------------------------------------------------------------ |
| 1        | 用户层       | 用户层描述了本系统所支持的用户类型包括：pc用户、app用户、h5用户。<br/>pc用户通过浏览器访问系统、app用户通过android、ios手机访问系统，H5用户 通过h5页面访问系统。 |
| 2        | CDN          | CDN全称Content Delivery Network，即内容分发网络，本系统所有静态资源全部通过CDN加速来提高访问速度。<br/>系统静态资源包括：html页面、js文件、css文件、image图片、pdf和ppt及doc教学文档、video视频等。 |
| 3        | 负载均衡     | 系统的CDN层、UI层、服务层及数据层均设置了负载均衡服务，上图仅在UI层前边标注了负载均衡。 <br/>每一层的负载均衡会根据系统的需求来确定负载均衡器的类型，系统支持4层负载均衡+7层负载均衡结合的方式，4层负载均衡是指在网络传输层进行流程转发，根据IP和端口进行转发，7层负载均衡完成HTTP协议负载均衡及反向代理的功能，根据url进行请求转发。 |
| 4        | UI层         | UI层描述了系统向pc用户、app用户、h5用户提供的产品界面。<br/>根据系统功能模块特点确定了UI层包括如下产品界面类型： 1）面向C端用户的万信金融交易平台。 <br/>2）面向B端服务的万信金融业务支撑系统。 |
| 5        | 微服务层     | 微服务层将系统服务分类两类：业务微服务、基础微服务。 业务微服务：主要为C端和B端服务提供业务服务，包括统一认证、交易中心、还款服务等。 <br/>基础微服务：为系统级的公共服务，不涉及具体的业务，包括文件服务、配置服务、调度服务等。 |
| 6        | 数据层       | 数据层描述了系统的数据存储的内容类型，持久化的业务数据使用MySQL。<br/>消息队列：存储系统服务间通信的消息，本身提供消息存取服务，与微服务层的系统服务连接。 <br/>索引库：存储标的信息的索引信息，本身提供索引维护及搜索的服务，与微服务层的系统服务连接。 <br/>缓存：作为系统的缓存服务，存储标的信息、验证码信息、用户信息等，与微服务层的所有服务连接。 <br/>文件存储：提供系统静态资源文件的分布式存储服务，文件存储服务器作为CDN服务器的数据来源，CDN上的静态资源将最终在文件存储服务器上保存多份。 |
| 7        | 外部系统接口 | 1）银行存管系统接口，借钱、出借交易全部通过银行存管系统存储订单信息。 <br/>2）支付宝、微信、网银支付接口，本系统提供支付宝、微信、网银三种 支付接口。 <br/>3）短信接口，本系统与第三方平台对接短信发送接口。 <br/>4）邮件接口，本系统需要连接第三方的smpt邮件服务器对外发送电子邮件。<br/>5）文件存储 ，静态资源文件的存储采用第三方文件服务方式，本系统初期采用七牛云文件存储 <br/>6）CDN，本系统与第三方CDN服务对接，使用CDN加速服务来提高本系统的访问速度。 |
| 8        | 银行存管系统 | 目前国家主张采用“银行存管模式”来规避P2P平台挪用借投人资金的风险，通过银行开发的“银行存管系统”管理投资者的资金，每位P2P平台用户在银行的存管系统内都会有一个独立账号，P2P平台来管理交易，做到资金和交易分开。P2P平台需要与银行存管系统接口，存储借款人和投资人之间的交易信息。 |
| 9        | DevOps       | DevOps（英文Development和Operations的组合）是一组过程、方法与系统的统称，用于促进开发（应用程序/软件工程）、技术运营和质量保障（QA）部门之间的沟通、协作与整合。<br/>本项目供了许多开发、运营、维护支撑的系统，包括： Eureka服务治理中心：提供服务治理服务，包括：服务注册、服务获取等。 <br/>Spring Cloud Confifig服务配置管理中心：提供服务配置管理服务，包括：配置文件更新、配置文件下发等。 <br/>Hystrix Dashboard服务熔断监控：监控熔断的请求响应时间、成功率等 。<br/>Zipkin服务追踪监控：监控服务调用链路健康情况。 <br/>Jenkins持续集成服务：提供系统持续集成服务。<br/>Git/GitLab代码管理服务:提供git代码管理服务。<br/>ELK日志分析服务:提供elk日志分析服务，包括系统运行日志分析、告警服务。 <br/>Docker容器化部署服务：将本系统所有服务采用容器化部署方式。 <br/>Maven项目管理工具：提供管理项 目所有的Java包依赖、项目工程打包服务。 |

业务流程举例： 

1、用户可以通过pc、手机等客户端访问万信金融平台。 

2、 系统应用CDN技术，对一些图片、CSS、视频等资源从CDN调度访问。 

3、所有的请求全部经过负载均衡器。 

4、首先请求UI层，渲染用户界面。 

5、借款人登录系统发布标的，UI层通过网关请求服务层，服务层完成业务处理，将数据持久化到数据层。 

6、投资人登录系统查询新的标的进行投标，其系统执行流程和发标过程一致，UI层请求服务层业务处理，服务层查询数据层并将数据持久化到数据层。 

## 1.6  技术栈

![](http://cdn.xiongsihao.com/202012030010_990.png)

万信金融服务端基于Spring Boot构建，采用Spring Cloud微服务框架。

1）基础设施

业务数据持久化采用MySQL，数据缓存采用Redis，采用RocketMQ的事务消息机制完成部分场景下的 

分布式事务控制，采用Elasticsearch完成标的信息搜索，与自研的分布式文件系统进行接口完成文件上 

传与分布式存储。 

2）组件 

系统微服务基于SpringBoot+SpringCloud开发，数据库连接池采用Druid，POJO构建采用Lombok，日 

志系统采用Log4j2，Guava工具类库，Mybatis Plus持久层接口实现，Sharding-jdbc分库分表组件， 

Swagger接口规范组件，Elastic-job分布式任务调度组件，sentinel限流组件。 

3）接入 

Zuul网关完成客户端认证、路由转发等功能，Ribbon完成客户端负载均衡，Feign完成微服务远程调 

用，Hystrix完成熔断降级处理，JWT提供前后端令牌管理方案。 

4）视图 

平台支持H5、App等各种前端。

## 1.7 技术解决方案

1、微服务技术应用于P2P金融业务解决方案 

2、接口规范SpringBoot+Swagger 

3、持久层编码 MyBatis Plus 

4、分布式系统配置中心：Apollo 

5、UAA认证方案：Spring Security Oauth2+JWT+ZUUL 

6、分布式事务解决方案（RocketMQ、Hmily、requestNo同步机制） 

7、分库分表解决方案：Sharding-jdbc

8、分布式任务调度方案：Elastic-job 

9、安全交易方案：HTTPS+SHA1withRSA 

10、身份认证方案：百度AI 

11、短信验证系统方案：短信验证服务+第三方短信平台（腾讯）























