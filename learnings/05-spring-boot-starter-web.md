# 🌐 spring-boot-starter-web — What and Why?

## Q: What is `spring-boot-starter-web` and why do we need it?

It bundles everything needed to build **REST APIs** into one single dependency.

---

## What it includes internally

| Included Library | Purpose |
|-----------------|---------|
| **Spring MVC** | Framework to write `@RestController`, `@GetMapping`, etc. |
| **Tomcat** (embedded) | Web server — runs your app on `localhost:8080` without installing anything |
| **Jackson** | Converts Java objects ↔ JSON automatically |

---

## Without it — you can't do this:

```java
@RestController
@RequestMapping("/membership")
public class MembershipController {

    @GetMapping("/plans")
    public List<MembershipPlan> getPlans() {
        return planService.getAllPlans();
    }
}
```

None of those annotations (`@RestController`, `@GetMapping`) would exist.  
Your app would have no HTTP server, no JSON handling — nothing.

---

## Why "starter"?

Spring Boot introduced the `starter` concept — instead of adding 5-6 separate dependencies,  
one `starter` pulls in everything you need for a specific use case.

```
spring-boot-starter-web
    ├── spring-webmvc          ← REST controllers, mappings
    ├── spring-boot-starter-tomcat  ← embedded web server
    ├── spring-boot-starter-json    ← Jackson (Java ↔ JSON)
    └── spring-boot-starter         ← core Spring Boot
```

**One line in pom.xml = fully working HTTP server + REST support ✓**

---

## In pom.xml

```xml
<!-- Enables REST APIs + embedded Tomcat server -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
