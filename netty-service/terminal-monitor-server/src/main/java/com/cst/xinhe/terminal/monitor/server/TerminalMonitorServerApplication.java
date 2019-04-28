package com.cst.xinhe.terminal.monitor.server;

import com.cst.xinhe.terminal.monitor.server.config.NettyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TerminalMonitorServerApplication implements CommandLineRunner {

    @Autowired
    NettyConfig nettyConfig;

    public static void main(String[] args) {
        SpringApplication.run(TerminalMonitorServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyConfig.run();
    }
}
