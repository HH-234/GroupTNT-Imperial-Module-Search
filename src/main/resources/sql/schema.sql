-- 删除已存在的表（如果存在）
DROP TABLE IF EXISTS course_modules;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS phone_numbers;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS users;

-- 创建 users 表
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    discovery_url_id TEXT,
    avatar_url TEXT,
    first_name TEXT,
    last_name TEXT,
    full_name TEXT,
    email_address TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- 创建 addresses 表
CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    country_code TEXT,
    single_line_format TEXT,
    street_address TEXT,
    city TEXT,
    state TEXT,
    country TEXT,
    CONSTRAINT fk_addresses_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 创建 phone_numbers 表
CREATE TABLE phone_numbers (
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    type_display_name TEXT,
    number TEXT,
    CONSTRAINT fk_phone_numbers_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 创建 courses 表
CREATE TABLE courses (
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
);

-- 创建 course_modules 表
CREATE TABLE course_modules (
    id SERIAL PRIMARY KEY,
    course_id INTEGER,
    year_number INTEGER,
    module_type TEXT,
    module_name TEXT,
    CONSTRAINT fk_course_modules_course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);

-- 创建索引以提高查询性能
CREATE INDEX idx_addresses_user_id ON addresses(user_id);
CREATE INDEX idx_phone_numbers_user_id ON phone_numbers(user_id);
CREATE INDEX idx_course_modules_course_id ON course_modules(course_id);
CREATE INDEX idx_users_full_name ON users(full_name);
CREATE INDEX idx_users_discovery_url_id ON users(discovery_url_id);
