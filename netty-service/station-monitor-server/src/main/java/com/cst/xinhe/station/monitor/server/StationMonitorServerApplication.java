package com.cst.xinhe.station.monitor.server;

import com.cst.xinhe.base.context.SpringContextUtil;
import com.cst.xinhe.station.monitor.server.config.NettyConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@EnableSwagger2
//@ComponentScan(basePackages={"com.cst.xinhe"})
@MapperScan("com.cst.xinhe.persistence.dao")
@EnableFeignClients
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class StationMonitorServerApplication extends SpringBootServletInitializer implements CommandLineRunner {



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
