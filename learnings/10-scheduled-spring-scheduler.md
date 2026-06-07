# ⏰ @Scheduled — How Spring Schedulers Work

## Q: How does Spring know to call the scheduler method automatically?

Spring uses `@EnableScheduling` + `@Scheduled` to run methods on a timer —
you never call them manually.

---

## How it works

**Step 1:** Enable scheduling on the main app class:
```java
@SpringBootApplication
@EnableScheduling   // ← activates Spring's scheduling engine
public class MembershipApplication { ... }
```

**Step 2:** Mark any bean method with `@Scheduled`:
```java
@Service
public class MembershipExpiryScheduler {

    @Scheduled(fixedRate = 10000)   // ← run every 10 seconds
    public void expireActiveMemberships() {
        // Spring calls this automatically — you never invoke it
    }
}
```

---

## What Spring does internally

```
App starts
    → @EnableScheduling activates ScheduledTaskRegistrar
    → Spring scans all beans for @Scheduled methods
    → Finds expireActiveMemberships() → registers as recurring task
    → Background thread fires it on schedule — no manual call needed
```

---

## Schedule options

| Annotation | Example | Meaning |
|-----------|---------|---------|
| `fixedRate` | `@Scheduled(fixedRate = 10000)` | Every 10 seconds from start |
| `fixedDelay` | `@Scheduled(fixedDelay = 5000)` | 5 seconds AFTER previous run finishes |
| `cron` | `@Scheduled(cron = "0 0 * * * *")` | Every hour at minute 0 |

---

## Cron expression format

```
@Scheduled(cron = "second minute hour day month weekday")

"0 0 * * * *"   → every hour
"0 0 9 * * *"   → every day at 9am
"0 */30 * * * *"→ every 30 minutes
```

---

## Analogy

Like a **cron job on a server** — you define the schedule declaratively,
the OS/JVM runs it. You never "call" a cron job — it fires automatically.

---

## In this project

`MembershipExpiryScheduler` runs every hour in production:
```java
@Scheduled(cron = "0 0 * * * *")
public void expireActiveMemberships() {
    // finds ACTIVE memberships where expiryDate < now
    // marks them EXPIRED in bulk
}
```

For demo: changed to `fixedRate = 10000` (every 10 seconds).

---

## How to demo it

1. Subscribe a user → note membership ID
2. In H2 console, backdate the expiry:
   ```sql
   UPDATE user_memberships SET expiry_date = '2020-01-01 00:00:00' WHERE user_id = 1;
   ```
3. Wait 10 seconds → see log: `[ExpiryScheduler] Expired 1 memberships`
4. `GET /api/membership/1` → 404 (no active membership)
5. `GET /api/membership/1/history` → status = `EXPIRED`
