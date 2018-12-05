CREATE TABLE clients (
    id INT(11) NOT NULL AUTO_INCREMENT,
    fio VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0,
    contract VARCHAR(255) NOT NULL,
    ip VARCHAR(255) NOT NULL,
    address VARCHAR(255) NULL,
    phone VARCHAR(255) NULL,
    email VARCHAR(255) NULL,
    PRIMARY KEY(`id`)
);