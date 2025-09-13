
-- AI Models table
CREATE TABLE IF NOT EXISTS public.models (
                                            id BIGSERIAL,
                                            name TEXT NOT NULL,
                                            provider TEXT NOT NULL, -- e.g., 'openai', 'anthropic', 'google',
                                            description TEXT,
-- Inherited from BaseEntity
                                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

                                            PRIMARY KEY (id)
);


-- Insert sample models
INSERT INTO models (name, provider, description)
VALUES
    ('GPT-4', 'openai', 'Most capable GPT-4 model'),
    ('GPT-4 Turbo', 'openai', 'Faster and more affordable GPT-4'),
    ('Claude 3 Opus', 'anthropic', 'Most powerful Claude model'),
    ('Gemini Pro', 'google', 'Google''s advanced language model'),
    ('Llama 3', 'meta',  'Meta''s large language model');

