package com.cst.xinhe.web.service.gas.client.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @program: EurekaServer
 * @description:    FeignConfig配置
 * @author: lifeng
 * @create: 2019-04-26 15:05
 **/
@Configuration
public class FeignConfig {
    /**
     * 请求重试
     * @return
     */
    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(200, TimeUnit.SECONDS.toMillis(2), 10);
    }

    /**
     * 超时设置
     * @return
     */
    @Bean
    Request.Options feignOptions(){
        return new Request.Options(60 * 1000, 60 * 1000);
    }

    /**
     * 日志打印
     * @return
     */
    @Bean
    public Logger.Level multipartLoggerLevel(){
        return Logger.Level.FULL;
    }

    /**
     * 注入负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate geRestTemplate(){
        return new RestTemplate();
    }

    /**
     * 配置负载均衡策略-默认轮询策略
     * @return
     */
    @Bean
    public IRule myRule(){

        //return new WeightedResponseTimeRule();
        return new RoundRobinRule();
    }
}
