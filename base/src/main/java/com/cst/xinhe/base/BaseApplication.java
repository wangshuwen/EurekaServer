package com.cst.xinhe.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-13 17:31
 **/
@EnableSwagger2
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

}
