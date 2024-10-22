CREATE TABLE IF NOT EXISTS users_permissions (
    user_id BIGINT NOT NULL,
    permissions_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, permissions_id)
);



INSERT INTO users_permissions (user_id, permissions_id) VALUES (1, 1);
INSERT INTO users_permissions (user_id, permissions_id) VALUES (2, 1);
INSERT INTO users_permissions (user_id, permissions_id) VALUES (3, 1);