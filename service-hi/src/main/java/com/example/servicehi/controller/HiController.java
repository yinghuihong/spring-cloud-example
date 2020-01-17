package com.example.servicehi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinghuihong
 * @date 2020/1/16
 */
@RestController
public class HiController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "yinghuihong") String name) {
        return "hi " + name + ", i am from port: " + port;
    }
}
