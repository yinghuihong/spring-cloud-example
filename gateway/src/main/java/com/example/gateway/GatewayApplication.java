package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author yinghuihong
 * @date 2020/1/17
 */
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        return builder.routes()
                // 添加路由，包括断言+行为
                .route(p -> p
                        // 采用path做断言
                        .path("/get")
                        // 经过一道过滤器：在头部添加了Hello: World
                        .filters(f -> f
                                .addRequestHeader("Hello", "World")
                        )
                        // 将转发请求至：http://httpbin.org/get
                        .uri(httpUri)
                )
                .route(p -> p
                        // 采用header做断言
                        .header("Hello", "Spring")
                        // 行为
                        .filters(f -> f
                                // hystrix 断路器
                                .hystrix(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")
                                )
                        )
                        .uri(httpUri)
                )
                .build();
    }

}
