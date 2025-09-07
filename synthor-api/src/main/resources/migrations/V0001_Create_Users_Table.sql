
CREATE TABLE IF NOT EXISTS public.users (
                                            id BIGSERIAL PRIMARY KEY,
                                            first_name TEXT,
                                            last_name TEXT,
                                            email TEXT,
                                            username TEXT,
                                            password TEXT NOT NULL, -- hashed password to be stored

                                            profile_image TEXT,
                                            display_name TEXT,
                                            date_of_birth DATE,

                                            status user_status DEFAULT 'INACTIVE',
                                            role user_role DEFAULT 'USER',

                                            version BIGINT DEFAULT 0,
    -- Security and account management fields
                                            email_verified BOOLEAN DEFAULT FALSE,
                                            email_verification_token TEXT,
                                            email_verification_expires_at TIMESTAMP,
                                            password_reset_token TEXT,
                                            password_reset_expires_at TIMESTAMP,
                                            account_locked_until TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                            last_login TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                            failed_login_attempts BIGINT DEFAULT 0,
                                            locked_until TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    -- Inherited from BaseModel
                                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
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


INSERT INTO public.users (first_name, last_name, email, username, password, display_name )
VALUES
    ('Alice', 'Johnson', 'alice.johnson@example.com', 'alicej', 'hashed_pw_1', null),
    ('Bob', 'Smith', 'bob.smith@example.com', 'bobsmith', 'hashed_pw_2', null),
    ('Charlie', 'Lee', 'charlie.lee@example.com', 'charlielee', 'hashed_pw_3',null),
    ('Dana', 'White', 'dana.white@example.com', 'danawhite', 'hashed_pw_4',null),
    ('Eli', 'Brown', 'eli.brown@example.com', 'elibrown', 'hashed_pw_5',null);
