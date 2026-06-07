package com.firstclub.membership.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstclub.membership.dto.ChangeTierRequest;
import com.firstclub.membership.dto.EvaluateTierRequest;
import com.firstclub.membership.dto.MembershipPlanResponse;
import com.firstclub.membership.dto.MembershipTierResponse;
import com.firstclub.membership.dto.SubscribeRequest;
import com.firstclub.membership.dto.UserMembershipResponse;
import com.firstclub.membership.service.MembershipService;
import com.firstclub.membership.service.TierEvaluationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    private final MembershipService membershipService;
    private final TierEvaluationService tierEvaluationService;

    public MembershipController(MembershipService membershipService, TierEvaluationService tierEvaluationService) {
        this.membershipService = membershipService;
        this.tierEvaluationService = tierEvaluationService;
    }

    // ── GET /api/membership/plans ─────────────────────────────────────────────
    // Returns all available membership plans (MONTHLY, QUARTERLY, YEARLY)

    @GetMapping("/plans")
    public ResponseEntity<List<MembershipPlanResponse>> getAllPlans() {
        List<MembershipPlanResponse> plans = membershipService.getAllPlans()
                .stream()
                .map(MembershipPlanResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(plans);
    }

    // ── GET /api/membership/tiers ─────────────────────────────────────────────
    // Returns all available membership tiers (SILVER, GOLD, PLATINUM) with benefits

    @GetMapping("/tiers")
    public ResponseEntity<List<MembershipTierResponse>> getAllTiers() {
        List<MembershipTierResponse> tiers = membershipService.getAllTiers()
                .stream()
                .map(MembershipTierResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tiers);
    }

    // ── POST /api/membership/subscribe ────────────────────────────────────────
    // Subscribe a user to a plan + tier. Fails if user already has an active membership.

    @PostMapping("/subscribe")
    public ResponseEntity<UserMembershipResponse> subscribe(@Valid @RequestBody SubscribeRequest request) {
        UserMembershipResponse response = UserMembershipResponse.from(
                membershipService.subscribe(request.getUserId(), request.getPlanId(), request.getTierId())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ── GET /api/membership/{userId} ──────────────────────────────────────────
    // Get a user's current ACTIVE membership

    @GetMapping("/{userId}")
    public ResponseEntity<UserMembershipResponse> getCurrentMembership(@PathVariable Long userId) {
        UserMembershipResponse response = UserMembershipResponse.from(
                membershipService.getCurrentMembership(userId)
        );
        return ResponseEntity.ok(response);
    }

    // ── PUT /api/membership/{userId}/change-tier ──────────────────────────────
    // Upgrade or downgrade a user's tier on their active membership

    @PutMapping("/{userId}/change-tier")
    public ResponseEntity<UserMembershipResponse> changeTier(
            @PathVariable Long userId,
            @Valid @RequestBody ChangeTierRequest request) {
        UserMembershipResponse response = UserMembershipResponse.from(
                membershipService.changeTier(userId, request.getNewTierId())
        );
        return ResponseEntity.ok(response);
    }

    // ── DELETE /api/membership/{userId}/cancel ────────────────────────────────
    // Cancel a user's active membership

    @DeleteMapping("/{userId}/cancel")
    public ResponseEntity<UserMembershipResponse> cancelMembership(@PathVariable Long userId) {
        UserMembershipResponse response = UserMembershipResponse.from(
                membershipService.cancelMembership(userId)
        );
        return ResponseEntity.ok(response);
    }

    // ── GET /api/membership/{userId}/history ──────────────────────────────────
    // Get full membership history for a user (ACTIVE + EXPIRED + CANCELLED)

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<UserMembershipResponse>> getMembershipHistory(@PathVariable Long userId) {
        List<UserMembershipResponse> history = membershipService.getMembershipHistory(userId)
                .stream()
                .map(UserMembershipResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(history);
    }

    // ── POST /api/membership/evaluate-tier ────────────────────────────────────
    // Evaluates which tier a user qualifies for based on their order activity.
    // Uses DB-configured TierCriteria rules (ORDER_COUNT, ORDER_VALUE, COHORT)

    @PostMapping("/evaluate-tier")
    public ResponseEntity<MembershipTierResponse> evaluateTier(@Valid @RequestBody EvaluateTierRequest request) {
        MembershipTierResponse response = MembershipTierResponse.from(
                tierEvaluationService.evaluateTier(
                        request.getOrderCount(),
                        request.getOrderValue(),
                        request.getCohort()
                )
        );
        return ResponseEntity.ok(response);
    }
}
