# 🏊 HikariCP — Database Connection Pool

## Q: What is HikariCP and why does Spring Boot use it?

HikariCP is the **default connection pool** in Spring Boot. It manages a pool of
reusable DB connections so your app doesn't open/close a new connection for every request.

---

## Why connection pooling is needed

Opening a DB connection is **expensive**:
```
Without pool:
Request → TCP handshake → DB auth → session setup → query → close connection
          (50-100ms just for setup, every single request)

With HikariCP:
App startup → open 10 connections → keep them alive in pool
Request 1  → borrow connection → query → return to pool  (2ms overhead)
Request 2  → borrow connection → query → return to pool  (2ms overhead)
```

---

## Why named "Hikari"?

**Hikari (光)** = Japanese for **"light"**.  
Named for its speed — fastest Java connection pool available.  
Consistently outperforms DBCP2 and C3P0 in benchmarks.

---

## HikariCP in Spring Boot — auto-configured

You don't need to add it — Spring Boot includes it via `spring-boot-starter-data-jpa`.  
Default config (override in `application.properties`):

```properties
spring.datasource.hikari.maximum-pool-size=10     # max open connections to DB
spring.datasource.hikari.minimum-idle=10          # min idle connections kept alive
spring.datasource.hikari.connection-timeout=30000 # ms to wait before "no connection" error
spring.datasource.hikari.idle-timeout=600000      # ms before idle connection is removed
```

---

## Relationship with Virtual Threads

```
10,000 virtual threads all want DB access simultaneously
    → HikariCP has 10 connections
    → 9,990 threads wait (cheaply — virtual threads park, not block OS thread)
    → DB serves 10 at a time
    → No OOM — just controlled, orderly DB access
```

With virtual threads, **HikariCP pool size** becomes the primary performance tuning knob.  
Recommended formula: `pool size = (number of CPU cores × 2) + effective_disk_spindles`

---

## In the logs

```
HikariPool-1 - Starting...
HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:membershipdb user=SA
HikariPool-1 - Start completed.
```

On shutdown:
```
HikariPool-1 - Shutdown initiated...
HikariPool-1 - Shutdown completed.
```

---

## Alternatives

| Pool | Notes |
|------|-------|
| **HikariCP** | Default in Spring Boot, fastest ✓ |
| **DBCP2** | Apache, older, slower |
| **C3P0** | Very old, avoid |
| **Vibur** | Less common |

HikariCP is the right choice — don't change it. Just tune `maximum-pool-size` based on DB capacity.
