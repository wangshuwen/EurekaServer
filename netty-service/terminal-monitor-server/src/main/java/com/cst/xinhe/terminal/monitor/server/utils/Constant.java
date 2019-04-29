package com.cst.xinhe.terminal.monitor.server.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
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
