package com.zds.bioengtsnapp.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 解析 Tsuru 的 TSURU_SERVICES 环境变量或直接环境变量来配置数据源
 * 
 * Tsuru 会自动注入以下环境变量：
 * - PGHOST, PGPORT, PGDATABASE, PGUSER, PGPASSWORD（直接环境变量）
 * - 或 TSURU_SERVICES（JSON格式，包含所有服务信息）
 */
@Configuration
public class TsuruServiceConfig {

    private static final Logger logger = LoggerFactory.getLogger(TsuruServiceConfig.class);

    @Bean
    @Primary
    public DataSource dataSource() {
        String host = null;
        String port = null;
        String database = null;
        String user = null;
        String password = null;
        String configSource = "Default values";
        
        // 优先尝试从 TSURU_SERVICES 解析
        String tsuruServices = System.getenv("TSURU_SERVICES");
        
        if (tsuruServices != null && !tsuruServices.isEmpty()) {
            logger.info("检测到 TSURU_SERVICES 环境变量，尝试解析...");
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(tsuruServices);
                
                // 查找 postgres 服务
                JsonNode postgresServices = root.get("postgres");
                if (postgresServices != null && postgresServices.isArray() && postgresServices.size() > 0) {
                    JsonNode firstInstance = postgresServices.get(0);
                    JsonNode envs = firstInstance.get("envs");
                    
                    if (envs != null) {
                        host = getJsonValue(envs, "PGHOST");
                        port = getJsonValue(envs, "PGPORT");
                        database = getJsonValue(envs, "PGDATABASE");
                        user = getJsonValue(envs, "PGUSER");
                        password = getJsonValue(envs, "PGPASSWORD");
                        configSource = "TSURU_SERVICES";
                        
                        logger.info("从 TSURU_SERVICES 解析数据库配置成功: host={}, port={}, database={}, user={}",
                                host, port, database, user);
                    }
                } else {
                    logger.warn("TSURU_SERVICES 中未找到 postgres 服务配置");
                }
            } catch (Exception e) {
                logger.error("解析 TSURU_SERVICES 失败: {}", e.getMessage());
            }
        }
        
        // 如果 TSURU_SERVICES 没解析到，尝试直接读取环境变量
        if (host == null || host.isEmpty()) {
            String envHost = System.getenv("PGHOST");
            if (envHost != null && !envHost.isEmpty()) {
                host = envHost;
                port = getEnv("PGPORT", "5432");
                database = getEnv("PGDATABASE", "postgres");
                user = getEnv("PGUSER", "postgres");
                password = getEnv("PGPASSWORD", "");
                configSource = "Individual ENV vars";
                
                logger.info("从环境变量读取数据库配置: host={}, port={}, database={}, user={}",
                        host, port, database, user);
            }
        }
        
        // 最后回退到默认值（本地开发）
        if (host == null || host.isEmpty()) {
            host = "localhost";
            port = "5432";
            database = "postgres";
            user = "postgres";
            password = "123456";
            configSource = "Default values (localhost)";
            
            logger.info("使用默认本地配置: host={}, port={}, database={}, user={}",
                    host, port, database, user);
        }
        
        // 构建 JDBC URL
        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
        logger.info("最终数据库连接 URL: {} (来源: {})", jdbcUrl, configSource);
        
        // 创建 HikariCP 数据源
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(user);
        config.setPassword(password);
        config.setDriverClassName("org.postgresql.Driver");
        config.setConnectionTimeout(30000);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setInitializationFailTimeout(-1); // 允许启动时数据库不可用
        
        return new HikariDataSource(config);
    }
    
    private String getJsonValue(JsonNode envs, String key) {
        JsonNode node = envs.get(key);
        return node != null ? node.asText() : null;
    }
    
    private String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null && !value.isEmpty() ? value : defaultValue;
    }
}
