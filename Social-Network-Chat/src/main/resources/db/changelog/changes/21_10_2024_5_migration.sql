CREATE TABLE IF NOT EXISTS chatrooms (
    id serial primary key,
    chat varchar,
    recipient varchar,
    sender varchar
);
INSERT INTO chatrooms (chat,recipient,sender) VALUES ('Olzhik_Zhako', 'Zhako','Olzhik');
INSERT INTO chatrooms (chat,recipient,sender) VALUES ('Olzhik_Zhako', 'Olzhik','Zhako');
INSERT INTO chatrooms (chat,recipient,sender) VALUES ('Tuka_Olzhik', 'Olzhik','Tuka');
INSERT INTO chatrooms (chat,recipient,sender) VALUES ('Tuka_Olzhik', 'Tuka','Olzhik');
INSERT INTO chatrooms (chat,recipient,sender) VALUES ('Tuka_Zhako', 'Zhako','Tuka');
INSERT INTO chatrooms (chat,recipient,sender) VALUES ('Tuka_Zhako', 'Tuka','Zhako');