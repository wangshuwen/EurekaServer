package com.cst.xinhe.voice.monitor.server.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.WebAppRootListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @program: EurekaServer
 * @description: 222
 * @author: lifeng
 * @create: 2019-09-04 18:41
 **/

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class WebAppRootContext implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("org.apache.tomcat.websocket.textBufferSize");
        servletContext.addListener(WebAppRootListener.class);
        //2048000
        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","4096000");
    }

}
