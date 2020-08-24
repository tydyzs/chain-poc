## Chain是什么
* Chain 是一个基于 Spring Boot 2 & Spring Cloud Greenwich & Mybatis 等核心技术，用于快速构建中大型系统的基础框架
* 已稳定生产近一年，经历了从Camden->Greenwich的技术架构，也经历了从FatJar->Docker->K8S+Jenkins的部署架构
* 采用前后端分离的模式，前端开发两个框架：Sword(基于React、Ant Design)、Saber(基于Vue、ElementUI)
* 后端采用SpringCloud系列，对其基础组件做了高度的封装，单独出一个后端核心框架：Chain-Tool
* Chain-Tool已推送至Maven私有库，直接引入减少工程的模块与依赖，可更注重于业务开发
* 集成Sentinel从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性
* 注册中心、配置中心选型Nacos，为工程瘦身的同时加强各模块之间的联动
* 使用Traefik进行反向代理，监听后台变化自动化应用新的配置文件
* 集成Oauth2协议，完美支持了多终端的接入与认证授权
* 集成工作流Flowable，复杂流程需求不再难办
* 创建多租户模式，发布简单，数据隔离轻松
* 项目分包明确，规范微服务的开发模式

## 商业授权
* 您一旦开始复制、下载、安装或者使用本产品，即被视为完全理解并接受本协议的各项条款
* 更多详情请看：[Chain商业授权许可协议](https://gitee.ltd/chain/Chain-Boot/src/master/LICENSE)

## 官网
* 官网地址：[https://chain.vip](https://chain.vip)

## 在线演示
* Sword演示地址：[https://sword.chain.vip](https://sword.chain.vip)
* Saber演示地址：[https://saber.avue.top](https://saber.avue.top)

## 后端项目地址
* [Chain](https://gitee.ltd/chain/Chain)

## 单工程SpringBoot版
* [Chain-Boot](https://gitee.ltd/chain/Chain-Boot)

## 前端项目地址
* [Sword--基于React](https://gitee.ltd/chain/Sword)
* [Saber--基于Vue](https://gitee.ltd/chain/Saber)

## 会员计划及交流群
* [会员计划及交流群](https://gitee.com/smallc/SpringChain/wikis/SpringChain会员计划)

## 技术文档
* [Chain开发手册](https://www.kancloud.cn/smallchill/chain)

## 工程结构
``` 
Chain
├── chain-auth -- 授权服务提供
├── chain-common -- 常用工具封装包
├── chain-gateway -- Spring Cloud 网关
├── chain-ops -- 运维中心
├    ├── chain-admin -- spring-cloud后台管理
├    ├── chain-develop -- 代码生成
├── chain-service -- 业务模块
├    ├── chain-desk -- 工作台模块 
├    ├── chain-log -- 日志模块 
├    ├── chain-system -- 系统模块 
├    └── chain-user -- 用户模块 
├── chain-service-api -- 业务模块api封装
├    ├── chain-desk-api -- 工作台api 
├    ├── chain-dict-api -- 字典api 
├    ├── chain-system-api -- 系统api 
└──  └── chain-user-api -- 用户api 
```

# 界面
## 监控界面一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-k8s1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-k8s2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-grafana.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-harbor.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-traefik.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-traefik-health.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-nacos.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-sentinel.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-admin1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-admin2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-swagger1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/springchain-swagger2.png"/></td>
    </tr>
</table>

## Sword界面一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-main.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-menu.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-menu-edit.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-menu-icon.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-role.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-user.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-dict.png "/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-log.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-locale-cn.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/sword-locale-us.png"/></td>
    </tr>
</table>

## Saber界面一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/saber-user.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/saber-role.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/saber-dict.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/saber-dict-select.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/saber-log.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringChain/raw/master/pic/saber-code.png"/></td>
    </tr>
</table>