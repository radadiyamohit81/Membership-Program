package com.firstclub.membership.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Catches all exceptions globally and returns clean JSON error responses
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── Already has active membership (409 Conflict) ──────────────────────────
    @ExceptionHandler(MembershipAlreadyActiveException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyActive(MembershipAlreadyActiveException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ── Resource not found (404) ──────────────────────────────────────────────
    @ExceptionHandler({PlanNotFoundException.class, TierNotFoundException.class, MembershipNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // ── @Valid request body validation failures (400) ─────────────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildError(HttpStatus.BAD_REQUEST, errors);
    }

    // ── Generic fallback (500) ────────────────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + ex.getMessage());
    }

    // ── Helper to build consistent error response ─────────────────────────────
    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(body);
    }
}
