package com.cst.xinhe.ws.push.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class WsPushServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsPushServiceApplication.class, args);
    }

}
