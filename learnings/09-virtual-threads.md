# 🧵 Virtual Threads — What, Why, OOM Concern, and Lifecycle

## Q: What are Virtual Threads and why did we enable them?

Virtual Threads (Project Loom, Java 21+) are **lightweight threads managed by the JVM**,
not by the OS. Each HTTP request gets its own virtual thread — no thread pool needed.

---

## Platform Threads vs Virtual Threads

| | Platform Thread | Virtual Thread |
|--|----------------|----------------|
| Managed by | OS | JVM |
| Memory per thread | ~1MB (OS stack) | ~2KB (heap) |
| 10,000 threads | ~10GB RAM 💀 | ~20MB RAM ✓ |
| Creation cost | Expensive (OS syscall) | Cheap (heap allocation) |
| Max concurrent | ~200 (typical pool size) | Millions |

---

## How we enabled it

```java
@Bean
public TomcatProtocolHandlerCustomizer<?> virtualThreadsForTomcat() {
    return protocolHandler ->
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
}
```

`newVirtualThreadPerTaskExecutor()` creates a **new virtual thread per task** — no pool, no limit.

---

## Q: Won't creating unlimited threads cause OOM?

**No** — virtual threads are just heap objects (~2KB each):

```
10,000 virtual threads = 10,000 × 2KB = ~20MB  ✓
10,000 platform threads = 10,000 × 1MB = ~10GB  💀
```

Pooling virtual threads is actually an **anti-pattern** — defeats the purpose:
```java
// ❌ WRONG — don't pool virtual threads
Executors.newFixedThreadPool(200)

// ✅ CORRECT — create fresh per task
Executors.newVirtualThreadPerTaskExecutor()
```

---

## Q: Who destroys them? Do we need to worry about GC?

**No special care needed.** Virtual threads are plain heap objects:

```
Request done
    → virtual thread has no more references
    → GC marks it eligible → collected in next cycle
```

Short-lived objects are collected efficiently in the **Eden space (young generation)** —
better for GC than long-lived thread pool objects in old generation.

---

## Real bottleneck shifts to DB connection pool

With virtual threads, the actual bottleneck is downstream resources:
```
10,000 virtual threads all hit DB simultaneously
    → HikariCP has only 10 connections by default
    → 9,990 threads wait → DB overwhelmed
```

**Fix:**
```properties
spring.datasource.hikari.maximum-pool-size=50
spring.datasource.hikari.minimum-idle=10
```

---

## How to prove virtual threads are active (demo)

```java
@GetMapping("/thread-info")
public ResponseEntity<Map<String, Object>> threadInfo() {
    Thread t = Thread.currentThread();
    Map<String, Object> info = new HashMap<>();
    info.put("isVirtual", t.isVirtual());   // true ✓
    info.put("threadName", t.getName());
    return ResponseEntity.ok(info);
}
```
Hit `GET /api/membership/thread-info` → `"isVirtual": true`
