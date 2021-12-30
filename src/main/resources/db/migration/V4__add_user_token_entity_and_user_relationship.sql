CREATE TABLE user_tokens
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_on datetime NULL,
    updated_on datetime NULL,
    is_deleted BIT(1) NULL,
    token      VARCHAR(255) NOT NULL,
    token_type VARCHAR(255) NOT NULL,
    expiry     datetime     NOT NULL,
    is_valid   BIT(1)       NOT NULL,
    user_id    BIGINT       NOT NULL,
    CONSTRAINT pk_user_tokens PRIMARY KEY (id)
);

ALTER TABLE user_tokens
    ADD CONSTRAINT uc_233cdd15f977edef37cca6fa5 UNIQUE (token, token_type);

ALTER TABLE user_tokens
    ADD CONSTRAINT FK_USER_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);