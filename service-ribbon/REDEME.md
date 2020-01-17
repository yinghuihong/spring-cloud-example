- [http://localhost:8090/hi](http://localhost:8090/hi)
- [服务发现缓存问题](https://www.jianshu.com/p/6ac4810f6f42)
- [Why does it take so long to register an instance with Eureka?](https://github.com/spring-cloud/spring-cloud-netflix/issues/373)
      
    (1) Client Registration
    When using the default configuration, registration happens at the first heartbeat sent to the server. Since the client just started, the server doesn't know anything about it and replies with a 404 forcing the client to register. The client then immediately issues a second call with all the registration information. The client is now registered.
    
    The first heartbeat happens 30 seconds after startup (eureka.instance.leaseRenewalIntervalInSeconds) - so your instance won't appear in the Eureka registry before this interval.
    
    (2) Server ResponseCache
    The server maintains a response cache that is updated every 30s by default (eureka.server.response-cache-update-interval-ms). So even if your instance is just registered, it won't appear in the result of a call to the /eureka/apps REST endpoint.
    
    However, your instance may appear in the UI web interface just after registration. This is because the web front-end bypasses the response cache used by the REST API...
    
    If you know the instanceId, you can still get some details from Eureka about it by calling /eureka/apps/<appName>/<instanceId>. This endpoint doesn't make use of the response cache. But since it requires to know the instance, it is of no help in the discovery process...
    
    So, it may take up to another 30s for other clients to discover your newly registered instance.
    
    (3) Client cache refresh
    Eureka client maintain a cache of the registry information. This cache is refreshed every 30 seconds by default ('eureka.client.registryFetchIntervalSeconds`). So again, it may take another 30s before a client decides to refresh its local cache and discover newly registered instances.
    
    (4) LoadBalancer refresh
    The load balancer used by Ribbon gets its information from the local Eureka client. It also maintains a local cache to avoid calling the discovery client for every request. This cache is refreshed every 30s (ribbon.serverListRefreshInterval). So again, it may take another 30s before your Ribbon client can make use of the newly registered instance.
    
    Note: this local cache is apparently required only to reduce the cost of obtaining server information from the used ServerList. This is not the case with none of the server list provided by default: DiscoveryEnabledNIWSServerList with Eureka, ConfigurationBasedServerList without.
    
    At the end, if you are not lucky, it may take up to 2 minutes before your newly registered instance starts receiving trafic from other clients.

- 缓存及时性的实验方法
    - 启动eureka-server
    - 启动service-ribbon
    - 反复上线/下线service-hi
    - 调用service-ribbon的hi接口

- 最终方案
    - Server ResponseCache
        - 在eureka-server中，配置eureka.server.use-read-only-response-cache: false
    - Client cache refresh
        - 在eureka-client中，配置eureka.client.registry-fetch-interval-seconds: 5
    - LoadBalancer refresh
        - 当负载均衡使用RIBBON时，修改ribbon.serverListRefreshInterval=2000
    - 正常上线下线客户端最大感知时间：7秒
    
    
------------------------------------------------------------------------------------------------------------------------

- hystrix dashboard
- [http://127.0.0.1:8791/hystrix/](http://127.0.0.1:8791/hystrix/)
- Single Hystrix App: [http://127.0.0.1:8791/actuator/hystrix.stream](http://127.0.0.1:8791/actuator/hystrix.stream)