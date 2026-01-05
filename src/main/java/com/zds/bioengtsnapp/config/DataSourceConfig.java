package com.zds.bioengtsnapp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置 - 使用原生 JDBC 环境变量方式
 * 从环境变量获取连接信息：PGHOST, PGPORT, PGDATABASE, PGUSER, PGPASSWORD
 * 
 * 这个配置会被 MyBatis Plus 自动使用
 */
@Configuration
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    /**
     * 创建 DataSource Bean - 使用 HikariCP 连接池
     * 连接信息从环境变量获取
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        String pgHost = System.getenv("PGHOST");
        String pgPort = System.getenv("PGPORT");
        String pgDatabase = System.getenv("PGDATABASE");
        String pgUser = System.getenv("PGUSER");
        String pgPassword = System.getenv("PGPASSWORD");

        logger.info("=== Creating DataSource from environment variables ===");
        logger.info("PGHOST: {}", pgHost != null ? pgHost : "(not set)");
        logger.info("PGPORT: {}", pgPort != null ? pgPort : "(not set)");
        logger.info("PGDATABASE: {}", pgDatabase != null ? pgDatabase : "(not set)");
        logger.info("PGUSER: {}", pgUser != null ? pgUser : "(not set)");
        logger.info("PGPASSWORD: {}", pgPassword != null ? "(set)" : "(not set)");

        // 如果环境变量缺失，抛出明确的错误信息
        if (pgHost == null || pgPort == null || pgDatabase == null || pgUser == null || pgPassword == null) {
            logger.warn("Database environment variables are missing! Creating a placeholder DataSource.");
            logger.warn("Please set: PGHOST, PGPORT, PGDATABASE, PGUSER, PGPASSWORD");
            
            // 返回一个配置了默认值的 DataSource（会在连接时失败，但不会阻止应用启动）
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/placeholder");
            config.setUsername("placeholder");
            config.setPassword("placeholder");
            config.setDriverClassName("org.postgresql.Driver");
            
            // 设置连接池参数 - 最小化以快速失败
            config.setMinimumIdle(0);
            config.setMaximumPoolSize(1);
            config.setConnectionTimeout(5000);  // 5秒超时
            config.setInitializationFailTimeout(-1);  // 不在启动时验证连接
            
            return new HikariDataSource(config);
        }

        // 构建 JDBC URL
        String jdbcUrl = "jdbc:postgresql://" + pgHost + ":" + pgPort + "/" + pgDatabase;
        logger.info("JDBC URL: {}", jdbcUrl);

        // 配置 HikariCP 连接池
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(pgUser);
        config.setPassword(pgPassword);
        config.setDriverClassName("org.postgresql.Driver");

        // 连接池配置
        config.setMinimumIdle(2);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);  // 30秒连接超时
        config.setIdleTimeout(600000);       // 10分钟空闲超时
        config.setMaxLifetime(1800000);      // 30分钟最大生命周期
        config.setInitializationFailTimeout(-1);  // 允许启动时数据库不可用

        // PostgreSQL 特定配置
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        logger.info("DataSource created successfully with HikariCP");
        
        return new HikariDataSource(config);
    }
}

