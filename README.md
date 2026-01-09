# Bioeng TSN App

Bioeng TSN App is a web application based on Spring Boot, designed to provide personnel profile queries, course queries, and intelligent AI Q&A services for Imperial College London.

## 🚀 Features

- **People Search**: 
  - Support searching personnel profiles by name.
  - Display detailed information including address, contact number, etc.
  - Adopt card layout with 3D flip animation effects.
  - Direct links to official Imperial Profiles pages.

- **Course Search**:
  - Browse course information and their included modules.

- **AI Chat Assistant**:
  - Integrated with DeepSeek AI model.
  - **Mandatory English Response**: The AI answers in English regardless of the language used by the user.
  - **Domain Restriction**: Focused on answering questions related to Imperial College London.
  - **Markdown Support**: Chat interface supports Markdown rendering.
  - **Interaction Optimization**: Supports draggable and zoomable floating chat window.

## 🛠 Tech Stack

- **Backend**: Java, Spring Boot 2.7.18, MyBatis Plus, Spring WebFlux/RestTemplate
- **Database**: PostgreSQL
- **Frontend**: HTML5, CSS3 (Grid, Flexbox, Animations), Vanilla JavaScript, Marked.js
- **Build Tool**: Maven

## 📋 Prerequisites

- JDK 8 or higher (JDK 17 recommended)
- Maven 3.6+
- PostgreSQL 13+ (or use Docker)

## ⚙️ Setup & Run

### 1. Run Database using Docker (Recommended)

1. Ensure Docker and Docker Compose are installed and running.
2. Start PostgreSQL database in the project root directory:

```bash
docker-compose up -d
```

3. Wait for the database container to start and initialize:

```bash
docker-compose logs postgres
```

### 2. Database Setup

If using Docker Compose, the database and table structures are created automatically. If creating manually:

1. Create PostgreSQL database `imperial_profiles`.
2. Execute `sql/schema.sql` script to create table structures.

```bash
# Example command (adjust according to actual environment)
psql -U postgres -d imperial_profiles -f sql/schema.sql
```

### 3. Configuration

Open `src/main/resources/application.yml` file and modify the following configurations according to your local environment:

```yaml
spring:
  datasource:
    # Modify database URL, port and database name
    url: jdbc:postgresql://localhost:5432/imperial_profiles?currentSchema=public
    # Modify database username and password
    username: postgres
    password: 123456

server:
  # Application port
  port: 8081

deepseek:
  api:
    # DeepSeek API Key
    key: your_api_key_here
```

### 4. Start Application

Run the following command in the project root directory to start the application:

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

### 5. Start with In-Memory Database (For Development)

If you don't want to install PostgreSQL, you can use H2 in-memory database for development:

```bash
# Windows - Use dev profile
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

# Linux/macOS - Use dev profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

When using dev configuration, the application will automatically use H2 in-memory database, and the H2 console can be accessed via:
[http://localhost:8081/h2-console](http://localhost:8081/h2-console)

### 6. Access Application

After successful startup, open browser and visit:

[http://localhost:8081](http://localhost:8081)

## 📂 Project Structure

```text
bioeng-tsn-app/
├── sql/                    # Database SQL scripts
├── src/
│   ├── main/
│   │   ├── java/           # Java Source Code (Controller, Service, Mapper, Entity)
│   │   └── resources/
│   │       ├── generator/  # MyBatis Generator mapping files
│   │       ├── static/     # Static Resources (index.html, css, js)
│   │       └── application.yml # Application Configuration
└── pom.xml                 # Maven Dependency Configuration
```

## 📝 Notes

- **AI Restrictions**: The system is configured with Prompts to limit AI to answer only positive content related to Imperial College London, and must use English.
- **Port Conflict**: If port 8081 is occupied, please modify `server.port` in `application.yml`.
- **API Key**: Please ensure DeepSeek API Key is valid and has sufficient quota.
