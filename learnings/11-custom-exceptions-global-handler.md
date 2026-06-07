# 🚨 Custom Exceptions + GlobalExceptionHandler in Spring Boot

## Q: Why custom exceptions instead of RuntimeException?

Throwing `RuntimeException("Plan not found")` works but is bad practice:
- No semantic meaning — all errors look the same
- Hard to test specifically
- GlobalExceptionHandler can't distinguish between error types

Custom exceptions give each error its **own identity and HTTP status**.

---

## Custom Exception Pattern

```java
@ResponseStatus(HttpStatus.NOT_FOUND)    // HTTP 404 when this is thrown
public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(Long planId) {
        super("Membership plan not found with id: " + planId);
    }
}
```

Simple, clean, self-documenting. Each exception = one specific problem.

---

## Exceptions in this project

| Exception | HTTP Status | When thrown |
|-----------|------------|-------------|
| `PlanNotFoundException` | 404 | Plan ID doesn't exist |
| `TierNotFoundException` | 404 | Tier ID doesn't exist |
| `MembershipNotFoundException` | 404 | No active membership for user |
| `MembershipAlreadyActiveException` | 409 | User already has active membership |

---

## GlobalExceptionHandler — @RestControllerAdvice

```java
@RestControllerAdvice   // ← applies to all @RestControllers globally
public class GlobalExceptionHandler {

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MembershipAlreadyActiveException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(RuntimeException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }
}
```

---

## What client sees (instead of ugly stack trace)

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Membership plan not found with id: 99",
  "timestamp": "2026-06-07T10:00:00"
}
```

---

## Flow

```
Controller → Service throws PlanNotFoundException
    → Spring catches it
    → Routes to @ExceptionHandler(PlanNotFoundException.class)
    → Returns clean JSON 404 response
    → Stack trace never reaches client ✓
```

---

## @ExceptionHandler priority

Spring matches the **most specific** exception handler first:
```
PlanNotFoundException (specific) → matched first ✓
RuntimeException (generic)       → only if no specific handler found
Exception (broadest)             → last resort fallback
```
