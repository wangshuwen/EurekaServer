package com.cst.xinhe.attendance.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableScheduling
@EnableTransactionManagement
@EnableFeignClients
@EnableHystrix
@EnableEurekaClient
@EnableSwagger2
@SpringBootApplication
@RestController
@MapperScan("com.cst.xinhe.persistence.dao")
public class AttendanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceServiceApplication.class, args);
    }

   /* @Value("${foo}")
     String foo;

    @RequestMapping("/hi")
    public String hi(){
        return "hi  shuwen!!!!"+foo;
    }*/

}
