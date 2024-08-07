# imwj-dev-tech-big-market - 大营销平台

imwj-dev-tech-big-market-app：是启动应用程序的入口，其他模块也被直接或者间接的引入到 app 模块下，这样才能被 Spring 扫描加载。  
imwj-dev-tech-big-market-infrastructure：是基础设施层，用于对接外部接口、缓存、数据库等相关内容的连接使用。本节主要是对接微信开发平台的接口。  
imwj-dev-tech-big-market-domain：是功能实现层，像是登录的具体实现，就是在 domain 领域层实现的。你将来使用 DDD 做的其他功能，也是放到 domain 领域下实现，每一个功能就是就是一个模块。  
imwj-dev-tech-big-market-trigger：调用http外部接口、定时任务、监听器触发等  
imwj-dev-tech-big-market-types：用于定义基本的类型、枚举、错误码等内容。 
test