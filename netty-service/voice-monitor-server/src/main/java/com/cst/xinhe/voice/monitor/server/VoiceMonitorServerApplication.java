package com.cst.xinhe.voice.monitor.server;

import com.cst.xinhe.voice.monitor.server.config.NettyConfig;
import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableTransactionManagement
@MapperScan("com.cst.xinhe.persistence.dao")
@EnableEurekaClient
@SpringBootApplication
public class VoiceMonitorServerApplication implements CommandLineRunner {

    @Autowired
    NettyConfig nettyConfig;

    public static void main(String[] args) {
        SpringApplication.run(VoiceMonitorServerApplication.class, args);
    }

//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
//        return tomcat;
//    }
//    // 配置http
//    private Connector createStandardConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setPort(8780);
//        return connector;
//    }

    @Override
    public void run(String... args) throws Exception {
        nettyConfig.run();
    }
}
