package com.firstclub.membership.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firstclub.membership.enums.MembershipStatus;
import com.firstclub.membership.exception.MembershipAlreadyActiveException;
import com.firstclub.membership.exception.MembershipNotFoundException;
import com.firstclub.membership.exception.PlanNotFoundException;
import com.firstclub.membership.exception.TierNotFoundException;
import com.firstclub.membership.model.MembershipPlan;
import com.firstclub.membership.model.MembershipTier;
import com.firstclub.membership.model.UserMembership;
import com.firstclub.membership.repository.MembershipPlanRepository;
import com.firstclub.membership.repository.MembershipTierRepository;
import com.firstclub.membership.repository.UserMembershipRepository;

@Service
public class MembershipService {

    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;
    private final UserMembershipRepository userMembershipRepository;

    public MembershipService(MembershipPlanRepository planRepository,
                             MembershipTierRepository tierRepository,
                             UserMembershipRepository userMembershipRepository) {
        this.planRepository = planRepository;
        this.tierRepository = tierRepository;
        this.userMembershipRepository = userMembershipRepository;
    }

    // ── Get all available plans ──────────────────────────────────────────────

    public List<MembershipPlan> getAllPlans() {
        return planRepository.findAll();
    }

    // ── Get all available tiers ──────────────────────────────────────────────

    public List<MembershipTier> getAllTiers() {
        return tierRepository.findAll();
    }

    // ── Subscribe to a plan + tier ───────────────────────────────────────────

    @Transactional
    public UserMembership subscribe(Long userId, Long planId, Long tierId) {
        // Enforce one active membership per user
        userMembershipRepository.findByUserIdAndStatus(userId, MembershipStatus.ACTIVE)
                .ifPresent(existing -> { throw new MembershipAlreadyActiveException(userId); });

        MembershipPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new PlanNotFoundException(planId));

        MembershipTier tier = tierRepository.findById(tierId)
                .orElseThrow(() -> new TierNotFoundException(tierId));

        LocalDateTime now = LocalDateTime.now();

        UserMembership membership = UserMembership.builder()
                .userId(userId)
                .plan(plan)
                .tier(tier)
                .status(MembershipStatus.ACTIVE)
                .startDate(now)
                .expiryDate(now.plusDays(plan.getDurationInDays())) // calculate expiry from plan duration
                .build();

        return userMembershipRepository.save(membership);
    }

    // ── Get current active membership for a user ─────────────────────────────

    public UserMembership getCurrentMembership(Long userId) {
        return userMembershipRepository.findByUserIdAndStatus(userId, MembershipStatus.ACTIVE)
                .orElseThrow(() -> new MembershipNotFoundException(userId));
    }

    // ── Upgrade or downgrade tier ────────────────────────────────────────────

    @Transactional
    public UserMembership changeTier(Long userId, Long newTierId) {
        UserMembership membership = getCurrentMembership(userId);

        MembershipTier newTier = tierRepository.findById(newTierId)
                .orElseThrow(() -> new TierNotFoundException(newTierId));

        membership.setTier(newTier);   // update tier (upgrade or downgrade)

        return userMembershipRepository.save(membership);
    }

    // ── Cancel membership ────────────────────────────────────────────────────

    @Transactional
    public UserMembership cancelMembership(Long userId) {
        UserMembership membership = getCurrentMembership(userId);

        membership.setStatus(MembershipStatus.CANCELLED);

        return userMembershipRepository.save(membership);
    }

    // ── Get membership history for a user ────────────────────────────────────

    public List<UserMembership> getMembershipHistory(Long userId) {
        return userMembershipRepository.findByUserId(userId);
    }
}
