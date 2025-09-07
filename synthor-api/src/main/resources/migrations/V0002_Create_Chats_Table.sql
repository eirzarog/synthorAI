
CREATE TABLE IF NOT EXISTS public.chats (
                                            id BIGSERIAL PRIMARY KEY,
                                            title TEXT DEFAULT 'Untitled',
                                            description TEXT,
                                            user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                            ai_model_id BIGINT  REFERENCES ai_models(id),
                                            system_prompt TEXT,
                                            status chat_status DEFAULT 'ACTIVE',
                                            is_archived BOOLEAN DEFAULT FALSE,
                                            message_count INTEGER DEFAULT 0,
                                            total_tokens INTEGER DEFAULT 0,
                                            last_message_at TIMESTAMP WITH TIME ZONE,
    -- Inherited from BaseModel
                                            created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- Inherited from SoftDeletableModel
                                            deleted BOOLEAN DEFAULT FALSE NOT NULL
);

-- Optional: Index on updated_at for sorting or recent activity
CREATE INDEX idx_chats_updated_at ON public.chats(updated_at DESC);






INSERT INTO public.chats (user_id, title)
VALUES ((SELECT id FROM public.users WHERE email = 'john_doe@example.com'), 'Daily Standup'),
       ((SELECT id FROM public.users WHERE email = 'john_doe@example.com'), 'Project Planning'),
       ((SELECT id FROM public.users WHERE email = 'john_doe@example.com'), 'Budget Review'),
       ((SELECT id FROM public.users WHERE email = 'john_doe@example.com'), 'Retrospective'),
       ((SELECT id FROM public.users WHERE email = 'john_doe@example.com'), 'Design Discussion'),
       ((SELECT id FROM public.users WHERE email = 'jane_doe@example.com'), 'Daily Standup'),
       ((SELECT id FROM public.users WHERE email = 'jane_doe@example.com'), 'Admin Team Meeting'),
       ((SELECT id FROM public.users WHERE email = 'jane_doe@example.com'), 'User Feedback'),
       ((SELECT id FROM public.users WHERE email = 'jane_doe@example.com'), 'Roadmap Session'),
       ((SELECT id FROM public.users WHERE email = 'jane_doe@example.com'), 'Marketing Sync');


