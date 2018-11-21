ALTER TABLE users ADD username VARCHAR(255);

INSERT INTO users(id, fio, password, email, username) VALUES(1, 'Admin', '$2a$04$HaWQTNUHMNzX2KAku/RL7O409BdXxu6KK9QFvywhxLZrJyZJYMTdm', 'michael74.ru@mail.ru', 'admin');