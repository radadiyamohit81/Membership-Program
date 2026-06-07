-- ─────────────────────────────────────────────────────────────────────────────
-- Seed data for FirstClub Membership Service
-- Runs automatically on startup (create-drop mode — data reloaded every restart)
-- ─────────────────────────────────────────────────────────────────────────────

-- ── Membership Plans ──────────────────────────────────────────────────────────
INSERT INTO membership_plans (plan_type, name, price, duration_in_days, description) VALUES
    ('MONTHLY',   'Monthly Plan',   99.00,  30,  'Flexible monthly membership'),
    ('QUARTERLY', 'Quarterly Plan', 249.00, 90,  'Save more with quarterly membership'),
    ('YEARLY',    'Yearly Plan',    799.00, 365, 'Best value yearly membership');

-- ── Membership Tiers ──────────────────────────────────────────────────────────
INSERT INTO membership_tiers (tier_type, name, description) VALUES
    ('SILVER',   'Silver',   'Entry level tier with basic perks'),
    ('GOLD',     'Gold',     'Mid tier with great discounts and free delivery'),
    ('PLATINUM', 'Platinum', 'Top tier with maximum benefits and priority support');

-- ── Tier Benefits (configurable per tier) ─────────────────────────────────────
-- Silver benefits
INSERT INTO tier_benefits (tier_id, benefit_type, benefit_value, description) VALUES
    (1, 'DISCOUNT_PERCENT', '5',    '5% discount on all orders'),
    (1, 'FREE_DELIVERY',    'true', 'Free delivery on orders above ₹500');

-- Gold benefits
INSERT INTO tier_benefits (tier_id, benefit_type, benefit_value, description) VALUES
    (2, 'DISCOUNT_PERCENT', '10',   '10% discount on all orders'),
    (2, 'FREE_DELIVERY',    'true', 'Free delivery on all orders'),
    (2, 'EXCLUSIVE_DEALS',  'true', 'Access to exclusive deals and early sales');

-- Platinum benefits
INSERT INTO tier_benefits (tier_id, benefit_type, benefit_value, description) VALUES
    (3, 'DISCOUNT_PERCENT', '20',   '20% discount on all orders'),
    (3, 'FREE_DELIVERY',    'true', 'Free delivery on all orders'),
    (3, 'EXCLUSIVE_DEALS',  'true', 'Access to exclusive deals and early sales'),
    (3, 'PRIORITY_SUPPORT', 'true', 'Priority customer support');

-- ── Tier Criteria (DB-configurable upgrade rules) ─────────────────────────────
-- Silver: order count > 3 OR order value > ₹1000
INSERT INTO tier_criteria (tier_id, criteria_type, threshold_value) VALUES
    (1, 'ORDER_COUNT', '3'),
    (1, 'ORDER_VALUE', '1000');

-- Gold: order count > 10 OR order value > ₹5000
INSERT INTO tier_criteria (tier_id, criteria_type, threshold_value) VALUES
    (2, 'ORDER_COUNT', '10'),
    (2, 'ORDER_VALUE', '5000');

-- Platinum: order count > 20 OR order value > ₹15000 OR cohort = VIP
INSERT INTO tier_criteria (tier_id, criteria_type, threshold_value) VALUES
    (3, 'ORDER_COUNT', '20'),
    (3, 'ORDER_VALUE', '15000'),
    (3, 'COHORT',      'VIP');
