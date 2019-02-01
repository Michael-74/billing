CREATE TABLE emails (
    id INT(11) NOT NULL AUTO_INCREMENT,
    host VARCHAR(255) NOT NULL,
    port VARCHAR(10) NOT NULL,
    type_encryption VARCHAR(100) NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_status TINYINT(1) NOT NULL DEFAULT 0,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);