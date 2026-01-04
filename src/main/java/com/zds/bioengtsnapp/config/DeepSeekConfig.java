package com.zds.bioengtsnapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class DeepSeekConfig {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.base-url}")
    private String baseUrl;

    @Bean
    public RestTemplate deepSeekRestTemplate(RestTemplateBuilder builder) {
        ClientHttpRequestInterceptor authInterceptor = (request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return execution.execute(request, body);
        };

        return builder
                .rootUri(baseUrl)
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(60))
                .additionalInterceptors(Collections.singletonList(authInterceptor))
                .build();
    }
}
