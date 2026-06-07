# 📚 Learnings — FirstClub Membership Project

All questions and answers documented while building this project from scratch.
Use this as a quick reference before interviews or demos.

---

## 🚀 Quick Start Commands

```bash
# Compile all source files (checks for errors)
mvn compile

# Compile fresh — deletes target/ first, then compiles
mvn clean compile

# Run the app (compiles + starts on localhost:8080)
mvn spring-boot:run

# Clean + run (recommended — avoids stale cache issues)
mvn clean spring-boot:run

# Run all tests
mvn test

# Package into a runnable JAR file (target/firstclub-0.0.1-SNAPSHOT.jar)
mvn package

# Skip tests while packaging
mvn package -DskipTests

# Run the packaged JAR directly
java -jar target/firstclub-0.0.1-SNAPSHOT.jar

# Delete the target/ folder (compiled output)
mvn clean

# Download all dependencies to local Maven cache (~/.m2)
mvn dependency:resolve

# See the full dependency tree
mvn dependency:tree
```

---

## 🌐 URLs (when app is running)

| URL | Purpose |
|-----|---------|
| `http://localhost:8080/swagger-ui/index.html` | Swagger UI — test all APIs |
| `http://localhost:8080/api-docs` | Raw OpenAPI JSON spec |
| `http://localhost:8080/h2-console` | H2 DB browser (JDBC URL: `jdbc:h2:mem:membershipdb`) |
| `http://localhost:8080/api/membership/thread-info` | Prove virtual threads are active |

---

## 📁 Project Structure

```
src/
└── main/
    ├── java/com/firstclub/membership/
    │   ├── MembershipApplication.java   ← entry point
    │   ├── config/                      ← AppConfig (Virtual Threads)
    │   ├── controller/                  ← REST API layer
    │   ├── service/                     ← business logic
    │   ├── repository/                  ← DB queries
    │   ├── model/                       ← JPA entities (DB tables)
    │   ├── dto/                         ← request/response objects
    │   ├── enums/                       ← PlanType, TierType, etc.
    │   └── exception/                   ← custom exceptions + handler
    └── resources/
        ├── application.properties       ← config (DB, Swagger, JPA)
        └── data.sql                     ← seed data (plans, tiers, benefits)
```

---

## 🗄️ API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/membership/plans` | Get all membership plans |
| GET | `/api/membership/tiers` | Get all tiers with benefits |
| POST | `/api/membership/subscribe` | Subscribe to a plan + tier |
| GET | `/api/membership/{userId}` | Get current active membership |
| PUT | `/api/membership/{userId}/change-tier` | Upgrade or downgrade tier |
| DELETE | `/api/membership/{userId}/cancel` | Cancel membership |
| GET | `/api/membership/{userId}/history` | Get membership history |
| POST | `/api/membership/evaluate-tier` | Evaluate which tier user qualifies for |
| GET | `/api/membership/thread-info` | Prove virtual threads are active |

---

## 📖 Index

| # | Topic | File |
|---|-------|------|
| 1 | pom.xml structure & purpose | [01-pom-xml.md](./01-pom-xml.md) |
| 2 | Java version — 17 vs 21 vs 25 | [02-java-version.md](./02-java-version.md) |
| 3 | Dependencies vs Parent | [03-dependencies-vs-parent.md](./03-dependencies-vs-parent.md) |
| 4 | H2 Database — what, why, alternatives | [04-h2-database.md](./04-h2-database.md) |
| 5 | spring-boot-starter-web — what and why | [05-spring-boot-starter-web.md](./05-spring-boot-starter-web.md) |
| 6 | spring-boot-starter-data-jpa — ORM, Hibernate, JpaRepository | [06-spring-boot-starter-data-jpa.md](./06-spring-boot-starter-data-jpa.md) |
| 7 | @Query, JPQL, custom queries in Spring Data JPA | [07-query-jpql-spring-data.md](./07-query-jpql-spring-data.md) |
| 8 | @Configuration + @Bean + IoC — how Spring auto-wires | [08-spring-configuration-bean-ioc.md](./08-spring-configuration-bean-ioc.md) |
| 9 | Virtual Threads — what, why, OOM concern, lifecycle, GC | [09-virtual-threads.md](./09-virtual-threads.md) |
| 10 | @Scheduled — how Spring schedulers work | [10-scheduled-spring-scheduler.md](./10-scheduled-spring-scheduler.md) |
| 11 | Custom Exceptions + GlobalExceptionHandler | [11-custom-exceptions-global-handler.md](./11-custom-exceptions-global-handler.md) |
| 12 | HikariCP — DB connection pool, why it exists, virtual thread relation | [12-hikaricp-connection-pool.md](./12-hikaricp-connection-pool.md) |
| 13 | Spring vs Spring Boot — Beans, IoC, DI, ApplicationContext, Scopes | [13-spring-vs-springboot-beans-ioc-di.md](./13-spring-vs-springboot-beans-ioc-di.md) |

