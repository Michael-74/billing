CREATE TABLE presets (
    id INT(11) NOT NULL AUTO_INCREMENT,
    url TEXT NOT NULL,
    name VARCHAR(255) NOT NULL,
    settings TEXT NOT NULL,
    PRIMARY KEY(`id`)
);