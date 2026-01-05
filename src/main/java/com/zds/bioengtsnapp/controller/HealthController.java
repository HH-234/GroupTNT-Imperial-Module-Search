package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.config.DatabaseConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.sql.Connection;
import java.time.Instant;
import java.util.*;

/**
 * 健康检查接口：用于验证应用是否正常启动、数据库是否连接正常、列出所有API
 */
@RestController
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 完整心跳检查接口
     * 返回：应用状态、数据库连接状态、所有API路径
     */
    @GetMapping({"/health", "/healthz", "/heartbeat"})
    public Map<String, Object> health() {
        long startTime = System.currentTimeMillis();
        logger.info("Received heartbeat request");
        
        Map<String, Object> resp = new LinkedHashMap<>();

        // 1. 应用状态
        resp.put("status", "running");
        resp.put("app", "bioeng-tsn-app");
        resp.put("time", Instant.now().toString());
        resp.put("javaVersion", System.getProperty("java.version"));
        resp.put("osName", System.getProperty("os.name"));

        // 2. 数据库连接检查
        long dbCheckStart = System.currentTimeMillis();
        Map<String, Object> dbStatus = checkDatabaseConnection();
        long dbCheckDuration = System.currentTimeMillis() - dbCheckStart;
        logger.info("Database check completed in {} ms, status: {}", dbCheckDuration, dbStatus.get("connected"));
        resp.put("database", dbStatus);

        // 3. 所有API路径
        long apiCheckStart = System.currentTimeMillis();
        List<Map<String, String>> apiList = getAllApiEndpoints();
        long apiCheckDuration = System.currentTimeMillis() - apiCheckStart;
        logger.info("API endpoints discovery completed in {} ms, count: {}", apiCheckDuration, apiList.size());
        
        resp.put("apiCount", apiList.size());
        resp.put("apis", apiList);

        // 4. 环境变量（仅显示是否存在，不暴露值）
        Map<String, Boolean> envMap = new HashMap<>();
        envMap.put("PGHOST", System.getenv("PGHOST") != null);
        envMap.put("PGPORT", System.getenv("PGPORT") != null);
        envMap.put("PGDATABASE", System.getenv("PGDATABASE") != null);
        envMap.put("PGUSER", System.getenv("PGUSER") != null);
        envMap.put("PGPASSWORD", System.getenv("PGPASSWORD") != null);
        envMap.put("PORT", System.getenv("PORT") != null);
        resp.put("env", envMap);

        long totalDuration = System.currentTimeMillis() - startTime;
        logger.info("Heartbeat request processed in {} ms", totalDuration);
        return resp;
    }

    /**
     * 简单存活检查（用于负载均衡器）
     */
    @GetMapping("/ping")
    public Map<String, String> ping() {
        Map<String, String> result = new HashMap<>();
        result.put("pong", Instant.now().toString());
        return result;
    }

    /**
     * 检查数据库连接 - 使用 DatabaseConnectionUtil 工具类
     */
    private Map<String, Object> checkDatabaseConnection() {
        Map<String, Object> dbStatus = new LinkedHashMap<>();
        
        // 检查环境变量是否配置完整
        if (!DatabaseConnectionUtil.isConfigured()) {
            dbStatus.put("connected", false);
            dbStatus.put("message", "Missing database environment variables");
            dbStatus.put("error", DatabaseConnectionUtil.getConfigStatus());
            return dbStatus;
        }
        
        String jdbcUrl = DatabaseConnectionUtil.getJdbcUrl();
        logger.info("Attempting database connection to: {}", jdbcUrl);
        
        Connection conn = null;
        try {
            // 使用 DatabaseConnectionUtil 获取连接
            conn = DatabaseConnectionUtil.getConnection();
            
            dbStatus.put("connected", true);
            dbStatus.put("message", "Database connection successful");
            dbStatus.put("databaseProductName", conn.getMetaData().getDatabaseProductName());
            dbStatus.put("databaseProductVersion", conn.getMetaData().getDatabaseProductVersion());
            dbStatus.put("url", jdbcUrl);
            
            logger.info("Database connection successful: {}", conn.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            dbStatus.put("connected", false);
            dbStatus.put("message", "Database connection failed");
            dbStatus.put("error", e.getMessage());
            logger.error("Database connection failed: {}", e.getMessage());
        } finally {
            // 关闭连接
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Failed to close database connection", e);
                }
            }
        }
        return dbStatus;
    }

    /**
     * 获取所有已注册的API端点
     */
    private List<Map<String, String>> getAllApiEndpoints() {
        List<Map<String, String>> apiList = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();

            // 获取路径 - 兼容 Spring Boot 2.7+
            Set<String> patterns = new HashSet<>();
            
            // 尝试使用 PathPatternsCondition (Spring 5.3+)
            if (mappingInfo.getPathPatternsCondition() != null) {
                mappingInfo.getPathPatternsCondition().getPatterns().forEach(p -> patterns.add(p.getPatternString()));
            }
            // 回退到 PatternsCondition
            else if (mappingInfo.getPatternsCondition() != null) {
                patterns.addAll(mappingInfo.getPatternsCondition().getPatterns());
            }

            // 如果仍然为空，跳过
            if (patterns.isEmpty()) {
                continue;
            }

            // 获取HTTP方法
            Set<org.springframework.web.bind.annotation.RequestMethod> methods =
                    mappingInfo.getMethodsCondition().getMethods();
            String httpMethods = methods.isEmpty() ? "ALL" :
                    methods.stream().map(Enum::name).reduce((a, b) -> a + ", " + b).orElse("");

            // 获取控制器和方法名
            String controller = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();

            for (String pattern : patterns) {
                Map<String, String> api = new LinkedHashMap<>();
                api.put("path", pattern);
                api.put("method", httpMethods);
                api.put("controller", controller);
                api.put("handler", methodName);
                apiList.add(api);
            }
        }

        // 按路径排序
        apiList.sort(Comparator.comparing(a -> a.get("path")));

        return apiList;
    }
}
