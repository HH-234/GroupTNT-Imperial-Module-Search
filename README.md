# Bioeng TSN App

Bioeng TSN App 是一个基于 Spring Boot 开发的 Web 应用程序，旨在提供帝国理工学院（Imperial College London）的人员档案查询、课程查询以及智能 AI 问答服务。

## 🚀 功能特性 (Features)

- **人员搜索 (People Search)**: 
  - 支持按姓名搜索人员档案。
  - 展示详细信息，包括地址、联系电话等。
  - 采用卡片式布局，支持 3D 翻转动画效果。
  - 直接链接到 Imperial Profiles 官方页面。

- **课程搜索 (Course Search)**:
  - 浏览课程信息及其包含的模块 (Modules).

- **AI 智能助手 (AI Chat Assistant)**:
  - 集成 DeepSeek AI 模型。
  - **强制英文回复**: 无论用户使用何种语言提问，AI 均以英文回答。
  - **领域限制**: 专注于回答关于帝国理工学院的问题。
  - **Markdown 支持**: 聊天界面支持 Markdown 格式渲染。
  - **交互优化**: 支持拖拽、缩放的悬浮聊天窗口。

## 🛠 技术栈 (Tech Stack)

- **后端**: Java 17, Spring Boot 3.5.9, MyBatis Plus, Spring WebFlux
- **数据库**: PostgreSQL
- **前端**: HTML5, CSS3 (Grid, Flexbox, Animations), Vanilla JavaScript, Marked.js
- **构建工具**: Maven

## 📋 环境要求 (Prerequisites)

- JDK 17 或更高版本
- Maven 3.6+
- PostgreSQL 13+ (或使用 Docker)

## ⚙️ 配置与启动 (Setup & Run)

### 1. 使用 Docker 运行数据库 (Recommended)

1. 确保 Docker 和 Docker Compose 已安装并运行。
2. 在项目根目录下启动 PostgreSQL 数据库：

```bash
docker-compose up -d
```

3. 等待数据库容器启动并初始化：

```bash
docker-compose logs postgres
```

### 2. 数据库准备 (Database Setup)

如果使用 Docker Compose，数据库和表结构会自动创建。如需手动创建：

1. 创建 PostgreSQL 数据库 `imperial_profiles`。
2. 执行 `sql/schema.sql` 脚本以创建表结构。

```bash
# 示例命令 (根据实际环境调整)
psql -U postgres -d imperial_profiles -f sql/schema.sql
```

### 3. 修改配置 (Configuration)

打开 `src/main/resources/application.yml` 文件，根据本地环境修改以下配置：

```yaml
spring:
  datasource:
    # 修改数据库连接地址、端口和数据库名
    url: jdbc:postgresql://localhost:5432/imperial_profiles?currentSchema=public
    # 修改数据库用户名和密码
    username: postgres
    password: 123456

server:
  # 应用启动端口
  port: 8081

deepseek:
  api:
    # DeepSeek API Key
    key: your_api_key_here
```

### 4. 启动应用 (Start Application)

在项目根目录下运行以下命令启动应用：

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

### 5. 使用内存数据库启动 (For Development)

如果不想安装 PostgreSQL，可以使用 H2 内存数据库进行开发：

```bash
# Windows - 使用开发配置文件
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

# Linux/macOS - 使用开发配置文件
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

当使用开发配置时，应用程序会自动使用 H2 内存数据库，并可通过以下 URL 访问 H2 控制台：
[http://localhost:8081/h2-console](http://localhost:8081/h2-console)

### 6. 访问应用 (Access)

应用启动成功后，打开浏览器访问：

[http://localhost:8081](http://localhost:8081)

## 📂 项目结构 (Project Structure)

```text
bioeng-tsn-app/
├── sql/                    # 数据库 SQL 脚本
├── src/
│   ├── main/
│   │   ├── java/           # Java 源代码 (Controller, Service, Mapper, Entity)
│   │   └── resources/
│   │       ├── generator/  # MyBatis Generator 映射文件
│   │       ├── static/     # 静态资源 (index.html, css, js)
│   │       └── application.yml # 应用配置文件
└── pom.xml                 # Maven 依赖配置
```

## 📝 注意事项 (Notes)

- **AI 限制**: 系统已配置 Prompt 限制 AI 只能回答与帝国理工学院相关的正面内容，并且必须使用英文。
- **端口占用**: 如果 8081 端口被占用，请在 `application.yml` 中修改 `server.port`。
- **API Key**: 请确保 DeepSeek API Key 有效且有足够的额度。