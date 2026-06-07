package com.firstclub.membership.model;

import com.firstclub.membership.enums.BenefitType;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "tier_benefits")
public class TierBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id", nullable = false)
    private MembershipTier tier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BenefitType benefitType;    // DISCOUNT_PERCENT, FREE_DELIVERY, etc.

    // Note: "value" is a reserved keyword in H2, so mapped to "benefit_value" column
    @Column(name = "benefit_value", nullable = false)
    private String value;

    private String description;

    // ── Constructors ────────────────────────────────────────────────────────

    public TierBenefit() {}

    public TierBenefit(Long id, MembershipTier tier, BenefitType benefitType, String value, String description) {
        this.id = id;
        this.tier = tier;
        this.benefitType = benefitType;
        this.value = value;
        this.description = description;
    }

    // ── Getters & Setters ───────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MembershipTier getTier() { return tier; }
    public void setTier(MembershipTier tier) { this.tier = tier; }

    public BenefitType getBenefitType() { return benefitType; }
    public void setBenefitType(BenefitType benefitType) { this.benefitType = benefitType; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
