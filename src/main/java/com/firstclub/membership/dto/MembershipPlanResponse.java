package com.firstclub.membership.dto;

import java.math.BigDecimal;

import com.firstclub.membership.enums.PlanType;
import com.firstclub.membership.model.MembershipPlan;

// Response object for plan details — never expose raw entity to client
public class MembershipPlanResponse {

    private Long id;
    private PlanType planType;      // MONTHLY, QUARTERLY, YEARLY
    private String name;
    private BigDecimal price;
    private Integer durationInDays;
    private String description;

    // ── Static factory — converts entity to DTO ───────────────────────────────

    public static MembershipPlanResponse from(MembershipPlan plan) {
        MembershipPlanResponse response = new MembershipPlanResponse();
        response.id = plan.getId();
        response.planType = plan.getPlanType();
        response.name = plan.getName();
        response.price = plan.getPrice();
        response.durationInDays = plan.getDurationInDays();
        response.description = plan.getDescription();
        return response;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public PlanType getPlanType() { return planType; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public Integer getDurationInDays() { return durationInDays; }
    public String getDescription() { return description; }
}
