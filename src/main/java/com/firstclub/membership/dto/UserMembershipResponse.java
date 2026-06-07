package com.firstclub.membership.dto;

import java.time.LocalDateTime;

import com.firstclub.membership.enums.MembershipStatus;
import com.firstclub.membership.enums.PlanType;
import com.firstclub.membership.enums.TierType;
import com.firstclub.membership.model.UserMembership;

// Response object for a user's membership — sent back after subscribe/cancel/upgrade
public class UserMembershipResponse {

    private Long id;
    private Long userId;
    private PlanType planType;          // MONTHLY, QUARTERLY, YEARLY
    private String planName;
    private TierType tierType;          // SILVER, GOLD, PLATINUM
    private String tierName;
    private MembershipStatus status;    // ACTIVE, EXPIRED, CANCELLED
    private LocalDateTime startDate;
    private LocalDateTime expiryDate;
    private LocalDateTime updatedAt;

    // ── Static factory — converts entity to DTO ───────────────────────────────

    public static UserMembershipResponse from(UserMembership membership) {
        UserMembershipResponse response = new UserMembershipResponse();
        response.id = membership.getId();
        response.userId = membership.getUserId();
        response.planType = membership.getPlan().getPlanType();
        response.planName = membership.getPlan().getName();
        response.tierType = membership.getTier().getTierType();
        response.tierName = membership.getTier().getName();
        response.status = membership.getStatus();
        response.startDate = membership.getStartDate();
        response.expiryDate = membership.getExpiryDate();
        response.updatedAt = membership.getUpdatedAt();
        return response;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public PlanType getPlanType() { return planType; }
    public String getPlanName() { return planName; }
    public TierType getTierType() { return tierType; }
    public String getTierName() { return tierName; }
    public MembershipStatus getStatus() { return status; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
