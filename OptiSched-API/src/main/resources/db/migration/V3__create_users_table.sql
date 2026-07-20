CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    institution_id BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),

    CONSTRAINT fk_user_institution
        FOREIGN KEY (institution_id)
        REFERENCES institution(id)
        ON DELETE CASCADE
);

INSERT INTO users (name, email, password, role, institution_id)
VALUES (
    'Super Admin OptiSched',
    '${superadmin.email}',
    '${superadmin.password}',
    'SUPER_ADMIN',
    NULL
);

ALTER TABLE professor DROP COLUMN email;

ALTER TABLE professor
ADD COLUMN user_id BIGINT UNIQUE,
ADD CONSTRAINT fk_professor_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE;

CREATE INDEX idx_professor_user_id ON professor(user_id);

CREATE INDEX idx_users_email ON users(email);