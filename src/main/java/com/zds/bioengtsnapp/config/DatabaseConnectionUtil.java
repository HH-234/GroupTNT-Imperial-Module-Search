package com.zds.bioengtsnapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接工具类 - 使用原生 JDBC 连接
 * 从环境变量获取连接信息：PGHOST, PGPORT, PGDATABASE, PGUSER, PGPASSWORD
 */
public class DatabaseConnectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionUtil.class);

    // 数据库连接信息（从环境变量获取）
    private static String pgHost;
    private static String pgPort;
    private static String pgDatabase;
    private static String pgUser;
    private static String pgPassword;

    // 静态初始化块，加载环境变量
    static {
        loadEnvironmentVariables();
    }

    /**
     * 从环境变量加载数据库连接信息
     */
    private static void loadEnvironmentVariables() {
        pgHost = System.getenv("PGHOST");
        pgPort = System.getenv("PGPORT");
        pgDatabase = System.getenv("PGDATABASE");
        pgUser = System.getenv("PGUSER");
        pgPassword = System.getenv("PGPASSWORD");

        logger.info("Database connection config loaded from environment variables:");
        logger.info("  PGHOST: {}", pgHost != null ? pgHost : "(not set)");
        logger.info("  PGPORT: {}", pgPort != null ? pgPort : "(not set)");
        logger.info("  PGDATABASE: {}", pgDatabase != null ? pgDatabase : "(not set)");
        logger.info("  PGUSER: {}", pgUser != null ? pgUser : "(not set)");
        logger.info("  PGPASSWORD: {}", pgPassword != null ? "(set)" : "(not set)");
    }

    /**
     * 获取数据库连接 URL
     */
    public static String getJdbcUrl() {
        if (pgHost == null || pgPort == null || pgDatabase == null) {
            return null;
        }
        return "jdbc:postgresql://" + pgHost + ":" + pgPort + "/" + pgDatabase;
    }

    /**
     * 获取数据库连接
     * @return Connection 数据库连接对象
     * @throws SQLException 如果连接失败
     */
    public static Connection getConnection() throws SQLException {
        // 检查必需的环境变量
        if (pgHost == null || pgPort == null || pgDatabase == null || pgUser == null || pgPassword == null) {
            throw new SQLException("Missing required database environment variables (PGHOST, PGPORT, PGDATABASE, PGUSER, PGPASSWORD)");
        }

        String dbUrl = "jdbc:postgresql://" + pgHost + ":" + pgPort + "/" + pgDatabase;
        
        logger.debug("Connecting to database: {}", dbUrl);
        
        return DriverManager.getConnection(dbUrl, pgUser, pgPassword);
    }

    /**
     * 测试数据库连接
     * @return true 如果连接成功，false 否则
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            logger.error("Database connection test failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 检查环境变量是否完整
     * @return true 如果所有必需的环境变量都已设置
     */
    public static boolean isConfigured() {
        return pgHost != null && pgPort != null && pgDatabase != null && pgUser != null && pgPassword != null;
    }

    /**
     * 获取环境变量状态信息
     */
    public static String getConfigStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("PGHOST=").append(pgHost != null ? pgHost : "NULL");
        sb.append(", PGPORT=").append(pgPort != null ? pgPort : "NULL");
        sb.append(", PGDATABASE=").append(pgDatabase != null ? pgDatabase : "NULL");
        sb.append(", PGUSER=").append(pgUser != null ? pgUser : "NULL");
        sb.append(", PGPASSWORD=").append(pgPassword != null ? "***" : "NULL");
        return sb.toString();
    }

    // Getters for individual environment variables
    public static String getPgHost() { return pgHost; }
    public static String getPgPort() { return pgPort; }
    public static String getPgDatabase() { return pgDatabase; }
    public static String getPgUser() { return pgUser; }
    public static String getPgPassword() { return pgPassword; }
}

