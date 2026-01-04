package com.zds.bioengtsnapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * 主应用类：继承 SpringBootServletInitializer 以支持外部 Tomcat 部署
 * 排除 ReactiveWebServerFactoryAutoConfiguration 避免 WebFlux Netty 与 Tomcat 冲突
 */
@SpringBootApplication(exclude = {ReactiveWebServerFactoryAutoConfiguration.class})
@ComponentScan({"com.zds.bioengtsnapp", "generator"})
@MapperScan({"com.zds.bioengtsnapp.mapper", "generator.mapper"})
public class BioengTsnAppApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BioengTsnAppApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BioengTsnAppApplication.class, args);
    }

}
