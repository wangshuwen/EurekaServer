package com.cst.xinhe.gateway.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/9/3/16:48
 */
@Component
public class FileConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //addResourceHandler是指你想在url请求的路径
    //addResourceLocations是图片存放的真实路径
        registry.addResourceHandler("/file/**").addResourceLocations("file:D:/voice/file");
        super.addResourceHandlers(registry);
    }


}
