-- Chat sharing table (for shared conversations)
CREATE TABLE chat_shares (
                             id BIGSERIAL PRIMARY KEY,
                             chat_id BIGINT NOT NULL REFERENCES chats(id) ON DELETE CASCADE,
                             shared_by BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             share_token VARCHAR(100) UNIQUE NOT NULL,
                             is_public BOOLEAN DEFAULT FALSE,
                             expires_at TIMESTAMP WITH TIME ZONE,
                             view_count INTEGER DEFAULT 0,
                             created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);