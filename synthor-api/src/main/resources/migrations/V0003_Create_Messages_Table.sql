CREATE TABLE public.messages (
                          id BIGSERIAL PRIMARY KEY,
                          chat_id  BIGINT REFERENCES chats(id) ON DELETE CASCADE,
                          user_id  BIGINT REFERENCES users(id) ON DELETE CASCADE,
                          ai_model_id BIGINT REFERENCES ai_models(id) ON DELETE CASCADE,
                          content TEXT NOT NULL,
                          message_type message_type DEFAULT 'IMAGE' NOT NULL,
                          created_by created_by DEFAULT 'USER' NOT NULL,
-- Reaction counters for performance
                          like_count INTEGER DEFAULT 0,
                          dislike_count INTEGER DEFAULT 0,

                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP  -- Stored as UTC
                              CHECK (
                              (user_id IS NOT NULL AND ai_model_id IS NULL) OR
                              (user_id IS NULL AND ai_model_id IS NOT NULL)
                              )

);


INSERT INTO public.messages (
    user_id,
    chat_id,
    ai_model_id,
    content,
    message_type,
    created_by
) VALUES (
             2,               -- user_id (human sender)
             2,             -- chat_id
             NULL,            -- ai_model_id must be NULL if user_id is present
             'How to start my day?',
             'TEXT',
             'USER'
         );



INSERT INTO public.messages (
    user_id,
    chat_id,
    ai_model_id,
    content,
    message_type,
    created_by
) VALUES (
             NULL,            -- user_id must be NULL if ai_model_id is present
             2,             -- chat_id
             1,               -- ai_model_id
             'Good morning! Here are some tips to start your day...',
             'TEXT',
             'AI'


         );


