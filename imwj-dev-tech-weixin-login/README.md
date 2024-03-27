# 微信公众号平台扫码登录（DDD）

## 对接文档-公众号
* 微信测试号：https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index
* 微信公众号文档：https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html

## 工程结构
* imwj-dev-tech-app：是启动应用程序的入口，其他模块也被直接或者间接的引入到 app 模块下，这样才能被 Spring 扫描加载。
* imwj-dev-tech-infrastructure：是基础设施层，用于对接外部接口、缓存、数据库等相关内容的连接使用。本节主要是对接微信开发平台的接口。采用的是 retrofit2 技术框架，这样对接起来更加方便。
* imwj-dev-tech-domain：是功能实现层，像是登录的具体实现，就是在 domain 领域层实现的。你将来使用 DDD 做的其他功能，也是放到 domain 领域下实现，每一个功能就是就是一个模块。
* imwj-dev-tech-types：用于定义基本的类型、枚举、错误码等内容。
