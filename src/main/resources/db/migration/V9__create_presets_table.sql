CREATE TABLE presets (
    id INT(11) NOT NULL AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    settings TEXT NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE INDEX url ON presets (url);