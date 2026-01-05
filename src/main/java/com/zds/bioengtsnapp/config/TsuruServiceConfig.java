package com.zds.bioengtsnapp.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 解析 Tsuru 的 TSURU_SERVICES 环境变量来配置数据源
 * 
 * TSURU_SERVICES 格式示例：
 * {
 *   "postgres": [{
 *     "instance_name": "db_bioengtsnapp",
 *     "envs": {
 *       "PGDATABASE": "...",
 *       "PGHOST": "...",
 *       "PGPASSWORD": "...",
 *       "PGPORT": "...",
 *       "PGUSER": "..."
 *     }
 *   }]
 * }
 */
@Configuration
public class TsuruServiceConfig {

    private static final Logger logger = LoggerFactory.getLogger(TsuruServiceConfig.class);

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties properties = new DataSourceProperties();
        
        // 尝试从 TSURU_SERVICES 解析
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
                        String host = getEnvValue(envs, "PGHOST");
                        String port = getEnvValue(envs, "PGPORT");
                        String database = getEnvValue(envs, "PGDATABASE");
                        String user = getEnvValue(envs, "PGUSER");
                        String password = getEnvValue(envs, "PGPASSWORD");
                        
                        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
                        
                        properties.setUrl(url);
                        properties.setUsername(user);
                        properties.setPassword(password);
                        properties.setDriverClassName("org.postgresql.Driver");
                        
                        logger.info("从 TSURU_SERVICES 解析数据库配置成功: host={}, port={}, database={}, user={}",
                                host, port, database, user);
                        return properties;
                    }
                }
                logger.warn("TSURU_SERVICES 中未找到 postgres 服务配置");
            } catch (Exception e) {
                logger.error("解析 TSURU_SERVICES 失败: {}", e.getMessage());
            }
        }
        
        // 回退：使用单独的环境变量或默认值
        String host = getEnv("PGHOST", "localhost");
        String port = getEnv("PGPORT", "5432");
        String database = getEnv("PGDATABASE", "postgres");
        String user = getEnv("PGUSER", "postgres");
        String password = getEnv("PGPASSWORD", "123456");
        
        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
        
        properties.setUrl(url);
        properties.setUsername(user);
        properties.setPassword(password);
        properties.setDriverClassName("org.postgresql.Driver");
        
        logger.info("使用环境变量配置数据库: host={}, port={}, database={}, user={}", 
                host, port, database, user);
        
        return properties;
    }
    
    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
    
    private String getEnvValue(JsonNode envs, String key) {
        JsonNode node = envs.get(key);
        return node != null ? node.asText() : null;
    }
    
    private String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null && !value.isEmpty() ? value : defaultValue;
    }
}

