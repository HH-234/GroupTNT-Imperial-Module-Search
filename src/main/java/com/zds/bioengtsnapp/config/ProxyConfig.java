package com.zds.bioengtsnapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 代理配置 - 用于转发请求到目标服务器
 */
@Configuration
public class ProxyConfig {

    public static final String TARGET_BASE_URL = "http://www.breakeve.cn";

    @Bean(name = "proxyRestTemplate")
    public RestTemplate proxyRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(30000);
        return new RestTemplate(factory);
    }
}

