package com.example.servicefeign.schedule;

import org.springframework.stereotype.Component;

/**
 * 断路器
 *
 * @author yinghuihong
 * @date 2020/1/17
 */
@Component
public class ScheduleServiceHiHystrix implements ScheduleServiceHi {

    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry " + name;
    }
}
