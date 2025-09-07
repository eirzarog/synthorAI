
-- AI Models table
CREATE TABLE IF NOT EXISTS public.ai_models (
                                            id BIGSERIAL PRIMARY KEY,
                                            name TEXT NOT NULL,
                                            provider TEXT NOT NULL, -- e.g., 'openai', 'anthropic', 'google'
                                            model_id TEXT NOT NULL, -- e.g., 'gpt-4', 'claude-3-opus'
                                            version TEXT NOT NULL,
                                            description TEXT,
                                            context_window INTEGER,
                                            max_tokens INTEGER,
                                            input_cost_per_token DECIMAL(10, 8), -- Cost per input token
                                            output_cost_per_token DECIMAL(10, 8), -- Cost per output token
                                            is_active BOOLEAN DEFAULT TRUE,
                                            is_default BOOLEAN DEFAULT FALSE,
-- Inherited from BaseModel
                                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

                                            UNIQUE(provider, model_id, version)
);


-- Insert sample AI models
INSERT INTO ai_models (name, provider, model_id, version, description, context_window, max_tokens, input_cost_per_token, output_cost_per_token)
VALUES
    ('GPT-4', 'openai', 'gpt-4', '4.0', 'Most capable GPT-4 model', 8192, 4096, 0.00003, 0.00006),
    ('GPT-4 Turbo', 'openai', 'gpt-4-turbo', 'latest', 'Faster and more affordable GPT-4', 128000, 4096, 0.00001, 0.00003),
    ('Claude 3 Opus', 'anthropic', 'claude-3-opus', 'latest', 'Most powerful Claude model', 200000, 4096, 0.000015, 0.000075),
    ('Gemini Pro', 'google', 'gemini-pro', 'latest', 'Google''s advanced language model', 100000, 8192, 0.00002, 0.00005),
    ('Llama 3', 'meta', 'llama-3', '3.0', 'Meta''s large language model', 32768, 4096, 0.00001, 0.00004);

