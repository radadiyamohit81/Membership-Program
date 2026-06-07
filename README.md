# 🏆 FirstClub Membership Program

A backend system for a subscription-based **Membership Program with Tiered Benefits**, built with Spring Boot + Java 25.

---

## 🚀 Quick Start

```bash
# Clone the repo
git clone https://github.com/radadiyamohit81/Membership-Program.git
cd Membership-Program

# Run the app
mvn clean spring-boot:run
```

App starts at `http://localhost:8080`

---

## 🌐 URLs

| URL | Purpose |
|-----|---------|
| `http://localhost:8080/swagger-ui/index.html` | Swagger UI — test all APIs |
| `http://localhost:8080/h2-console` | H2 DB browser (JDBC URL: `jdbc:h2:mem:membershipdb`) |
| `http://localhost:8080/api-docs` | Raw OpenAPI JSON spec |
| `http://localhost:8080/api/membership/thread-info` | Prove virtual threads are active |

---

## 🧩 Problem Statement

Design a backend system for a Membership Program with:
- **Membership Plans** — Monthly, Quarterly, Yearly with specific pricing
- **Membership Tiers** — Silver, Gold, Platinum with configurable benefits
- **User Actions** — Subscribe, upgrade/downgrade tier, cancel, track expiry
- **Tier Evaluation** — Based on order count, order value, or user cohort (DB-configurable)

---

## 🗄️ API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/membership/plans` | Get all membership plans |
| GET | `/api/membership/tiers` | Get all tiers with benefits |
| POST | `/api/membership/subscribe` | Subscribe to a plan + tier |
| GET | `/api/membership/{userId}` | Get current active membership |
| PUT | `/api/membership/{userId}/change-tier` | Upgrade or downgrade tier |
| DELETE | `/api/membership/{userId}/cancel` | Cancel membership |
| GET | `/api/membership/{userId}/history` | Get full membership history |
| POST | `/api/membership/evaluate-tier` | Evaluate which tier user qualifies for |
| GET | `/api/membership/thread-info` | Prove virtual threads are active |

---

## 📁 Project Structure

```
src/
└── main/
    ├── java/com/firstclub/membership/
    │   ├── MembershipApplication.java   ← entry point (@SpringBootApplication)
    │   ├── config/                      ← AppConfig (Virtual Threads)
    │   ├── controller/                  ← REST API layer
    │   ├── service/                     ← business logic + scheduler
    │   ├── repository/                  ← Spring Data JPA repositories
    │   ├── model/                       ← JPA entities (DB tables)
    │   ├── dto/                         ← request/response objects
    │   ├── enums/                       ← PlanType, TierType, MembershipStatus...
    │   └── exception/                   ← custom exceptions + GlobalExceptionHandler
    └── resources/
        ├── application.properties       ← DB, JPA, Swagger config
        └── data.sql                     ← seed data (plans, tiers, benefits, criteria)
```

---

## 🏗️ Entity Design

```
MembershipPlan        ← MONTHLY / QUARTERLY / YEARLY plans with pricing
MembershipTier        ← SILVER / GOLD / PLATINUM tiers
TierBenefit           ← benefits per tier (DB-configurable)
TierCriteria          ← upgrade rules per tier (DB-configurable)
UserMembership        ← user's active subscription (plan + tier + expiry)
```

**Relationships:**
```
MembershipTier ──< TierBenefit     (one tier → many benefits)
MembershipTier ──< TierCriteria    (one tier → many criteria rules)
UserMembership >── MembershipPlan  (many users → one plan)
UserMembership >── MembershipTier  (many users → one tier)
```

---

## ⚙️ Key Design Decisions

### DB-Configurable Tiers & Benefits
Benefits and tier upgrade criteria are stored in DB — no code change needed to add new benefits or adjust thresholds.

### Tier Evaluation (OR logic)
A user qualifies for a tier if they meet **any one** of its criteria:
```
GOLD: ORDER_COUNT > 10  OR  ORDER_VALUE > ₹5000
PLATINUM: ORDER_COUNT > 20  OR  ORDER_VALUE > ₹15000  OR  COHORT = 'VIP'
```

### One Active Membership Per User
Enforced at service layer — subscribe throws `409 Conflict` if user already has an active membership.

### Virtual Threads (Concurrency Bonus)
Every HTTP request runs on a **Java 25 Virtual Thread** — handles thousands of concurrent requests with minimal memory overhead.

```java
@Bean
public TomcatProtocolHandlerCustomizer<?> virtualThreadsForTomcat() {
    return protocolHandler ->
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
}
```

### Auto-Expiry Scheduler
`MembershipExpiryScheduler` runs hourly and automatically marks expired memberships as `EXPIRED`.

---

## 🛠️ Tech Stack

| Technology | Purpose |
|-----------|---------|
| Java 25 | Runtime |
| Spring Boot 3.4.1 | Framework |
| Spring Data JPA + Hibernate | ORM |
| H2 (in-memory) | Database (swap to PostgreSQL for production) |
| HikariCP | Connection pooling |
| springdoc-openapi 2.7.0 | Swagger UI |
| Maven | Build tool |

---

## 📚 Learnings

All concepts learned while building this project are documented in [`learnings/`](./learnings/README.md) — 13 topics covering pom.xml, JPA, Virtual Threads, IoC, Schedulers, and more.
