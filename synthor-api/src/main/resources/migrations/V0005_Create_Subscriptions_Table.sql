-- Subscriptions table
CREATE TABLE subscriptions (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               subscription_tier subscription_tier NOT NULL DEFAULT 'FREE',
                               subscription_status subscription_status NOT NULL DEFAULT 'TRIAL',
                               monthly_message_limit INTEGER,
                               monthly_token_limit BIGINT,
                               current_period_start TIMESTAMP WITH TIME ZONE,
                               current_period_end TIMESTAMP WITH TIME ZONE,
                               trial_end TIMESTAMP WITH TIME ZONE,
                               cancelled_at TIMESTAMP WITH TIME ZONE,
-- Inherited from BaseModel
                               created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP




);

INSERT INTO subscriptions (
    user_id,
    subscription_tier,
    subscription_status,
    monthly_message_limit,
    monthly_token_limit,
    current_period_start,
    current_period_end,
    trial_end,
    cancelled_at
) VALUES (
             1, -- assuming user with ID 1 exists
             'PREMIUM',
             'ACTIVE',
             10000,
             5000000,
             NOW(),
             NOW() + INTERVAL '30 days',
             NOW() + INTERVAL '7 days',
             NULL -- not cancelled
         );
