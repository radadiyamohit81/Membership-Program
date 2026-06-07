package com.firstclub.membership.dto;

import jakarta.validation.constraints.NotNull;

// Request body for tier evaluation — simulates user's order activity
public class EvaluateTierRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    private int orderCount;     // total number of orders by this user

    private double orderValue;  // total order value in rupees

    private String cohort;      // optional user cohort label e.g. "VIP", "EMPLOYEE"

    // ── Constructors ──────────────────────────────────────────────────────────

    public EvaluateTierRequest() {}

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public int getOrderCount() { return orderCount; }
    public void setOrderCount(int orderCount) { this.orderCount = orderCount; }

    public double getOrderValue() { return orderValue; }
    public void setOrderValue(double orderValue) { this.orderValue = orderValue; }

    public String getCohort() { return cohort; }
    public void setCohort(String cohort) { this.cohort = cohort; }
}
