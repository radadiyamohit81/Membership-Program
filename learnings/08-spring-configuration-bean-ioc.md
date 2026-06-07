# 🌱 @Configuration + @Bean — How Spring Auto-Wires Without You Calling Anything

## Q: We never called AppConfig anywhere — how does Spring know to use it?

This is **Inversion of Control (IoC)** — you don't call Spring, Spring calls you.

---

## How it works step by step

```java
@Configuration          // ← tells Spring: "this class has beans to register"
public class AppConfig {

    @Bean               // ← tells Spring: "register the return value as a managed bean"
    public TomcatProtocolHandlerCustomizer<?> virtualThreadsForTomcat() {
        return protocolHandler ->
                protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }
}
```

When Spring Boot starts:
1. `@SpringBootApplication` includes `@ComponentScan`
2. `@ComponentScan` scans all classes in the package
3. Finds `@Configuration` classes → calls all `@Bean` methods
4. Registers return values in the **Application Context** (Spring's container)

---

## Why `TomcatProtocolHandlerCustomizer` works automatically

Spring Boot's auto-configuration checks for beans of specific known types at startup:

```
Spring Boot starts Tomcat
    → checks: "is there a TomcatProtocolHandlerCustomizer bean in context?"
    → finds yours → calls it → sets VirtualThread executor on Tomcat
    → every request now runs on a virtual thread ✓
```

This is called a **well-known hook point** — Spring Boot defines extension points
and you just plug in the right bean type.

---

## Analogy

Like a **plugin socket** — Spring Boot has pre-defined sockets.
You plug in the right type and Spring picks it up.

---

## Common Spring Stereotypes

| Annotation | Meaning |
|-----------|---------|
| `@Configuration` | Class contains bean definitions |
| `@Bean` | Method return value is a Spring-managed bean |
| `@Service` | Business logic bean (auto-detected by component scan) |
| `@Repository` | DB layer bean |
| `@RestController` | HTTP controller bean |
| `@Component` | Generic bean |
