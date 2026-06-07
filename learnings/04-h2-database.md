# 🗄️ H2 Database — What, Why, and Alternatives

## Q: What is H2 and why use it?

H2 is a **relational database written entirely in Java**.  
It runs **inside your application's memory** — no separate DB server needed.

---

## Why named "H2"?

- H₂ is the chemical formula for **Hydrogen**
- Created by **Thomas Müller** as a successor to **HyperSQL (HSQLDB)**
- "H2" = "Hypersonic 2" — fast, lightweight, pure Java

---

## How it works

```
Normal DB setup:                    H2 setup:
┌──────────┐    TCP     ┌────────┐  ┌──────────────────────────┐
│ Your App │ ─────────> │ MySQL  │  │ Your App + H2 (in-memory)│
└──────────┘            └────────┘  └──────────────────────────┘
  (separate process)                  (single process, no install)
```

- Data lives **in RAM** while the app runs
- Data is **wiped on restart** (that's fine for dev/testing)
- Supports standard SQL — switch to real DB later seamlessly

---

## Why people use H2

| Reason | Detail |
|--------|--------|
| Zero setup | No install, no server, just add dependency |
| Fast | Everything in memory = instant queries |
| Testing | Each test gets a fresh, clean DB state |
| Built-in console | Browser UI at `localhost:8080/h2-console` |
| SQL compatible | Same SQL as PostgreSQL/MySQL |

---

## H2 in pom.xml

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>  <!-- only needed when app runs, not at compile time -->
</dependency>
```

---

## H2 in application.properties

```properties
# H2 in-memory config
spring.datasource.url=jdbc:h2:mem:membershipdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Enable the H2 browser console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Let Hibernate auto-create tables from your entities
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

Visit `http://localhost:8080/h2-console` to browse your in-memory data.

---

## Alternatives to H2

| DB | Type | Use case |
|----|------|---------|
| **HSQLDB** | In-memory | Similar to H2, older, less popular |
| **SQLite** | File-based | Mobile/embedded apps, data persists to a file |
| **Apache Derby** | In-memory | Apache project, less popular than H2 |
| **TestContainers** | Docker-based | Spins a real PostgreSQL/MySQL in Docker for tests — best for production-like testing |

---

## Migration Plan (H2 → PostgreSQL)

When APIs are ready, swap is just 2 steps:

**Step 1:** Replace dependency in `pom.xml`
```xml
<!-- Remove H2 -->
<!-- Add PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

**Step 2:** Update `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/membershipdb
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

JPA/Hibernate handles the rest automatically ✓
