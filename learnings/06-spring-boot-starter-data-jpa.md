# 🗃️ spring-boot-starter-data-jpa — What and Why?

## Q: What is `spring-boot-starter-data-jpa` and why do we need it?

It lets you **talk to your database using Java objects** instead of writing raw SQL.

---

## Without JPA — raw SQL everywhere (painful):

```java
String sql = "SELECT * FROM membership_plans WHERE id = ?";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setLong(1, id);
ResultSet rs = stmt.executeQuery();
// manually map each column to a Java field... 😩
MembershipPlan plan = new MembershipPlan();
plan.setId(rs.getLong("id"));
plan.setName(rs.getString("name"));
```

## With JPA — clean and simple:

```java
MembershipPlan plan = planRepository.findById(id); // that's it ✓
```

---

## What it includes internally

| Included | Purpose |
|----------|---------|
| **JPA** (Jakarta Persistence API) | Standard specification/API for ORM in Java |
| **Hibernate** | The actual implementation of JPA — does the heavy lifting |
| **Spring Data JPA** | Adds `JpaRepository` — gives you `save()`, `findById()`, `findAll()` for free |

> JPA is just an **interface/spec**. Hibernate is the **implementation** (like how Java has interfaces and classes implement them).

---

## ORM = Object Relational Mapping

Maps your Java class directly to a DB table:

```java
@Entity                          // this class = a DB table
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;             // primary key column (auto-increment)

    private String name;         // "name" column
    private Double price;        // "price" column
}
```

Hibernate auto-generates this SQL for you:
```sql
CREATE TABLE membership_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE
);
```

---

## JpaRepository — free methods out of the box

```java
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
    // Nothing needed here — you already get:
}

// Available for free:
planRepository.findAll();           // SELECT * FROM membership_plan
planRepository.findById(1L);        // SELECT * WHERE id = 1
planRepository.save(plan);          // INSERT or UPDATE
planRepository.deleteById(1L);      // DELETE WHERE id = 1
planRepository.count();             // SELECT COUNT(*)
```

---

## In pom.xml

```xml
<!-- ORM: talk to DB using Java objects, auto-generates SQL -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

---

## `@Transactional` — why it matters

```java
@Transactional
public UserMembership subscribe(Long userId, Long planId, Long tierId) {
    // multiple DB operations happen here
    // if ANY fails → ALL are rolled back automatically
}
```

Without `@Transactional`: partial writes can corrupt data.  
With `@Transactional`: all-or-nothing — either everything saves or nothing does.

---

## FetchType — LAZY vs EAGER

```java
@OneToMany(fetch = FetchType.LAZY)   // load benefits only when accessed (preferred)
@OneToMany(fetch = FetchType.EAGER)  // load benefits immediately with tier (avoid)
```

**Always prefer LAZY** — don't load data you don't need.  
EAGER causes N+1 query problems and slow API responses.

---

## ddl-auto options

| Value | Behaviour | Use in |
|-------|-----------|--------|
| `create-drop` | Create on start, drop on stop | Tests / dev |
| `update` | Add missing columns, never delete | Dev |
| `validate` | Check schema matches entities, fail if not | Staging |
| `none` | Do nothing — manage schema manually | Production |
