-- Message attachments table (for file uploads, images, etc.)
CREATE TABLE message_attachments (
                                     id BIGSERIAL PRIMARY KEY,
                                     message_id BIGINT NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
                                     file_name VARCHAR(255) NOT NULL,
                                     file_type VARCHAR(100),
                                     file_size BIGINT,
                                     file_url TEXT NOT NULL,
                                     thumbnail_url TEXT,
                                     metadata JSONB DEFAULT '{}',
                                     created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
