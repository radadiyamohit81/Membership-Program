package com.firstclub.membership.model;

import java.math.BigDecimal;

import com.firstclub.membership.enums.PlanType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "membership_plans")
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)    // store enum as "MONTHLY" string, not 0/1/2
    @Column(nullable = false, unique = true)
    private PlanType planType;      // MONTHLY, QUARTERLY, YEARLY

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer durationInDays; // 30, 90, 365 — used to calculate expiry

    private String description;

    // ── Constructors ────────────────────────────────────────────────────────

    public MembershipPlan() {}

    public MembershipPlan(Long id, PlanType planType, String name, BigDecimal price, Integer durationInDays, String description) {
        this.id = id;
        this.planType = planType;
        this.name = name;
        this.price = price;
        this.durationInDays = durationInDays;
        this.description = description;
    }

    // ── Getters & Setters ───────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PlanType getPlanType() { return planType; }
    public void setPlanType(PlanType planType) { this.planType = planType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getDurationInDays() { return durationInDays; }
    public void setDurationInDays(Integer durationInDays) { this.durationInDays = durationInDays; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // ── Builder ─────────────────────────────────────────────────────────────

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private PlanType planType;
        private String name;
        private BigDecimal price;
        private Integer durationInDays;
        private String description;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder planType(PlanType planType) { this.planType = planType; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder durationInDays(Integer durationInDays) { this.durationInDays = durationInDays; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public MembershipPlan build() { return new MembershipPlan(id, planType, name, price, durationInDays, description); }
    }
}
