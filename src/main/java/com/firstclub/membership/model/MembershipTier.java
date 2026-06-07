package com.firstclub.membership.model;

import java.util.List;

import com.firstclub.membership.enums.TierType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "membership_tiers")
public class MembershipTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TierType tierType;      // SILVER, GOLD, PLATINUM

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "tier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TierBenefit> benefits;

    @OneToMany(mappedBy = "tier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TierCriteria> criteriaList;

    // ── Constructors ────────────────────────────────────────────────────────

    public MembershipTier() {}

    public MembershipTier(Long id, TierType tierType, String name, String description) {
        this.id = id;
        this.tierType = tierType;
        this.name = name;
        this.description = description;
    }

    // ── Getters & Setters ───────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TierType getTierType() { return tierType; }
    public void setTierType(TierType tierType) { this.tierType = tierType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<TierBenefit> getBenefits() { return benefits; }
    public void setBenefits(List<TierBenefit> benefits) { this.benefits = benefits; }

    public List<TierCriteria> getCriteriaList() { return criteriaList; }
    public void setCriteriaList(List<TierCriteria> criteriaList) { this.criteriaList = criteriaList; }
}
