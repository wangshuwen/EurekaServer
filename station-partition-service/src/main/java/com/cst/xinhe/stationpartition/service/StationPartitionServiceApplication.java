package com.cst.xinhe.stationpartition.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableTransactionManagement
@MapperScan("com.cst.xinhe.persistence.dao")
@EnableFeignClients
@EnableHystrix
@EnableSwagger2
@EnableEurekaClient
@SpringBootApplication
public class StationPartitionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StationPartitionServiceApplication.class, args);
    }

}
