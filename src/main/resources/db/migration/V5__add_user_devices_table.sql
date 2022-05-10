CREATE TABLE user_devices
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_on DATETIME NOT NULL,
    last_modified_on DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    device_id VARCHAR(255) NOT NULL,
    is_authorized BIT(1) NOT NULL,
    authorized_on DATETIME NULL,
    device_type VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_devices PRIMARY KEY (id),
    CONSTRAINT uc_95ff8925bfe810bc2a54eb905 UNIQUE (device_id, device_type),
    CONSTRAINT FK_USER_DEVICES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id)
);