# 🔍 @Query — JPQL and Custom Queries in Spring Data JPA

## Q: What is `@Query` and JPQL?

`@Query` lets you write **custom queries** in Spring Data JPA when the built-in repository methods aren't enough.

JPQL = **Java Persistence Query Language** — looks like SQL but works on **Java class names and field names**, not DB table/column names.

---

## Example from real code

```java
@Query("SELECT m FROM Movie m WHERE m.title LIKE CONCAT('%', :query, '%') AND m.status = :status")
List<Movie> searchByTitle(@Param("query") String query, @Param("status") Movie.MovieStatus status);
```

- `Movie` = Java class name (not the DB table name)
- `m.title` = Java field name (not the DB column name)
- `:query`, `:status` = named parameters bound via `@Param`
- `CONCAT('%', :query, '%')` = LIKE contains search

---

## JPQL vs SQL

| | SQL | JPQL |
|--|-----|------|
| Works on | DB tables | Java entity classes |
| Table reference | `FROM membership_plans` | `FROM MembershipPlan` |
| Column reference | `m.plan_name` | `m.planName` (Java field) |
| DB specific? | Yes | No — DB agnostic |

---

## 3 Ways to Query in Spring Data JPA

### 1. Derived Method Names (simplest — no query needed)
Spring auto-generates the SQL from the method name:
```java
List<MembershipPlan> findByStatus(PlanStatus status);
// → SELECT * FROM membership_plan WHERE status = ?

List<MembershipPlan> findByPriceLessThan(Double price);
// → SELECT * FROM membership_plan WHERE price < ?

Optional<MembershipPlan> findByNameAndStatus(String name, PlanStatus status);
// → SELECT * WHERE name = ? AND status = ?
```

### 2. `@Query` with JPQL (custom logic, DB agnostic)
```java
@Query("SELECT p FROM MembershipPlan p WHERE p.price < :max AND p.status = :status")
List<MembershipPlan> findAffordablePlans(
    @Param("max") Double max,
    @Param("status") PlanStatus status
);
```

### 3. `@Query` with Native SQL (DB-specific, use sparingly)
```java
@Query(value = "SELECT * FROM membership_plans WHERE price < ?1", nativeQuery = true)
List<MembershipPlan> findAffordableNative(Double max);
```

> ⚠️ Avoid `nativeQuery = true` unless absolutely needed — it ties you to a specific DB.

---

## When to use which?

| Scenario | Use |
|---------|-----|
| Simple filters (by field, by status) | Derived method names |
| Complex filters, joins, aggregations | `@Query` with JPQL |
| DB-specific features (e.g. PostgreSQL full-text search) | `@Query` with nativeQuery |

---

## In this project

We'll use `@Query` for things like:
- Finding eligible users for tier upgrades (count orders > X)
- Filtering memberships by expiry date
- Aggregating order value in a month for tier criteria
