package com.cst.xinhe.ws.push.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WSConfig
 * @Description
 * @Auther lifeng
 * @DATE 2018/8/31 14:30
 * @Vserion v0.0.1
 */


@Configuration
public class WSConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
