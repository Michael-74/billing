CREATE TABLE schedules (
    id INT(11) NOT NULL AUTO_INCREMENT,
    dateformat VARCHAR(255) NOT NULL,
    is_once INT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY(`id`)
);