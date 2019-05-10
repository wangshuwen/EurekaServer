package com.cst.xinhe.station.monitor.server;

import com.cst.xinhe.station.monitor.server.config.NettyConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.cst.xinhe.persistence.dao")
@EnableFeignClients
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class StationMonitorServerApplication implements CommandLineRunner {


    @Autowired
    private NettyConfiguration nettyConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(StationMonitorServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        nettyConfiguration.run();
    }
}
