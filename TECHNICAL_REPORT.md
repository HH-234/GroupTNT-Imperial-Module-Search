# Technical Report: Architecture & Design

## 1. Introduction

This document outlines the technical architecture, core technologies, and design philosophy behind the Bioeng TSN App. The application is designed to provide information about Imperial College London personnel and courses, leveraging modern Java web development practices and AI integration.

## 2. Technology Stack & Core Technologies

### 2.1 Backend Core
- **Spring Boot 2.7**: The core framework used for rapid application development. It simplifies the configuration and deployment of Spring applications.
- **MyBatis Plus**: An enhanced toolkit for MyBatis to simplify development. It provides powerful CRUD operations without writing XML for basic queries.
- **PostgreSQL**: The relational database management system used for storing user and course data.

### 2.2 AI Integration
- **DeepSeek API**: Integrated via `RestTemplate` to provide an intelligent chat assistant.
- **Prompt Engineering**: Custom system prompts are used to enforce domain restrictions (ICL related topics only) and language constraints (English only).

## 3. Object-Oriented Design & Concepts

### 3.1 Encapsulation
The project heavily utilizes **Encapsulation** to maintain a clean separation of concerns and hide internal implementation details.
- **DTOs (Data Transfer Objects)**: Classes like `UserDetailDTO` and `CourseDetailDTO` encapsulate data transferred between the backend and frontend. They aggregate data from multiple entities (e.g., `Users` + `Addresses` + `PhoneNumbers`) into a single object, hiding the complexity of the underlying database structure from the API consumer.
- **Service Layer**: Business logic is encapsulated within Service classes (e.g., `UsersService`, `CoursesService`). Controllers do not know how data is fetched or processed; they simply request it from the Service.

### 3.2 Polymorphism
**Polymorphism** allows for flexible and interchangeable components.
- **Mapper Interfaces**: We define interfaces like `UsersMapper` that extend `BaseMapper`. The underlying implementation is generated dynamically by MyBatis Plus, allowing us to treat different data access objects uniformly.
- **Dependency Injection**: Spring's DI container injects implementations into interfaces (or classes). For example, `DataSource` is injected into `HealthController`. We interact with the abstract `DataSource` interface, not the specific implementation (HikariCP), allowing the underlying connection pool to be swapped without changing code.

### 3.3 MVC Architecture
The application follows the **Model-View-Controller (MVC)** architectural pattern:
- **Controller** (`com.zds.bioengtsnapp.controller`): Handles incoming HTTP requests and determines the response.
- **Model** (`com.zds.bioengtsnapp.domain`): Represents the data structure.
- **Service** (`com.zds.bioengtsnapp.service`): Contains business logic.
- **View**: In this RESTful API design, the "View" is the JSON response rendered to the frontend (or static HTML files in `static/`).

## 4. Spring Boot vs. Traditional Servlet Development

This project is built using Spring Boot, which offers significant advantages over traditional "Raw" Java EE (Servlet/JSP) development.

### 4.1 DispatcherServlet vs. Manual Servlet Mapping
- **Traditional**: In raw Servlet development, developers often need to write a separate Servlet class for every single endpoint or manually parse URLs in a monolithic Servlet. Configuration in `web.xml` can become unmanageable.
- **Spring Boot**: Uses a central `DispatcherServlet` (Front Controller pattern). It automatically routes requests to the appropriate `@Controller` methods based on annotations like `@GetMapping` or `@PostMapping`. This drastically reduces boilerplate code.

### 4.2 Database Connection Pooling
- **Traditional**: Developers might manually open and close JDBC connections using `DriverManager`. This is inefficient and prone to resource leaks. Implementing a connection pool manually is complex and error-prone.
- **Spring Boot**: Comes with **HikariCP** (a high-performance JDBC connection pool) pre-configured. It automatically manages a pool of connections, reusing them to improve performance and stability.

### 4.3 Auto-Configuration & Dependency Management
- **Traditional**: Requires manually downloading JAR files, adding them to the classpath, and resolving version conflicts (DLL/JAR Hell). Configuration often involves verbose XML files.
- **Spring Boot**:
  - **Starters**: `spring-boot-starter-web` pulls in all necessary dependencies (Tomcat, Spring MVC, Jackson, etc.) with compatible versions.
  - **Auto-Configuration**: Spring Boot scans the classpath and automatically configures beans. For example, if it sees a database driver, it attempts to configure a DataSource automatically.

### 4.4 Embedded Server
- **Traditional**: Requires installing an external Web Server (like Apache Tomcat), configuring it, and deploying a WAR file.
- **Spring Boot**: Includes an **Embedded Tomcat** server. The application creates a standalone JAR file that can be run with `java -jar`, making deployment and development much simpler.

### 4.5 Simplified JSON Handling
- **Traditional**: Requires manual serialization of Java objects to JSON strings using libraries like Gson or Jackson, and manually writing them to the `HttpServletResponse`.
- **Spring Boot**: With `@RestController`, return values are automatically serialized to JSON by the configured `HttpMessageConverter` (Jackson by default).

## 5. Conclusion

By adopting Spring Boot and MyBatis Plus, the Bioeng TSN App achieves a high level of maintainability, scalability, and development efficiency. The use of strict encapsulation and layered architecture ensures that the code remains clean and understandable, while modern tooling automates the repetitive aspects of web development.
