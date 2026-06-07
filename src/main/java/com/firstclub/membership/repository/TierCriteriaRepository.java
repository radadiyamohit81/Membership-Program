package com.firstclub.membership.repository;

import com.firstclub.membership.model.MembershipTier;
import com.firstclub.membership.model.TierCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TierCriteriaRepository extends JpaRepository<TierCriteria, Long> {

    // Get all criteria rules for a given tier — used during tier evaluation
    List<TierCriteria> findByTier(MembershipTier tier);
}
