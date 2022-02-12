CREATE TABLE user_devices
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_on    datetime NULL,
    updated_on    datetime NULL,
    is_deleted    BIT(1) NULL,
    device_id     VARCHAR(255) NULL,
    is_authorized BIT(1)       NOT NULL,
    authorized_on datetime NULL,
    device_type   VARCHAR(255) NOT NULL,
    user_id       BIGINT       NOT NULL,
    CONSTRAINT pk_user_devices PRIMARY KEY (id)
);

ALTER TABLE user_devices
    ADD CONSTRAINT uc_95ff8925bfe810bc2a54eb905 UNIQUE (device_id, device_type);

ALTER TABLE user_devices
    ADD CONSTRAINT FK_USER_DEVICES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);