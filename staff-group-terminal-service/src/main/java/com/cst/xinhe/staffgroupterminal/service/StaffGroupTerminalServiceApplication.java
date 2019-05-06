package com.cst.xinhe.staffgroupterminal.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan("com.cst.xinhe.persistence.dao")
@SpringBootApplication
public class StaffGroupTerminalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffGroupTerminalServiceApplication.class, args);
    }

}
