package com.firstclub.membership.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstclub.membership.enums.MembershipStatus;
import com.firstclub.membership.model.UserMembership;

@Repository
public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {

    // Enforces one active membership per user — used before subscribing
    Optional<UserMembership> findByUserIdAndStatus(Long userId, MembershipStatus status);

    // Get all memberships for a user (history — ACTIVE + EXPIRED + CANCELLED)
    List<UserMembership> findByUserId(Long userId);

    // Used by expiry scheduler — finds active memberships past their expiry date
    List<UserMembership> findByStatusAndExpiryDateBefore(MembershipStatus status, LocalDateTime now);
}
