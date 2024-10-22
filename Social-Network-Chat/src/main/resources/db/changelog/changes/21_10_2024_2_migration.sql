create table if not exists permissions(
    id serial primary key,
    role varchar
);
INSERT INTO permissions(role) VALUES ('ROLE_USER');
INSERT INTO permissions(role) VALUES ('ROLE_ADMIN');