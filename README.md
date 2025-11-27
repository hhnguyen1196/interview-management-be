# Interview Management – Backend (Spring Boot)

Backend service for an **Interview Management** system built with **Java**, **Spring Boot**, and **MySQL**.  
It exposes REST APIs for managing candidates, interviews and related data.

---

## 1. Tech Stack

- **Language:** Java (JDK 17+ recommended)
- **Framework:** Spring Boot
- **Build Tool:** Maven
- **Database:** MySQL
- **Security:** Spring Security & JWT (see `security` package)
- **Others:** Lombok, Spring Data JPA, Validation (Jakarta/hibernate) – depending on your `pom.xml`

---

## 2. Project Structure

```text
.
├── .mvn/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── assignment.interview_management
│   │   │       ├── config/        # Global configs
│   │   │       ├── controller/    # REST controllers (HTTP endpoints)
│   │   │       ├── dto/           # Data Transfer Objects used by controllers/services
│   │   │       ├── entity/        # JPA entities mapped to MySQL tables
│   │   │       ├── enums/         # Application enums (roles, statuses, etc.)
│   │   │       ├── exceptions/    # Custom exceptions & global handlers
│   │   │       ├── repository/    # Spring Data JPA repositories
│   │   │       ├── security/      # Security config, filters, JWT utilities
│   │   │       ├── service/       # Business logic
│   │   │       ├── utils/         # Helpers, constants, mappers
│   │   │       └── InterviewManagementBeApplication.java  # Main Spring Boot class
│   │   └── resources
│   │       ├── templates/         # Email / HTML templates (if used)
│   │       ├── uploads/           # File upload storage (local)
│   │       └── application.yml    # Environment & DB configuration
│   └── test
└── target/                         # Build output (generated)
```

---

## 3. Prerequisites

- Java JDK 17+
- Maven 3.8+
- MySQL 8+ (or compatible)

> [NOTE]
>
>Make sure MySQL is running and you have a database created.
>
>Query to create and update database is as defined in interview-management.sql

---

## 4. Build & Run

From the project root:
```bash
# Clean and build
mvn clean install
```

```bash
# Run the Spring Boot application
mvn spring-boot:run
```
