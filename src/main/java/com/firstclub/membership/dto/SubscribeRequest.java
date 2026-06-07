package com.firstclub.membership.dto;

import jakarta.validation.constraints.NotNull;

// Request body when user subscribes to a plan + tier
public class SubscribeRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "planId is required")
    private Long planId;

    @NotNull(message = "tierId is required")
    private Long tierId;

    // ── Constructors ──────────────────────────────────────────────────────────

    public SubscribeRequest() {}

    public SubscribeRequest(Long userId, Long planId, Long tierId) {
        this.userId = userId;
        this.planId = planId;
        this.tierId = tierId;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public Long getTierId() { return tierId; }
    public void setTierId(Long tierId) { this.tierId = tierId; }
}
