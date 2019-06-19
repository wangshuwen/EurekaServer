package com.cst.xinhe.kafka.consumer.service;

import com.cst.xinhe.kafka.consumer.service.context.SpringContextUtil;
import com.cst.xinhe.kafka.consumer.service.service.KafkaConsumerService;
import com.cst.xinhe.kafka.consumer.service.service.impl.KafkaConsumerServiceImpl;
import com.cst.xinhe.kafka.consumer.service.util.CheckPointWithPolygon;
import com.cst.xinhe.kafka.consumer.service.util.ICheckPointWithPolygon;
import com.cst.xinhe.kafka.consumer.service.util.ObserverableOfPoint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@EnableTransactionManagement
@MapperScan("com.cst.xinhe.persistence.dao")
@EnableFeignClients
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class KafkaConsumerServiceApplication implements ApplicationRunner {


    @Resource
    private KafkaConsumerService kafkaConsumerService;

    @Resource
    ObserverableOfPoint observerableOfPoint;

    public static void main(String[] args) {

        SpringApplication.run(KafkaConsumerServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        observerableOfPoint.register(SpringContextUtil.getBean(CheckPointWithPolygon.class));
        kafkaConsumerService.updateWarningAreaInfo();
    }
}
