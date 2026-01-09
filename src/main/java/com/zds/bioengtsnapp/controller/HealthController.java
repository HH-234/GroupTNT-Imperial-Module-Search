package com.zds.bioengtsnapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.Instant;
import java.util.*;

/**
 * Health check interface: Used to verify if the application is running normally,
 * if the database connection is normal, and list all APIs.
 */
@RestController
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private DataSource dataSource;

    /**
     * Complete heartbeat check interface
     * Returns: Application status, database connection status, all API paths
     */
    @GetMapping({"/health", "/healthz", "/heartbeat"})
    public Map<String, Object> health() {
        long startTime = System.currentTimeMillis();
        logger.info("Received heartbeat request");
        
        Map<String, Object> resp = new LinkedHashMap<>();

        // 1. Application Status
        resp.put("status", "running");
        resp.put("app", "bioeng-tsn-app");
        resp.put("time", Instant.now().toString());
        resp.put("javaVersion", System.getProperty("java.version"));
        resp.put("osName", System.getProperty("os.name"));

        // 2. Database Connection Check
        long dbCheckStart = System.currentTimeMillis();
        Map<String, Object> dbStatus = checkDatabaseConnection();
        long dbCheckDuration = System.currentTimeMillis() - dbCheckStart;
        logger.info("Database check completed in {} ms, status: {}", dbCheckDuration, dbStatus.get("connected"));
        resp.put("database", dbStatus);

        // 3. All API Paths
        long apiCheckStart = System.currentTimeMillis();
        List<Map<String, String>> apiList = getAllApiEndpoints();
        long apiCheckDuration = System.currentTimeMillis() - apiCheckStart;
        logger.info("API endpoints discovery completed in {} ms, count: {}", apiCheckDuration, apiList.size());
        
        resp.put("apiCount", apiList.size());
        resp.put("apis", apiList);

        // 4. Environment Variables (Only show existence, do not expose values)
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
     * Simple Liveness Check (For Load Balancer)
     */
    @GetMapping("/ping")
    public Map<String, String> ping() {
        Map<String, String> result = new HashMap<>();
        result.put("pong", Instant.now().toString());
        return result;
    }

    /**
     * Check Database Connection - Using Spring Boot's DataSource
     */
    private Map<String, Object> checkDatabaseConnection() {
        Map<String, Object> dbStatus = new LinkedHashMap<>();
        
        try (Connection conn = dataSource.getConnection()) {
            dbStatus.put("connected", true);
            dbStatus.put("message", "Database connection successful");
            dbStatus.put("databaseProductName", conn.getMetaData().getDatabaseProductName());
            dbStatus.put("databaseProductVersion", conn.getMetaData().getDatabaseProductVersion());
            dbStatus.put("url", conn.getMetaData().getURL());
            
            logger.info("Database connection successful: {}", conn.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            dbStatus.put("connected", false);
            dbStatus.put("message", "Database connection failed");
            dbStatus.put("error", e.getMessage());
            logger.error("Database connection failed: {}", e.getMessage());
        }
        return dbStatus;
    }

    /**
     * Get all registered API endpoints
     */
    private List<Map<String, String>> getAllApiEndpoints() {
        List<Map<String, String>> apiList = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();

            // Get Path - Compatible with Spring Boot 2.7+
            Set<String> patterns = new HashSet<>();
            
            // Try using PathPatternsCondition (Spring 5.3+)
            if (mappingInfo.getPathPatternsCondition() != null) {
                mappingInfo.getPathPatternsCondition().getPatterns().forEach(p -> patterns.add(p.getPatternString()));
            }
            // Fallback to PatternsCondition
            else if (mappingInfo.getPatternsCondition() != null) {
                patterns.addAll(mappingInfo.getPatternsCondition().getPatterns());
            }

            // If still empty, skip
            if (patterns.isEmpty()) {
                continue;
            }

            // Get HTTP Method
            Set<org.springframework.web.bind.annotation.RequestMethod> methods =
                    mappingInfo.getMethodsCondition().getMethods();
            String httpMethods = methods.isEmpty() ? "ALL" :
                    methods.stream().map(Enum::name).reduce((a, b) -> a + ", " + b).orElse("");

            // Get Controller and Method Name
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

        // Sort by path
        apiList.sort(Comparator.comparing(a -> a.get("path")));

        return apiList;
    }
}
