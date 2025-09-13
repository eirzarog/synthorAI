
CREATE TABLE IF NOT EXISTS public.users (
                                            id BIGSERIAL,
                                            first_name TEXT,
                                            last_name TEXT,
                                            email TEXT,
                                            username TEXT,
                                            password TEXT NOT NULL, -- hashed password to be stored
                                            remember_me BOOLEAN DEFAULT FALSE,

                                            profile_image TEXT,
                                            display_name TEXT,
                                            date_of_birth DATE,

                                            status user_status DEFAULT 'ACTIVE',
                                            role user_role DEFAULT 'USER',

                                            version BIGINT DEFAULT 0,
    -- Security and account management fields
                                            email_verified BOOLEAN DEFAULT FALSE,

                                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

                                            PRIMARY KEY (id)
);



-- Create functions for automatic timestamp updates
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at := current_timestamp;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
-- Create triggers for automatic timestamp updates
CREATE TRIGGER trg_update_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();


-- Insert admin user
INSERT INTO public.users (username, email, password, first_name, last_name, role)
VALUES ('eirzarog', 'admin@example.com', '123', 'Eirini', 'Zarogiannou', 'ADMIN');

-- Insert users
INSERT INTO public.users (username, email, password, first_name, last_name)
VALUES ('john_doe', 'john_doe@example.com', '1234', 'John', 'Doe'),
       ('jane_doe', 'jane_doe@example.com', '5678', 'Jane', 'Doe');


INSERT INTO public.users (first_name, last_name, email, username, password)
VALUES
    ('Alice', 'Johnson', 'alice.johnson@example.com', 'alicej', 'hashed_pw_1'),
    ('Bob', 'Smith', 'bob.smith@example.com', 'bobsmith', 'hashed_pw_2'),
    ('Charlie', 'Lee', 'charlie.lee@example.com', 'charlielee', 'hashed_pw_3'),
    ('Dana', 'White', 'dana.white@example.com', 'danawhite', 'hashed_pw_4'),
    ('Eli', 'Brown', 'eli.brown@example.com', 'elibrown', 'hashed_pw_5');
