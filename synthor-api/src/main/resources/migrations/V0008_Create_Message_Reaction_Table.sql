-- Message reactions table (likes/dislikes)
CREATE TABLE message_reactions (
                                   id BIGSERIAL PRIMARY KEY,
                                   message_id BIGINT NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
                                   user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                   reaction_type VARCHAR(20) NOT NULL CHECK (reaction_type IN ('LIKE', 'DISLIKE')),
                                   created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   UNIQUE(message_id, user_id) -- One reaction per user per message
);
