# 🫘 Spring vs Spring Boot — Beans, IoC Container, DI Explained

## Q: What is Spring? What is Spring Boot? What are Beans?

---

## Spring Framework

Spring is a **Java application framework** that provides:
- **IoC Container** — manages object creation and lifecycle
- **Dependency Injection** — wires objects together automatically
- **AOP** — cross-cutting concerns (logging, transactions)
- **Data access** — JDBC, JPA abstractions
- **Web MVC** — REST API support

Problem: Spring alone requires massive XML/Java config to set up. Very verbose.

---

## Spring Boot

Spring Boot is **Spring + opinionated defaults + auto-configuration**.

```
Spring alone:
  → You configure DataSource bean manually
  → You configure EntityManagerFactory manually
  → You configure TransactionManager manually
  → 200 lines of config before writing any business code

Spring Boot:
  → Add spring-boot-starter-data-jpa to pom.xml
  → Add DB config to application.properties
  → Done. Everything auto-configured. ✓
```

**Spring Boot = Spring with batteries included.**

---

## What is a Bean?

A **Bean** is any Java object whose **lifecycle is managed by Spring's IoC container**.

```java
// NOT a bean — you create it, you manage it
MembershipService service = new MembershipService();

// IS a bean — Spring creates it, manages it, injects it where needed
@Service
public class MembershipService { ... }
```

Spring manages: creation, wiring dependencies, destruction.

---

## IoC Container (Inversion of Control)

Normally **you** control object creation:
```java
// Traditional — you are in control
MembershipService service = new MembershipService(
    new MembershipPlanRepository(),
    new MembershipTierRepository()
);
```

With IoC — **Spring** controls object creation:
```java
// IoC — Spring creates and wires everything
@Service
public class MembershipService {
    // Spring injects these automatically
    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;

    public MembershipService(MembershipPlanRepository planRepository,
                             MembershipTierRepository tierRepository) {
        this.planRepository = planRepository;
        this.tierRepository = tierRepository;
    }
}
```

Spring scans → finds `@Service` → creates instance → injects dependencies → stores in **ApplicationContext**.

---

## ApplicationContext — Spring's container

The `ApplicationContext` is Spring's **registry of all beans**:

```
ApplicationContext (registry)
├── MembershipService       (singleton)
├── MembershipController    (singleton)
├── MembershipPlanRepository(singleton)
├── TierEvaluationService   (singleton)
├── AppConfig               (singleton)
└── ... all other beans
```

When `MembershipController` needs `MembershipService`, Spring looks it up in the context and injects it. You never do `new`.

---

## Dependency Injection (DI)

DI = the mechanism Spring uses to inject beans into each other.

**3 ways to inject (constructor is best):**

```java
// 1. Constructor injection (recommended ✓)
public MembershipController(MembershipService membershipService) {
    this.membershipService = membershipService;
}

// 2. Field injection (avoid — hard to test)
@Autowired
private MembershipService membershipService;

// 3. Setter injection (rare)
@Autowired
public void setMembershipService(MembershipService membershipService) {
    this.membershipService = membershipService;
}
```

We use **constructor injection** throughout this project ✓

---

## Bean Scopes

| Scope | Meaning | Default? |
|-------|---------|----------|
| `singleton` | One instance per ApplicationContext | ✓ Yes |
| `prototype` | New instance every time it's requested | No |
| `request` | One instance per HTTP request | No (web only) |
| `session` | One instance per HTTP session | No (web only) |

All our beans (`@Service`, `@Repository`, `@RestController`) are **singletons** — one shared instance handles all requests. That's why thread safety matters.

---

## How Spring finds beans — Component Scan

```java
@SpringBootApplication
// includes @ComponentScan — scans com.firstclub.membership and all sub-packages
```

Spring scans for:
| Annotation | Bean type |
|-----------|----------|
| `@Component` | Generic bean |
| `@Service` | Business logic layer |
| `@Repository` | Data access layer |
| `@RestController` | HTTP controller |
| `@Configuration` + `@Bean` | Explicit bean definition |

---

## Spring vs Spring Boot summary

| | Spring | Spring Boot |
|--|--------|------------|
| Config | Manual (verbose) | Auto-configured |
| Server | Deploy to external Tomcat | Embedded Tomcat included |
| Setup time | Hours | Minutes |
| Entry point | web.xml / DispatcherServlet config | `@SpringBootApplication` main() |
| Properties | Multiple XML files | Single `application.properties` |
