# ☕ Java Version — 17 vs 21 vs 25

## Q: Should I use Java 17, 21, or 25?

### Answer: Use **Java 21** ✓

---

## Comparison

| Version | Type | Released | Verdict |
|---------|------|----------|---------|
| Java 17 | LTS | Sep 2021 | Stable but old |
| **Java 21** | **LTS** | **Sep 2023** | **Best choice right now** ✓ |
| Java 25 | LTS | Sep 2026 | Not released yet, avoid |

> **LTS** = Long-Term Support — Oracle supports it for years with security patches.  
> Non-LTS versions are short-lived (6 months), not for production.

---

## Why Java 21 for this project?

### 1. Virtual Threads (Project Loom)
```java
// Old way — one OS thread per request (expensive)
ExecutorService pool = Executors.newFixedThreadPool(200);

// Java 21 — Virtual threads (cheap, millions possible)
ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor();
```
- Virtual threads are **lightweight** — you can spin thousands without worrying about thread pool limits
- **Directly relevant** to the concurrency bonus in the problem statement ✓

### 2. Records (clean DTOs)
```java
// Old way
public class MembershipPlanDTO {
    private String name;
    private double price;
    // getters, setters, constructors...
}

// Java 21 Records — immutable, concise
public record MembershipPlanDTO(String name, double price) {}
```

### 3. Pattern Matching
```java
// Cleaner instanceof checks
if (obj instanceof MembershipPlan plan) {
    System.out.println(plan.getName()); // no cast needed
}
```

### 4. Sealed Classes
```java
// Restrict which classes can implement an interface
public sealed interface TierCriteria permits OrderCountCriteria, OrderValueCriteria {}
```
Great for modelling the tier upgrade criteria in this project.

---

## How to set in pom.xml

```xml
<properties>
    <java.version>21</java.version>
</properties>
```

Spring Boot 3.4.1 fully supports Java 21 ✓
