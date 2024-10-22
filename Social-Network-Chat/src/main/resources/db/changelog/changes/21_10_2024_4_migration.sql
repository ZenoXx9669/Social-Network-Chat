CREATE TABLE IF NOT EXISTS chat_users (
    id serial primary key,
    full_name varchar,
    nick_name varchar,
    status varchar
);
INSERT INTO chat_users (full_name,nick_name,status) VALUES ('Olzhas Jakenov', 'Olzhik','OFFLINE');
INSERT INTO chat_users (full_name,nick_name,status) VALUES ('Zhalgas Mereyov', 'Zhako','OFFLINE');
INSERT INTO chat_users (full_name,nick_name,status) VALUES ('Turar Esenov', 'Tuka','OFFLINE');