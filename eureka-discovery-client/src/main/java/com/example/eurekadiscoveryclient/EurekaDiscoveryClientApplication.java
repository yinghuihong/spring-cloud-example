package com.example.eurekadiscoveryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaDiscoveryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaDiscoveryClientApplication.class, args);
    }

}
