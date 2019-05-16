package com.cst.xinhe.chatmessage.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@MapperScan("com.cst.xinhe.persistence.dao")
@EnableSwagger2
@EnableHystrix
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ChatMessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatMessageServiceApplication.class, args);
    }


}
