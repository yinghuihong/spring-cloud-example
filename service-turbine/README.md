- 启动service-ribbon
- 启动service-turbine
- 访问监控流地址，https://turbine-hostname:port/turbine.stream
    - [http://127.0.0.1:8801/turbine.stream](http://127.0.0.1:8801/turbine.stream)
    - 注意这里的path中没有actuator，不同于[http://127.0.0.1:8791/actuator/hystrix.stream](http://127.0.0.1:8791/actuator/hystrix.stream)