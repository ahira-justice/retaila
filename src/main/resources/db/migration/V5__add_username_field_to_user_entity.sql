ALTER TABLE users
    ADD username VARCHAR(255) NULL;

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);