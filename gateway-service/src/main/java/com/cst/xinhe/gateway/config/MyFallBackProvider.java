/*
package com.cst.xinhe.gateway.service;

import org.springframework.stereotype.Component;

import org.springframework.cloud.netflix.zuul.ZuulServletFilter;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

*/
/**
 * @author wangshuwen
 * @Description:  在zuul上配置熔断器
 * @Date 2019/4/29/11:04
 *//*

@Component
public class MyFallBackProvider implements ZuulFallbackProvider{


    @Override
    public String getRoute() {
        return "eureka-client";//所有的路由服务都加熔断功能
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("ooops! error,i'm the fallback.".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders header = new HttpHeaders();
                header.setContentType(MediaType.APPLICATION_JSON);
                return header;
            }
        };
    }
}
*/
