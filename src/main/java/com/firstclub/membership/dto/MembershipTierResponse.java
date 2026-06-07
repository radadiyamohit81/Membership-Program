package com.firstclub.membership.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.firstclub.membership.enums.TierType;
import com.firstclub.membership.model.MembershipTier;
import com.firstclub.membership.model.TierBenefit;

// Response object for tier details including its benefits
public class MembershipTierResponse {

    private Long id;
    private TierType tierType;      // SILVER, GOLD, PLATINUM
    private String name;
    private String description;
    private List<BenefitDetail> benefits;

    // ── Static factory — converts entity to DTO ───────────────────────────────

    public static MembershipTierResponse from(MembershipTier tier) {
        MembershipTierResponse response = new MembershipTierResponse();
        response.id = tier.getId();
        response.tierType = tier.getTierType();
        response.name = tier.getName();
        response.description = tier.getDescription();

        List<TierBenefit> benefits = tier.getBenefits();
        response.benefits = (benefits != null)
                ? benefits.stream().map(BenefitDetail::from).collect(Collectors.toList())
                : Collections.emptyList();

        return response;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public TierType getTierType() { return tierType; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<BenefitDetail> getBenefits() { return benefits; }

    // ── Nested DTO for benefit details ────────────────────────────────────────

    public static class BenefitDetail {
        private String benefitType;
        private String value;
        private String description;

        public static BenefitDetail from(TierBenefit benefit) {
            BenefitDetail detail = new BenefitDetail();
            detail.benefitType = benefit.getBenefitType().name();
            detail.value = benefit.getValue();
            detail.description = benefit.getDescription();
            return detail;
        }

        public String getBenefitType() { return benefitType; }
        public String getValue() { return value; }
        public String getDescription() { return description; }
    }
}
