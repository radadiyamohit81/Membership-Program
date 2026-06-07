package com.firstclub.membership.dto;

import jakarta.validation.constraints.NotNull;

// Request body when user upgrades or downgrades their tier
public class ChangeTierRequest {

    @NotNull(message = "newTierId is required")
    private Long newTierId;

    // ── Constructors ──────────────────────────────────────────────────────────

    public ChangeTierRequest() {}

    public ChangeTierRequest(Long newTierId) {
        this.newTierId = newTierId;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long getNewTierId() { return newTierId; }
    public void setNewTierId(Long newTierId) { this.newTierId = newTierId; }
}
