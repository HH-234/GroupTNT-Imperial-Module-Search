package com.zds.bioengtsnapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 数据库初始化器
 * 在应用启动时自动创建表结构并插入初始数据（如果表为空）
 */
@Slf4j  
@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            log.info("开始初始化数据库...");
            
            // 检查数据库连接
            checkDatabaseConnection();
            
            // 创建表结构
            createTablesIfNotExists();
            
            // 插入初始数据（如果表为空）
            insertInitialDataIfEmpty();
            
            log.info("数据库初始化完成！");
        } catch (Exception e) {
            log.error("数据库初始化失败", e);
            // 不抛出异常，允许应用继续启动（可能表已存在）
        }
    }

    /**
     * 检查数据库连接
     */
    private void checkDatabaseConnection() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            log.info("数据库连接成功");
            
            // 打印数据库环境变量信息（不打印密码）
            String pghost = System.getenv("PGHOST");
            String pgport = System.getenv("PGPORT");
            String pgdatabase = System.getenv("PGDATABASE");
            String pguser = System.getenv("PGUSER");
            boolean hasPassword = System.getenv("PGPASSWORD") != null;
            
            log.info("数据库配置 - PGHOST: {}, PGPORT: {}, PGDATABASE: {}, PGUSER: {}, PGPASSWORD: {}",
                    pghost != null ? pghost : "未设置（使用默认值）",
                    pgport != null ? pgport : "未设置（使用默认值）",
                    pgdatabase != null ? pgdatabase : "未设置（使用默认值）",
                    pguser != null ? pguser : "未设置（使用默认值）",
                    hasPassword ? "已设置" : "未设置（使用默认值）");
        } catch (Exception e) {
            log.warn("数据库连接检查失败: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 创建表结构（如果不存在）
     */
    private void createTablesIfNotExists() throws IOException {
        try {
            log.info("检查并创建表结构...");
            
            // 读取 schema.sql 文件（从 resources/sql/ 或直接使用内嵌 SQL）
            ClassPathResource resource = new ClassPathResource("sql/schema.sql");
            if (!resource.exists()) {
                // 如果文件不存在，使用内嵌的 SQL 语句
                log.warn("未找到 sql/schema.sql 文件，使用内嵌 SQL 创建表");
                createTablesWithEmbeddedSQL();
                return;
            }
            
            String schemaSql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            
            // 分割 SQL 语句（按分号和换行）
            String[] statements = schemaSql.split(";");
            
            int createdTables = 0;
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("--")) {
                    continue;
                }
                
                // 跳过 DROP TABLE 语句（使用 CREATE TABLE IF NOT EXISTS 代替）
                if (trimmed.toUpperCase().startsWith("DROP TABLE")) {
                    log.debug("跳过 DROP TABLE 语句");
                    continue;
                }
                
                // 将 CREATE TABLE 替换为 CREATE TABLE IF NOT EXISTS
                if (trimmed.toUpperCase().contains("CREATE TABLE") && !trimmed.toUpperCase().contains("IF NOT EXISTS")) {
                    trimmed = trimmed.replaceFirst("(?i)CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
                }
                
                // 将 CREATE INDEX 替换为 CREATE INDEX IF NOT EXISTS
                if (trimmed.toUpperCase().contains("CREATE INDEX") && !trimmed.toUpperCase().contains("IF NOT EXISTS")) {
                    trimmed = trimmed.replaceFirst("(?i)CREATE INDEX", "CREATE INDEX IF NOT EXISTS");
                }
                
                try {
                    // 执行建表语句
                    jdbcTemplate.execute(trimmed);
                    if (trimmed.toUpperCase().contains("CREATE TABLE")) {
                        createdTables++;
                    }
                } catch (Exception e) {
                    // 如果表已存在，忽略错误
                    if (e.getMessage() != null && (e.getMessage().contains("already exists") || e.getMessage().contains("duplicate"))) {
                        log.debug("表或索引已存在，跳过");
                    } else {
                        log.warn("执行 SQL 语句时出错: {}", e.getMessage());
                    }
                }
            }
            
            if (createdTables > 0) {
                log.info("成功创建 {} 个表", createdTables);
            } else {
                log.info("所有表已存在，无需创建");
            }
        } catch (Exception e) {
            log.error("创建表结构失败", e);
            throw e;
        }
    }

    /**
     * 使用内嵌 SQL 创建表（当 schema.sql 文件不存在时使用）
     */
    private void createTablesWithEmbeddedSQL() {
        try {
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    discovery_url_id TEXT,
                    avatar_url TEXT,
                    first_name TEXT,
                    last_name TEXT,
                    full_name TEXT,
                    email_address TEXT,
                    created_at TIMESTAMP,
                    updated_at TIMESTAMP
                )
                """;
            
            String createAddressesTable = """
                CREATE TABLE IF NOT EXISTS addresses (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER,
                    country_code TEXT,
                    single_line_format TEXT,
                    street_address TEXT,
                    city TEXT,
                    state TEXT,
                    country TEXT,
                    CONSTRAINT fk_addresses_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
                """;
            
            String createPhoneNumbersTable = """
                CREATE TABLE IF NOT EXISTS phone_numbers (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER,
                    type_display_name TEXT,
                    number TEXT,
                    CONSTRAINT fk_phone_numbers_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
                """;
            
            String createCoursesTable = """
                CREATE TABLE IF NOT EXISTS courses (
                    id SERIAL PRIMARY KEY,
                    course_name TEXT,
                    course_url TEXT,
                    qualification TEXT,
                    duration TEXT,
                    start_date TEXT,
                    ucas_code TEXT,
                    study_mode TEXT,
                    fee_home TEXT,
                    fee_overseas TEXT,
                    delivered_by TEXT,
                    location TEXT,
                    applications_places TEXT,
                    entry_requirement_alevel TEXT,
                    entry_requirement_ib TEXT,
                    description TEXT,
                    created_at TIMESTAMP,
                    updated_at TIMESTAMP
                )
                """;
            
            String createCourseModulesTable = """
                CREATE TABLE IF NOT EXISTS course_modules (
                    id SERIAL PRIMARY KEY,
                    course_id INTEGER,
                    year_number INTEGER,
                    module_type TEXT,
                    module_name TEXT,
                    CONSTRAINT fk_course_modules_course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
                )
                """;
            
            // 创建索引
            String createIndexes = """
                CREATE INDEX IF NOT EXISTS idx_addresses_user_id ON addresses(user_id);
                CREATE INDEX IF NOT EXISTS idx_phone_numbers_user_id ON phone_numbers(user_id);
                CREATE INDEX IF NOT EXISTS idx_course_modules_course_id ON course_modules(course_id);
                CREATE INDEX IF NOT EXISTS idx_users_full_name ON users(full_name);
                CREATE INDEX IF NOT EXISTS idx_users_discovery_url_id ON users(discovery_url_id);
                """;
            
            jdbcTemplate.execute(createUsersTable);
            jdbcTemplate.execute(createAddressesTable);
            jdbcTemplate.execute(createPhoneNumbersTable);
            jdbcTemplate.execute(createCoursesTable);
            jdbcTemplate.execute(createCourseModulesTable);
            jdbcTemplate.execute(createIndexes);
            
            log.info("使用内嵌 SQL 成功创建表结构");
        } catch (Exception e) {
            log.error("使用内嵌 SQL 创建表结构失败", e);
        }
    }

    /**
     * 插入初始数据（如果表为空）
     */
    private void insertInitialDataIfEmpty() {
        try {
            log.info("检查并插入初始数据...");
            
            // 检查 users 表是否有数据
            Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            if (userCount == null || userCount == 0) {
                log.info("users 表为空，插入示例数据...");
                insertSampleUsers();
            } else {
                log.info("users 表已有 {} 条数据，跳过插入", userCount);
            }
            
            // 检查 courses 表是否有数据
            Integer courseCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM courses", Integer.class);
            if (courseCount == null || courseCount == 0) {
                log.info("courses 表为空，插入示例数据...");
                insertSampleCourses();
            } else {
                log.info("courses 表已有 {} 条数据，跳过插入", courseCount);
            }
            
        } catch (Exception e) {
            log.warn("插入初始数据时出错（可能表不存在）: {}", e.getMessage());
        }
    }

    /**
     * 插入示例用户数据
     */
    private void insertSampleUsers() {
        try {
            // 插入几个示例用户
            jdbcTemplate.update(
                "INSERT INTO users (discovery_url_id, first_name, last_name, full_name, email_address) VALUES (?, ?, ?, ?, ?)",
                "example-1", "John", "Smith", "John Smith", "john.smith@imperial.ac.uk"
            );
            
            jdbcTemplate.update(
                "INSERT INTO users (discovery_url_id, first_name, last_name, full_name, email_address) VALUES (?, ?, ?, ?, ?)",
                "example-2", "Jane", "Doe", "Jane Doe", "jane.doe@imperial.ac.uk"
            );
            
            log.info("成功插入 2 条示例用户数据");
        } catch (Exception e) {
            log.warn("插入示例用户数据失败: {}", e.getMessage());
        }
    }

    /**
     * 插入示例课程数据
     */
    private void insertSampleCourses() {
        try {
            // 插入几个示例课程
            jdbcTemplate.update(
                "INSERT INTO courses (course_name, course_url, qualification, duration, start_date, study_mode, delivered_by, location, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "Biomedical Engineering",
                "https://www.imperial.ac.uk/study/courses/undergraduate/2026/biomedical-engineering/",
                "MEng",
                "5 Years",
                "October 2026",
                "Full-time",
                "Department of Bioengineering",
                "South Kensington",
                "Develop a breadth and depth of engineering skills and knowledge to address problems in medicine and biology."
            );
            
            jdbcTemplate.update(
                "INSERT INTO courses (course_name, course_url, qualification, duration, start_date, study_mode, delivered_by, location, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "Computing",
                "https://www.imperial.ac.uk/study/courses/undergraduate/2026/computing-meng/",
                "MEng",
                "4 years",
                "October 2026",
                "Full-time",
                "Department of Computing",
                "South Kensington",
                "Take your study of computing further with advanced modules and an industrial placement in this integrated Master's degree."
            );
            
            log.info("成功插入 2 条示例课程数据");
        } catch (Exception e) {
            log.warn("插入示例课程数据失败: {}", e.getMessage());
        }
    }
}

