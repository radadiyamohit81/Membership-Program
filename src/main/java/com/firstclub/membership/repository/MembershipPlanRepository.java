package com.firstclub.membership.repository;

import com.firstclub.membership.enums.PlanType;
import com.firstclub.membership.model.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {

    // Find plan by type — used when user selects MONTHLY / QUARTERLY / YEARLY
    Optional<MembershipPlan> findByPlanType(PlanType planType);
}
