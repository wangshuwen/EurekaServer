package com.cst.xinhe.eureka.server.backups;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerBackupsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerBackupsApplication.class, args);
    }

}
