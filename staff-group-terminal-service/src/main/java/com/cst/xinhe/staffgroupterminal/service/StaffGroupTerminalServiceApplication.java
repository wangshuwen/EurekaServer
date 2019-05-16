package com.cst.xinhe.staffgroupterminal.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@EnableFeignClients
@EnableHystrix
@EnableSwagger2
@MapperScan("com.cst.xinhe.persistence.dao")
@SpringBootApplication
@EnableEurekaClient
public class StaffGroupTerminalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffGroupTerminalServiceApplication.class, args);
    }

}
