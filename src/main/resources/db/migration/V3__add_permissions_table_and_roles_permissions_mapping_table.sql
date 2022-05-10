CREATE TABLE permissions
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_on DATETIME NOT NULL,
    last_modified_on DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL,
    version INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_system BIT(1) NOT NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id),
    CONSTRAINT uc_permissions_name UNIQUE (name)
);

CREATE TABLE roles_permissions
(
    permission_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_roles_permissions PRIMARY KEY (permission_id, role_id),
    CONSTRAINT fk_roles_permissions_on_permission FOREIGN KEY (permission_id) REFERENCES permissions (id),
    CONSTRAINT fk_roles_permissions_on_role FOREIGN KEY (role_id) REFERENCES roles (id)
);