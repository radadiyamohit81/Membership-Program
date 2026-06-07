package com.firstclub.membership.model;

import com.firstclub.membership.enums.CriteriaType;

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
@Table(name = "tier_criteria")
public class TierCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id", nullable = false)
    private MembershipTier tier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CriteriaType criteriaType;  // ORDER_COUNT, ORDER_VALUE, COHORT

    // The threshold value — e.g. "5" for 5 orders, "5000" for ₹5000, "VIP" for cohort
    // If multiple criteria exist for a tier, ANY one matching = eligible (OR logic)
    @Column(nullable = false)
    private String thresholdValue;

    // ── Constructors ────────────────────────────────────────────────────────

    public TierCriteria() {}

    public TierCriteria(Long id, MembershipTier tier, CriteriaType criteriaType, String thresholdValue) {
        this.id = id;
        this.tier = tier;
        this.criteriaType = criteriaType;
        this.thresholdValue = thresholdValue;
    }

    // ── Getters & Setters ───────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MembershipTier getTier() { return tier; }
    public void setTier(MembershipTier tier) { this.tier = tier; }

    public CriteriaType getCriteriaType() { return criteriaType; }
    public void setCriteriaType(CriteriaType criteriaType) { this.criteriaType = criteriaType; }

    public String getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(String thresholdValue) { this.thresholdValue = thresholdValue; }
}
