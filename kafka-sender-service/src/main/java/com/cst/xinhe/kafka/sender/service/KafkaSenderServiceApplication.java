package com.cst.xinhe.kafka.sender.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class KafkaSenderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaSenderServiceApplication.class, args);
    }

}
