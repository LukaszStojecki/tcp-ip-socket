CREATE TABLE users (
id BIGINT PRIMARY KEY NOT NULL,
nick VARCHAR(15) NOT NULL,
login VARCHAR(15) NOT NULL,
password VARCHAR(15) NOT NULL,
insert_time timestamp NOT NULL DEFAULT now()
);

CREATE TABLE vehicles (
id BIGINT PRIMARY KEY NOT NULL,
user_login varchar(15) NOT NULL,
brand VARCHAR(15) NOT NULL,
model VARCHAR(15) NOT NULL,
insert_time timestamp NOT NULL,
KEY vehicles_ibfk_1 (user_login)
);

CREATE TABLE insurance_offers (
id BIGINT PRIMARY KEY NOT NULL,
vehicle_id BIGINT(15) NOT NULL,
insurer VARCHAR(15) NOT NULL,
price DECIMAL(10,2) NOT NULL,
insert_time timestamp NOT NULL,
CONSTRAINT FOREIGN KEY(vehicle_id) REFERENCES vehicles (id)
);

INSERT INTO users (id, nick, login, password) VALUES (1,'user1', 'user1', 'user123');
INSERT INTO users (id, nick, login, password) VALUES (2,'user2', 'user2', 'user123');

INSERT INTO vehicles (id, user_login, brand, model, insert_time) VALUES (1,'user1', 'Toyota ', 'Corolla','2022-01-01 00:00:01');
INSERT INTO vehicles (id, user_login, brand, model, insert_time) VALUES (2,'user2', 'Toyota ', 'Aygo','2022-02-03 00:00:01');

INSERT INTO vehicles (id,user_login, brand, model, insert_time) VALUES (3,'user2', 'Volkswagen ', 'Golf','2022-01-01 00:00:01');
INSERT INTO vehicles (id,user_login, brand, model, insert_time) VALUES (4,'user2', 'Volkswagen ', 'Passat','2022-01-01 00:00:01');

INSERT INTO insurance_offers (id,vehicle_id, insurer, price,insert_time) VALUES (1, 1, 'UNIQA', '1000','2022-01-01 00:00:01');
INSERT INTO insurance_offers (id,vehicle_id, insurer, price,insert_time) VALUES (2, 2, 'WARTA', '2000','2022-01-01 00:00:01');
INSERT INTO insurance_offers (id,vehicle_id, insurer, price,insert_time) VALUES (3, 3, 'GENERALI', '3000','2022-01-01 00:00:01');