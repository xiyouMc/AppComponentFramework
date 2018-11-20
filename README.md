# AppComponentFramework
App 组件化 加载框架，维护一个树状数据结构进行管理、深度优先搜索


# 组件化框架-》树状结构

## 一、需求介绍

> 随着目前 App 的组件越来越多，同时每一个组件的初始化工作也都隶属于本组件内。基于目前的组件化+路由框架 存在几个比较棘手的问题是：
> 

* 初始化的耦合问题

这个问题在目前的系统也是比较明显的，例如在调用网络接口的时候就必须依赖网络组件的初始化、再比如调用用户数据相关的地方必须保证数据库已经初始化。

* 懒加载并非真正的懒加载

目前 组件化框架虽然已经支持 Lazy 字段的设置，但目前的 Lazy 并不是调用的时候才去初始化。而是在 Splash 之后 延迟做初始化。

这样就存在了性能问题，因为某些组件可能一直并没有使用到，但却被初始化了。

> 之前的组件化框架，是直接通过一个 ServiceManager 来管理的，这也导致 ServiceManager 非常重。

![afterBuild.png](https://raw.githubusercontent.com/xiyouMc/AppComponentFramework/master/image/before.png)


## 二、需求分析

1.1 、组件化框架的 5W 分析如下：

Who： 在 App 启动以及 子 Module 相关调用的时候需要使用到。

When： 在 App 启动的时候，将 bundles.xml 构建为一个树状的数据结构。同时构建完成之后，对非 lazy 的节点进行初始化。 以及 某些lazy 的节点被调用时候的初始化。

What: 构建出基于组件化的一套树状数据结构，同时支持 叶子节点被访问时 校验根节点的初始化状态。

Where: library-router 的 Module 进行重构

Why: 将组件化进行高可用的重构，支持 根节点（组件的初始化） 的被动调用（叶子节点被访问时 校验根节点的状态）

1.2、 组件化的 1H 分析如下：

树状的数据结构有几个核心功能：

* 启动构建树
* 广度遍历初始化非 lazy 的节点（Bundle + Service+Fragment）
* 支持业务方的 查找 Service 功能（深度搜索）
* 支持 查找 Service 时 的根节点校验支持


## 三、架构设计

3.1、总体方案

基于目前组件化加载框架，对其增加 Session 和 Node 的概念，以及将 Node 串联为一个树状的结构

3.2、架构总揽

![after.png](https://raw.githubusercontent.com/xiyouMc/AppComponentFramework/master/image/after.png)



架构设计的关键点：

* 树状数据结构
* 每一个 Module 定义为 Session 的概念，即维护整个 Module 。以及管理 Module 的组件加载工作
* 每一个 Session 中包含了很多个叶子节点。叶子节点分为 Service 和 Fragment .
* 当 B Session 的组件需要调用 A Session 的 Service 节点， 则 在 A Session 的 叶子节点需要回溯 A Session 是否初始化。 Fragment 同理

3.3、核心流程

*  App 启动 构建树状结构

最外层的 APP 为 Root 节点，每一个 Bundle 作为 Session 层。 Bundle 里面的 Service 、Fragment 作为叶子节点

* 初始化 每一个 Session

当 Bundle 定义为 非 Lazy 的，则在 树构建成功之后，以广度优先算法对齐进行初始化

* 初始化每一个 Service 、Fragment

深度优先，遍历 数 对每一个 非 lazy  的 Service 和 Fragment 进行初始化

* 调用 Service 

深度优先、从根节点一直遍历到叶子节点，如果找到 则 return、如果未找到 则回溯到根节点 再遍历其他 子节点。

## 四、高可用性

* 如果树结构已经解体，则重新遍历 bundles 构建新的树
* 如果某一个叶子节点在深度搜索之后都没有找到，则说明 树已经出现问题。则重新构建树
* 支持叶子节点回溯 初始化根节点（Session） 的逻辑。