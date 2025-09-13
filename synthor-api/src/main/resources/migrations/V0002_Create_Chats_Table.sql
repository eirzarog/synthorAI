
CREATE TABLE IF NOT EXISTS public.chats (
                                            id BIGSERIAL,
                                            user_id BIGINT NOT NULL,
                                            title TEXT,
                                            status chat_status DEFAULT 'ACTIVE',
                                            is_archived BOOLEAN DEFAULT FALSE,
    -- Inherited from BaseEntity
                                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    -- Inherited from SoftDeletableModel
                                            deleted BOOLEAN DEFAULT FALSE,

                                            PRIMARY KEY (id),
                                            FOREIGN KEY (user_id) REFERENCES public.users (id)
);

-- Index on updated_at for sorting or recent activity
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


