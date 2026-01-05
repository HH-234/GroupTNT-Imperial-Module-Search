package com.zds.bioengtsnapp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 自定义数据源配置
 * 从环境变量读取数据库连接信息，避免在配置文件中硬编码
 */
@Configuration
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    public DataSource dataSource() {
        // 从环境变量读取数据库连接信息，提供默认值用于本地开发
        String host = System.getenv("PGHOST");
        String port = System.getenv("PGPORT");
        String database = System.getenv("PGDATABASE");
        String username = System.getenv("PGUSER");
        String password = System.getenv("PGPASSWORD");

        // 如果环境变量不存在，使用本地开发默认值
        if (host == null || host.isEmpty()) {
            host = "localhost";
            logger.info("PGHOST not set, using default: localhost");
        }
        if (port == null || port.isEmpty()) {
            port = "5432";
            logger.info("PGPORT not set, using default: 5432");
        }
        if (database == null || database.isEmpty()) {
            database = "postgres";
            logger.info("PGDATABASE not set, using default: postgres");
        }
        if (username == null || username.isEmpty()) {
            username = "postgres";
            logger.info("PGUSER not set, using default: postgres");
        }
        if (password == null || password.isEmpty()) {
            password = "123456";
            logger.info("PGPASSWORD not set, using default: 123456");
        }

        // 构造 JDBC URL
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        
        logger.info("Creating DataSource with URL: jdbc:postgresql://{}:{}/{}", host, port, database);
        logger.info("Username: {}", username);

        // 使用 HikariCP 配置数据源
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        
        // 连接池配置
        config.setConnectionTimeout(30000); // 30秒
        config.setMaximumPoolSize(10);      // 最大连接数
        config.setMinimumIdle(2);           // 最小空闲连接数
        config.setIdleTimeout(600000);      // 空闲连接超时时间（10分钟）
        config.setMaxLifetime(1800000);     // 连接最大生存时间（30分钟）
        config.setLeakDetectionThreshold(60000); // 连接泄漏检测阈值（60秒）
        
        return new HikariDataSource(config);
    }
}

