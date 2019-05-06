package com.cst.xinhe.common.netty.utils;

/**
 * @program: demo
 * @description: 常量
 * @author: lifeng
 * @create: 2019-03-05 13:47
 **/

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 常量类
 *
 */
@Component
@ConfigurationProperties(prefix = "constant")
public class Constant {

    public String webBaseUrl ;
    public String basePath ;
    public String rangBasePath ;

    public String getWebBaseUrl() {
        return webBaseUrl;
    }

    public void setWebBaseUrl(String webBaseUrl) {
        this.webBaseUrl = webBaseUrl;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getRangBasePath() {
        return rangBasePath;
    }

    public void setRangBasePath(String rangBasePath) {
        this.rangBasePath = rangBasePath;
    }
}
