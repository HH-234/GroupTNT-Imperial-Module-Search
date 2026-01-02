package com.zds.bioengtsnapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 事务管理配置
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    // Spring Boot 会自动配置事务管理器，这里只需要启用注解驱动的事务管理
}

