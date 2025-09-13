CREATE TABLE public.messages (
                          id BIGSERIAL,
                          chat_id  BIGINT NOT NULL,
                          user_id  BIGINT,
                          model_id BIGINT,
                          content TEXT,
                          message_sender message_sender DEFAULT 'USER' NOT NULL,

                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

                          PRIMARY KEY (id),
                          FOREIGN KEY (chat_id) REFERENCES public.chats (id),
                          FOREIGN KEY (user_id) REFERENCES public.users (id),
                          FOREIGN KEY (model_id) REFERENCES public.models (id)

);


INSERT INTO public.messages (user_id, chat_id, model_id, content, message_sender)
VALUES (2, 2, NULL,'How to start my day?', 'USER'),
       (NULL,2, 1, 'Good morning! Here are some tips to start your day...', 'MODEL');

