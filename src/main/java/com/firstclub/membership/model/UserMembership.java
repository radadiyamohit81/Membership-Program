package com.firstclub.membership.model;

import java.time.LocalDateTime;

import com.firstclub.membership.enums.MembershipStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_memberships")
public class UserMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We don't have a User entity (out of scope) — just store userId as a reference
    @Column(nullable = false)
    private Long userId;

    // The plan the user subscribed to (MONTHLY / QUARTERLY / YEARLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private MembershipPlan plan;

    // The tier the user is currently at (SILVER / GOLD / PLATINUM)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id", nullable = false)
    private MembershipTier tier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status;    // ACTIVE, EXPIRED, CANCELLED

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime expiryDate;   // startDate + plan.durationInDays

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // ── Constructors ────────────────────────────────────────────────────────

    public UserMembership() {}

    // ── Getters & Setters ───────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public MembershipPlan getPlan() { return plan; }
    public void setPlan(MembershipPlan plan) { this.plan = plan; }

    public MembershipTier getTier() { return tier; }
    public void setTier(MembershipTier tier) { this.tier = tier; }

    public MembershipStatus getStatus() { return status; }
    public void setStatus(MembershipStatus status) { this.status = status; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ── Builder ─────────────────────────────────────────────────────────────

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long userId;
        private MembershipPlan plan;
        private MembershipTier tier;
        private MembershipStatus status;
        private LocalDateTime startDate;
        private LocalDateTime expiryDate;

        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder plan(MembershipPlan plan) { this.plan = plan; return this; }
        public Builder tier(MembershipTier tier) { this.tier = tier; return this; }
        public Builder status(MembershipStatus status) { this.status = status; return this; }
        public Builder startDate(LocalDateTime startDate) { this.startDate = startDate; return this; }
        public Builder expiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; return this; }

        public UserMembership build() {
            UserMembership m = new UserMembership();
            m.userId = this.userId;
            m.plan = this.plan;
            m.tier = this.tier;
            m.status = this.status;
            m.startDate = this.startDate;
            m.expiryDate = this.expiryDate;
            return m;
        }
    }
}
