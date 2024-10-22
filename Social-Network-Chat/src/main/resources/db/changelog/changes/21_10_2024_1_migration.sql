create table if not exists users (
    id serial primary key ,
    nick_name varchar,
    full_name varchar,
    email varchar,
    password varchar
);

INSERT INTO users (nick_name,full_name, email, password)
VALUES ('Olzhik','Olzhas Jakenov', 'olzhas@gmail.com', '$2a$12$C7rHzcA.UhUowurvTlNEkeuQaILs5/5yaHHpFFRJOJmls/ap6Jy1W');
-- password:123
INSERT INTO users (nick_name,full_name, email, password)
VALUES ('Zhako','Zhalgas Mereyov', 'zhalgas@gmail.com', '$2a$12$rN3CoptV7x69qL8bSAEMtODNp9ZSGCNh1uyY/2qLuPhgIhVuPRseK');
-- password:456
INSERT INTO users (nick_name,full_name, email, password)
VALUES ('Tuka','Turar Esenov', 'esenov@gmail.com', '$2a$12$UP4PzOkGKdgGrcRYJZq5teuqlZ6WHn82y3AKN94vsqTxrddHkSbfe');
-- password:789




