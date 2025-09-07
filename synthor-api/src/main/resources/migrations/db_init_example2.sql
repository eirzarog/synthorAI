-- Chat AI Web App PostgreSQL Schema
-- Created: 2025-08-31

-- Enable UUID extension for generating UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create enum types
CREATE TYPE user_status AS ENUM ('active', 'inactive', 'suspended', 'deleted');
CREATE TYPE subscription_status AS ENUM ('active', 'cancelled', 'expired', 'trial', 'suspended');
CREATE TYPE subscription_tier AS ENUM ('free', 'basic', 'premium', 'enterprise');
CREATE TYPE message_type AS ENUM ('user', 'assistant', 'system');
CREATE TYPE chat_status AS ENUM ('active', 'archived', 'deleted');

-- Users table
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       email VARCHAR(255) UNIQUE NOT NULL,
                       username VARCHAR(50) UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       avatar_url TEXT,
                       status user_status DEFAULT 'active',
                       email_verified BOOLEAN DEFAULT FALSE,
                       last_login_at TIMESTAMP WITH TIME ZONE,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Roles table
CREATE TABLE roles (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       name VARCHAR(50) UNIQUE NOT NULL,
                       description TEXT,
                       permissions JSONB DEFAULT '{}',
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- User roles junction table
CREATE TABLE user_roles (
                            user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                            role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
                            assigned_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            assigned_by UUID REFERENCES users(id),
                            PRIMARY KEY (user_id, role_id)
);

-- AI Models table
CREATE TABLE ai_models (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           name VARCHAR(100) NOT NULL,
                           provider VARCHAR(50) NOT NULL, -- e.g., 'openai', 'anthropic', 'google'
                           model_id VARCHAR(100) NOT NULL, -- e.g., 'gpt-4', 'claude-3-opus'
                           version VARCHAR(20),
                           description TEXT,
                           context_window INTEGER,
                           max_tokens INTEGER,
                           input_cost_per_token DECIMAL(10, 8), -- Cost per input token
                           output_cost_per_token DECIMAL(10, 8), -- Cost per output token
                           capabilities JSONB DEFAULT '{}', -- JSON object for model capabilities
                           is_active BOOLEAN DEFAULT TRUE,
                           created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           UNIQUE(provider, model_id, version)
);

-- Subscriptions table
CREATE TABLE subscriptions (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               tier subscription_tier NOT NULL DEFAULT 'free',
                               status subscription_status NOT NULL DEFAULT 'trial',
                               monthly_message_limit INTEGER,
                               monthly_token_limit BIGINT,
                               allowed_models JSONB DEFAULT '[]', -- Array of model IDs user can access
                               stripe_subscription_id VARCHAR(255),
                               stripe_customer_id VARCHAR(255),
                               current_period_start TIMESTAMP WITH TIME ZONE,
                               current_period_end TIMESTAMP WITH TIME ZONE,
                               trial_end TIMESTAMP WITH TIME ZONE,
                               cancelled_at TIMESTAMP WITH TIME ZONE,
                               created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Usage tracking table
CREATE TABLE usage_tracking (
                                id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                subscription_id UUID REFERENCES subscriptions(id) ON DELETE SET NULL,
                                period_start TIMESTAMP WITH TIME ZONE NOT NULL,
                                period_end TIMESTAMP WITH TIME ZONE NOT NULL,
                                messages_used INTEGER DEFAULT 0,
                                tokens_used BIGINT DEFAULT 0,
                                cost_incurred DECIMAL(10, 4) DEFAULT 0.00,
                                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                UNIQUE(user_id, period_start)
);

-- Chats table
CREATE TABLE chats (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       title VARCHAR(255),
                       ai_model_id UUID REFERENCES ai_models(id),
                       system_prompt TEXT,
                       status chat_status DEFAULT 'active',
                       settings JSONB DEFAULT '{}', -- Chat-specific settings (temperature, etc.)
                       metadata JSONB DEFAULT '{}', -- Additional metadata
                       message_count INTEGER DEFAULT 0,
                       total_tokens INTEGER DEFAULT 0,
                       last_message_at TIMESTAMP WITH TIME ZONE,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Messages table
CREATE TABLE messages (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          chat_id UUID NOT NULL REFERENCES chats(id) ON DELETE CASCADE,
                          user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          ai_model_id UUID REFERENCES ai_models(id),
                          type message_type NOT NULL,
                          content TEXT NOT NULL,
                          role VARCHAR(20) NOT NULL, -- 'user', 'assistant', 'system'
                          sequence_number INTEGER NOT NULL,
                          input_tokens INTEGER,
                          output_tokens INTEGER,
                          cost DECIMAL(10, 6),
                          processing_time_ms INTEGER,
                          metadata JSONB DEFAULT '{}', -- Additional message metadata
                          parent_message_id UUID REFERENCES messages(id), -- For conversation threading
                          edited_at TIMESTAMP WITH TIME ZONE,
    -- Reaction counters for performance
                          like_count INTEGER DEFAULT 0,
                          dislike_count INTEGER DEFAULT 0,
                          feedback_count INTEGER DEFAULT 0,
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          UNIQUE(chat_id, sequence_number)
);

-- Message attachments table (for file uploads, images, etc.)
CREATE TABLE message_attachments (
                                     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                     message_id UUID NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
                                     file_name VARCHAR(255) NOT NULL,
                                     file_type VARCHAR(100),
                                     file_size BIGINT,
                                     file_url TEXT NOT NULL,
                                     thumbnail_url TEXT,
                                     metadata JSONB DEFAULT '{}',
                                     created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Chat sharing table (for shared conversations)
CREATE TABLE chat_shares (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             chat_id UUID NOT NULL REFERENCES chats(id) ON DELETE CASCADE,
                             shared_by UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             share_token VARCHAR(100) UNIQUE NOT NULL,
                             is_public BOOLEAN DEFAULT FALSE,
                             expires_at TIMESTAMP WITH TIME ZONE,
                             view_count INTEGER DEFAULT 0,
                             created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- API keys table (for API access)
CREATE TABLE api_keys (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          name VARCHAR(100) NOT NULL,
                          key_hash VARCHAR(255) NOT NULL,
                          key_prefix VARCHAR(20) NOT NULL, -- First few characters for identification
                          permissions JSONB DEFAULT '{}',
                          last_used_at TIMESTAMP WITH TIME ZONE,
                          expires_at TIMESTAMP WITH TIME ZONE,
                          is_active BOOLEAN DEFAULT TRUE,
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Message reactions table (likes/dislikes)
CREATE TABLE message_reactions (
                                   id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                   message_id UUID NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
                                   user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                   reaction_type VARCHAR(20) NOT NULL CHECK (reaction_type IN ('like', 'dislike')),
                                   created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   UNIQUE(message_id, user_id) -- One reaction per user per message
);

-- Enhanced feedback table
CREATE TABLE feedback (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          user_id UUID REFERENCES users(id) ON DELETE SET NULL,
                          message_id UUID REFERENCES messages(id) ON DELETE CASCADE,
                          chat_id UUID REFERENCES chats(id) ON DELETE CASCADE,
                          rating INTEGER CHECK (rating >= 1 AND rating <= 5),
                          feedback_text TEXT,
                          feedback_type VARCHAR(50), -- 'helpful', 'unhelpful', 'report', 'improvement_suggestion', etc.
                          reaction_id UUID REFERENCES message_reactions(id) ON DELETE SET NULL, -- Link to reaction if exists
                          categories TEXT[], -- Array of feedback categories
                          metadata JSONB DEFAULT '{}',
                          is_public BOOLEAN DEFAULT FALSE, -- For sharing feedback publicly
                          moderator_reviewed BOOLEAN DEFAULT FALSE,
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_users_created_at ON users(created_at);

CREATE INDEX idx_chats_user_id ON chats(user_id);
CREATE INDEX idx_chats_status ON chats(status);
CREATE INDEX idx_chats_created_at ON chats(created_at);
CREATE INDEX idx_chats_last_message_at ON chats(last_message_at);

CREATE INDEX idx_messages_chat_id ON messages(chat_id);
CREATE INDEX idx_messages_user_id ON messages(user_id);
CREATE INDEX idx_messages_type ON messages(type);
CREATE INDEX idx_messages_created_at ON messages(created_at);
CREATE INDEX idx_messages_sequence ON messages(chat_id, sequence_number);

CREATE INDEX idx_message_reactions_message_id ON message_reactions(message_id);
CREATE INDEX idx_message_reactions_user_id ON message_reactions(user_id);
CREATE INDEX idx_message_reactions_type ON message_reactions(reaction_type);
CREATE INDEX idx_message_reactions_created_at ON message_reactions(created_at);

CREATE INDEX idx_subscriptions_user_id ON subscriptions(user_id);
CREATE INDEX idx_subscriptions_status ON subscriptions(status);
CREATE INDEX idx_subscriptions_period ON subscriptions(current_period_start, current_period_end);

CREATE INDEX idx_usage_tracking_user_period ON usage_tracking(user_id, period_start);
CREATE INDEX idx_ai_models_provider ON ai_models(provider);
CREATE INDEX idx_ai_models_active ON ai_models(is_active);

CREATE INDEX idx_message_attachments_message_id ON message_attachments(message_id);
CREATE INDEX idx_chat_shares_token ON chat_shares(share_token);
CREATE INDEX idx_api_keys_user_id ON api_keys(user_id);
CREATE INDEX idx_api_keys_active ON api_keys(is_active);
CREATE INDEX idx_feedback_message_id ON feedback(message_id);
CREATE INDEX idx_feedback_user_id ON feedback(user_id);
CREATE INDEX idx_feedback_type ON feedback(feedback_type);

-- Create functions for automatic timestamp updates
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Trigger for reaction updates
CREATE TRIGGER update_message_reactions_updated_at BEFORE UPDATE ON message_reactions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_feedback_updated_at BEFORE UPDATE ON feedback
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Function to update message reaction counters
CREATE OR REPLACE FUNCTION update_message_reaction_counters()
    RETURNS TRIGGER AS $
BEGIN
IF TG_OP = 'INSERT' THEN
        -- Increment counter
        IF NEW.reaction_type = 'like' THEN
UPDATE messages SET like_count = like_count + 1 WHERE id = NEW.message_id;
ELSIF NEW.reaction_type = 'dislike' THEN
UPDATE messages SET dislike_count = dislike_count + 1 WHERE id = NEW.message_id;
END IF;
RETURN NEW;
ELSIF TG_OP = 'UPDATE' THEN
        -- Handle reaction type change
        IF OLD.reaction_type != NEW.reaction_type THEN
            -- Decrement old counter
            IF OLD.reaction_type = 'like' THEN
UPDATE messages SET like_count = like_count - 1 WHERE id = OLD.message_id;
ELSIF OLD.reaction_type = 'dislike' THEN
UPDATE messages SET dislike_count = dislike_count - 1 WHERE id = OLD.message_id;
END IF;
-- Increment new counter
IF NEW.reaction_type = 'like' THEN
UPDATE messages SET like_count = like_count + 1 WHERE id = NEW.message_id;
ELSIF NEW.reaction_type = 'dislike' THEN
UPDATE messages SET dislike_count = dislike_count + 1 WHERE id = NEW.message_id;
END IF;
END IF;
RETURN NEW;
ELSIF TG_OP = 'DELETE' THEN
        -- Decrement counter
        IF OLD.reaction_type = 'like' THEN
UPDATE messages SET like_count = like_count - 1 WHERE id = OLD.message_id;
ELSIF OLD.reaction_type = 'dislike' THEN
UPDATE messages SET dislike_count = dislike_count - 1 WHERE id = OLD.message_id;
END IF;
RETURN OLD;
END IF;
RETURN NULL;
END;
$ language 'plpgsql';

-- Function to update message feedback counter
CREATE OR REPLACE FUNCTION update_message_feedback_counter()
    RETURNS TRIGGER AS $
BEGIN
IF TG_OP = 'INSERT' THEN
UPDATE messages SET feedback_count = feedback_count + 1 WHERE id = NEW.message_id;
RETURN NEW;
ELSIF TG_OP = 'DELETE' THEN
UPDATE messages SET feedback_count = feedback_count - 1 WHERE id = OLD.message_id;
RETURN OLD;
END IF;
RETURN NULL;
END;
$ language 'plpgsql';

-- Create triggers for automatic timestamp updates
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_roles_updated_at BEFORE UPDATE ON roles
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_ai_models_updated_at BEFORE UPDATE ON ai_models
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_subscriptions_updated_at BEFORE UPDATE ON subscriptions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_usage_tracking_updated_at BEFORE UPDATE ON usage_tracking
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_chats_updated_at BEFORE UPDATE ON chats
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Function to update chat statistics
CREATE OR REPLACE FUNCTION update_chat_stats()
    RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        UPDATE chats
        SET
            message_count = message_count + 1,
            total_tokens = total_tokens + COALESCE(NEW.input_tokens, 0) + COALESCE(NEW.output_tokens, 0),
            last_message_at = NEW.created_at
        WHERE id = NEW.chat_id;
    ELSIF TG_OP = 'DELETE' THEN
        UPDATE chats
        SET
            message_count = message_count - 1,
            total_tokens = total_tokens - COALESCE(OLD.input_tokens, 0) - COALESCE(OLD.output_tokens, 0)
        WHERE id = OLD.chat_id;
    END IF;

    RETURN COALESCE(NEW, OLD);
END;
$$ language 'plpgsql';

-- Trigger to automatically update chat statistics
CREATE TRIGGER update_chat_message_stats
    AFTER INSERT OR DELETE ON messages
    FOR EACH ROW EXECUTE FUNCTION update_chat_stats();

-- Insert default roles
INSERT INTO roles (name, description, permissions) VALUES
                                                       ('admin', 'Full system administration access', '{"all": true}'),
                                                       ('user', 'Standard user access', '{"chat": true, "api": false}'),
                                                       ('premium_user', 'Premium user with extended features', '{"chat": true, "api": true, "priority_support": true}'),
                                                       ('moderator', 'Content moderation capabilities', '{"chat": true, "moderate": true}');

-- Insert sample AI models
INSERT INTO ai_models (name, provider, model_id, version, description, context_window, max_tokens, input_cost_per_token, output_cost_per_token, capabilities) VALUES
                                                                                                                                                                  ('GPT-4', 'openai', 'gpt-4', '0613', 'Most capable GPT-4 model', 8192, 4096, 0.00003, 0.00006, '{"vision": false, "function_calling": true, "json_mode": true}'),
                                                                                                                                                                  ('GPT-4 Turbo', 'openai', 'gpt-4-turbo', 'latest', 'Faster and more affordable GPT-4', 128000, 4096, 0.00001, 0.00003, '{"vision": true, "function_calling": true, "json_mode": true}'),
                                                                                                                                                                  ('Claude 3 Opus', 'anthropic', 'claude-3-opus', 'latest', 'Most powerful Claude model', 200000, 4096, 0.000015, 0.000075, '{"vision": true, "function_calling": false, "json_mode": false}'),
                                                                                                                                                                  ('Claude 3 Sonnet', 'anthropic', 'claude-3-sonnet', 'latest', 'Balanced performance and speed', 200000, 4096, 0.000003, 0.000015, '{"vision": true, "function_calling": false, "json_mode": false}'),
                                                                                                                                                                  ('Gemini Pro', 'google', 'gemini-pro', 'latest', 'Google''s most capable model', 32000, 2048, 0.0000005, 0.0000015, '{"vision": false, "function_calling": true, "json_mode": true}');

-- Create views for common queries

-- Active users with their current subscription
CREATE VIEW active_users_with_subscription AS
SELECT
    u.id,
    u.email,
    u.username,
    u.first_name,
    u.last_name,
    u.status as user_status,
    s.tier as subscription_tier,
    s.status as subscription_status,
    s.monthly_message_limit,
    s.monthly_token_limit,
    u.created_at as user_created_at,
    u.last_login_at
FROM users u
         LEFT JOIN subscriptions s ON u.id = s.user_id
WHERE u.status = 'active';

-- Enhanced chat summary view with reaction stats
CREATE VIEW chat_summary AS
SELECT
    c.id,
    c.title,
    c.user_id,
    u.username,
    c.ai_model_id,
    am.name as model_name,
    c.message_count,
    c.total_tokens,
    c.status,
    c.last_message_at,
    c.created_at,
    COALESCE(SUM(m.like_count), 0) as total_likes,
    COALESCE(SUM(m.dislike_count), 0) as total_dislikes,
    COALESCE(SUM(m.feedback_count), 0) as total_feedback
FROM chats c
         JOIN users u ON c.user_id = u.id
         LEFT JOIN ai_models am ON c.ai_model_id = am.id
         LEFT JOIN messages m ON c.id = m.chat_id AND m.role = 'assistant'
WHERE c.status != 'deleted'
GROUP BY c.id, c.title, c.user_id, u.username, c.ai_model_id, am.name,
         c.message_count, c.total_tokens, c.status, c.last_message_at, c.created_at;

-- Message reactions summary view
CREATE VIEW message_reactions_summary AS
SELECT
    m.id as message_id,
    m.chat_id,
    m.content,
    m.role,
    m.like_count,
    m.dislike_count,
    m.feedback_count,
    m.created_at as message_created_at,
    u.username as message_author,
    am.name as ai_model_name
FROM messages m
         LEFT JOIN users u ON m.user_id = u.id
         LEFT JOIN ai_models am ON m.ai_model_id = am.id
WHERE m.role = 'assistant' -- Only AI responses can be liked/disliked
ORDER BY m.created_at DESC;

-- User reaction activity view
CREATE VIEW user_reaction_activity AS
SELECT
    u.id as user_id,
    u.username,
    COUNT(CASE WHEN mr.reaction_type = 'like' THEN 1 END) as total_likes_given,
    COUNT(CASE WHEN mr.reaction_type = 'dislike' THEN 1 END) as total_dislikes_given,
    COUNT(f.id) as total_feedback_given,
    MAX(mr.created_at) as last_reaction_at,
    MAX(f.created_at) as last_feedback_at
FROM users u
         LEFT JOIN message_reactions mr ON u.id = mr.user_id
         LEFT JOIN feedback f ON u.id = f.user_id
WHERE u.status = 'active'
GROUP BY u.id, u.username;

-- User usage summary view
CREATE VIEW user_usage_summary AS
SELECT
    u.id as user_id,
    u.email,
    u.username,
    COUNT(DISTINCT c.id) as total_chats,
    COUNT(DISTINCT m.id) as total_messages,
    SUM(m.input_tokens + m.output_tokens) as total_tokens_used,
    SUM(m.cost) as total_cost,
    MAX(m.created_at) as last_message_at
FROM users u
         LEFT JOIN chats c ON u.id = c.user_id AND c.status != 'deleted'
         LEFT JOIN messages m ON c.id = m.chat_id
WHERE u.status = 'active'
GROUP BY u.id, u.email, u.username;

-- Application configuration table (for non-sensitive settings)
CREATE TABLE app_config (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            key VARCHAR(100) UNIQUE NOT NULL,
                            value TEXT,
                            value_type VARCHAR(20) DEFAULT 'string', -- 'string', 'number', 'boolean', 'json'
                            description TEXT,
                            is_encrypted BOOLEAN DEFAULT FALSE,
                            is_public BOOLEAN DEFAULT FALSE, -- Can be exposed to frontend
                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Index for quick config lookups
CREATE INDEX idx_app_config_key ON app_config(key);
CREATE INDEX idx_app_config_public ON app_config(is_public);

-- Trigger for config updates
CREATE TRIGGER update_app_config_updated_at BEFORE UPDATE ON app_config
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Example config entries
INSERT INTO app_config (key, value, value_type, description, is_public) VALUES
                                                                            ('max_chat_title_length', '100', 'number', 'Maximum length for chat titles', true),
                                                                            ('default_ai_model', 'gpt-4-turbo', 'string', 'Default AI model for new chats', true),
                                                                            ('maintenance_mode', 'false', 'boolean', 'Enable maintenance mode', false),
                                                                            ('rate_limit_per_minute', '60', 'number', 'API rate limit per minute per user', false),
                                                                            ('feature_flags', '{"vision_enabled": true, "api_access": true}', 'json', 'Feature toggles', true);

COMMENT ON TABLE users IS 'Application users with authentication and profile information';
COMMENT ON TABLE roles IS 'Role-based access control definitions';
COMMENT ON TABLE user_roles IS 'Many-to-many relationship between users and roles';
COMMENT ON TABLE ai_models IS 'Available AI models with pricing and capability information';
COMMENT ON TABLE subscriptions IS 'User subscription plans and limits';
COMMENT ON TABLE usage_tracking IS 'Monthly usage tracking for billing and limits';
COMMENT ON TABLE chats IS 'Individual chat conversations';
COMMENT ON TABLE messages IS 'Individual messages within chats with reaction counters';
COMMENT ON TABLE message_reactions IS 'User reactions (like/dislike) to AI responses';
COMMENT ON TABLE message_attachments IS 'File attachments for messages';
COMMENT ON TABLE chat_shares IS 'Shared chat conversations with public access';
COMMENT ON TABLE api_keys IS 'API keys for programmatic access';
COMMENT ON TABLE feedback IS 'User feedback on messages and conversations with categories';

-- Example queries for reaction functionality:

-- Get message with user's reaction status
/*
SELECT
    m.*,
    mr.reaction_type as user_reaction,
    mr.created_at as reaction_date
FROM messages m
LEFT JOIN message_reactions mr ON m.id = mr.message_id AND mr.user_id = $1
WHERE m.chat_id = $2
ORDER BY m.sequence_number ASC;
*/

-- Get most liked AI responses
/*
SELECT
    m.id,
    m.content,
    m.like_count,
    m.dislike_count,
    m.like_count - m.dislike_count as net_score,
    c.title as chat_title,
    u.username,
    am.name as model_name
FROM messages m
JOIN chats c ON m.chat_id = c.id
JOIN users u ON c.user_id = u.id
LEFT JOIN ai_models am ON m.ai_model_id = am.id
WHERE m.role = 'assistant'
    AND m.like_count > 0
ORDER BY m.like_count DESC, net_score DESC
LIMIT 50;
*/

-- Toggle user reaction (upsert operation)
/*
INSERT INTO message_reactions (message_id, user_id, reaction_type)
VALUES ($1, $2, $3)
ON CONFLICT (message_id, user_id)
DO UPDATE SET
    reaction_type = EXCLUDED.reaction_type,
    updated_at = CURRENT_TIMESTAMP
RETURNING *;
*/

-- Remove user reaction
/*
DELETE FROM message_reactions
WHERE message_id = $1 AND user_id = $2
RETURNING *;
*/

-- Get feedback with reactions
/*
SELECT
    f.*,
    mr.reaction_type,
    m.content as message_content,
    u.username
FROM feedback f
LEFT JOIN message_reactions mr ON f.reaction_id = mr.id
LEFT JOIN messages m ON f.message_id = m.id
LEFT JOIN users u ON f.user_id = u.id
WHERE f.message_id = $1
ORDER BY f.created_at DESC;
*/

-- Example queries for common operations:

-- Get user's active chats with latest message
/*
SELECT
    c.id,
    c.title,
    c.message_count,
    c.last_message_at,
    am.name as model_name
FROM chats c
LEFT JOIN ai_models am ON c.ai_model_id = am.id
WHERE c.user_id = $1 AND c.status = 'active'
ORDER BY c.last_message_at DESC;
*/

-- Get chat history with messages
/*
SELECT
    m.id,
    m.type,
    m.role,
    m.content,
    m.sequence_number,
    m.created_at,
    u.username
FROM messages m
JOIN users u ON m.user_id = u.id
WHERE m.chat_id = $1
ORDER BY m.sequence_number ASC;
*/

-- Check user's monthly usage
/*
SELECT
    ut.messages_used,
    ut.tokens_used,
    s.monthly_message_limit,
    s.monthly_token_limit,
    (ut.messages_used::float / NULLIF(s.monthly_message_limit, 0) * 100) as message_usage_percent,
    (ut.tokens_used::float / NULLIF(s.monthly_token_limit, 0) * 100) as token_usage_percent
FROM usage_tracking ut
JOIN subscriptions s ON ut.user_id = s.user_id
WHERE ut.user_id = $1
    AND ut.period_start <= CURRENT_TIMESTAMP
    AND ut.period_end > CURRENT_TIMESTAMP
    AND s.status = 'active';
*/