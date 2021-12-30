CREATE TABLE users
(
    id                        BIGINT       NOT NULL AUTO_INCREMENT,
    created_on                datetime NULL,
    updated_on                datetime NULL,
    is_deleted                BIT(1) NULL,
    email                     VARCHAR(150) NOT NULL,
    is_email_verified BIT(1)       NOT NULL,
    password        VARCHAR(255) NOT NULL,
    first_name                VARCHAR(50)  NOT NULL,
    last_name                 VARCHAR(50)  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);