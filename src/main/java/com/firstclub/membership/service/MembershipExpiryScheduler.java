package com.firstclub.membership.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firstclub.membership.enums.MembershipStatus;
import com.firstclub.membership.model.UserMembership;
import com.firstclub.membership.repository.UserMembershipRepository;

@Service
public class MembershipExpiryScheduler {

    private final UserMembershipRepository userMembershipRepository;

    public MembershipExpiryScheduler(UserMembershipRepository userMembershipRepository) {
        this.userMembershipRepository = userMembershipRepository;
    }

    /**
     * Runs every hour and marks expired memberships as EXPIRED.
     * A membership is expired if: status=ACTIVE and expiryDate < now
     *
     * cron = "0 0 * * * *" means: at second=0, minute=0, every hour, every day
     */
    @Scheduled(fixedRate = 10000) // runs every 10 seconds — change to cron "0 0 * * * *" in production
    @Transactional
    public void expireActiveMemberships() {
        List<UserMembership> expiredMemberships = userMembershipRepository
                .findByStatusAndExpiryDateBefore(MembershipStatus.ACTIVE, LocalDateTime.now());

        if (expiredMemberships.isEmpty()) return;

        expiredMemberships.forEach(m -> m.setStatus(MembershipStatus.EXPIRED));
        userMembershipRepository.saveAll(expiredMemberships);

        System.out.println("[ExpiryScheduler] Expired " + expiredMemberships.size() + " memberships at " + LocalDateTime.now());
    }
}
