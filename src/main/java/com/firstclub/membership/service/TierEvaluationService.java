package com.firstclub.membership.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.firstclub.membership.model.MembershipTier;
import com.firstclub.membership.model.TierCriteria;
import com.firstclub.membership.repository.MembershipTierRepository;
import com.firstclub.membership.repository.TierCriteriaRepository;

@Service
public class TierEvaluationService {

    private final MembershipTierRepository tierRepository;
    private final TierCriteriaRepository tierCriteriaRepository;

    public TierEvaluationService(MembershipTierRepository tierRepository,
                                 TierCriteriaRepository tierCriteriaRepository) {
        this.tierRepository = tierRepository;
        this.tierCriteriaRepository = tierCriteriaRepository;
    }

    /**
     * Evaluates which tier a user qualifies for based on their activity.
     * Any single matching criteria = user qualifies for that tier (OR logic).
     *
     * @param orderCount  total number of orders by the user
     * @param orderValue  total order value (in rupees) by the user
     * @param cohort      user's cohort label (e.g. "VIP", "EMPLOYEE")
     * @return the highest tier the user qualifies for, or SILVER as default
     */
    public MembershipTier evaluateTier(int orderCount, double orderValue, String cohort) {
        List<MembershipTier> allTiers = tierRepository.findAll();

        MembershipTier qualifiedTier = null;

        for (MembershipTier tier : allTiers) {
            List<TierCriteria> criteriaList = tierCriteriaRepository.findByTier(tier);

            // Check if user meets ANY criteria for this tier (OR logic)
            boolean qualifies = criteriaList.stream().anyMatch(criteria ->
                    matchesCriteria(criteria, orderCount, orderValue, cohort)
            );

            if (qualifies) {
                // Pick the "highest" qualifying tier
                // Assumes tiers are ordered by id (SILVER < GOLD < PLATINUM)
                if (qualifiedTier == null || tier.getId() > qualifiedTier.getId()) {
                    qualifiedTier = tier;
                }
            }
        }

        // Default to SILVER tier if no criteria matched
        if (qualifiedTier == null) {
            qualifiedTier = tierRepository.findAll().stream()
                    .filter(t -> t.getTierType().name().equals("SILVER"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Default SILVER tier not configured in DB"));
        }

        return qualifiedTier;
    }

    /**
     * Checks if a single criteria rule matches the user's activity.
     */
    private boolean matchesCriteria(TierCriteria criteria, int orderCount, double orderValue, String cohort) {
        return switch (criteria.getCriteriaType()) {
            case ORDER_COUNT -> orderCount >= Integer.parseInt(criteria.getThresholdValue());
            case ORDER_VALUE -> orderValue >= Double.parseDouble(criteria.getThresholdValue());
            case COHORT -> cohort != null && cohort.equalsIgnoreCase(criteria.getThresholdValue());
        };
    }
}
