# 📦 Dependencies vs Parent — What's the difference?

## Q: We already added `<parent>`, why still add dependencies?

### Short Answer
- **`<parent>`** = version manager (tells Maven *what versions* to use)
- **`<dependencies>`** = your actual library list (tells Maven *what to include*)

The parent does **not** automatically include any libraries. You still have to declare what you need.

---

## Analogy

Think of it like a restaurant menu:
- `<parent>` = the menu (lists available dishes and prices)
- `<dependencies>` = your order (what you actually want to eat)

Just having the menu doesn't give you food. You still have to order.

---

## Benefit of `<parent>`

Without parent — you manage every version manually:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.4.1</version>  <!-- you manage this -->
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <version>3.4.1</version>  <!-- and this -->
</dependency>
```

With parent — Spring manages compatible versions for you:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <!-- no version needed ✓ -->
</dependency>
```

---

## Dependencies used in this project

| Dependency | Purpose |
|-----------|---------|
| `spring-boot-starter-web` | Build REST APIs |
| `spring-boot-starter-data-jpa` | Talk to DB using Java objects (ORM) |
| `h2` | In-memory database for development |
| `spring-boot-starter-validation` | Validate request bodies (`@NotNull`, `@Min`, etc.) |
| `lombok` | Remove boilerplate (auto-generate getters/setters) |
| `spring-boot-starter-test` | Unit and integration testing |

---

## Dependency Scopes

| Scope | When is it available? |
|-------|-----------------------|
| (none) | Compile + runtime + test — everywhere |
| `runtime` | Only when app is running (e.g. DB drivers) |
| `test` | Only during test execution |
| `provided` | Available at compile but not packaged (e.g. servlet API) |
| `optional` | Not inherited by dependent projects (e.g. Lombok) |
