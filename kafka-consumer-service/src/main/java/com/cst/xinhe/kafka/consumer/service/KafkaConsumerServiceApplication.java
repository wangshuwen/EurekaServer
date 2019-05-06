package com.cst.xinhe.kafka.consumer.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.cst.xinhe.persistence.dao")
@EnableSwagger2
@EnableFeignClients
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class KafkaConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerServiceApplication.class, args);
    }

}
