CREATE TABLE user_tokens
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_on DATETIME NOT NULL,
    last_modified_on DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    token_type VARCHAR(255) NOT NULL,
    expiry DATETIME NOT NULL,
    is_valid BIT(1) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_tokens PRIMARY KEY (id),
    CONSTRAINT uc_233cdd15f977edef37cca6fa5 UNIQUE (token, token_type),
    CONSTRAINT FK_USER_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id)
);