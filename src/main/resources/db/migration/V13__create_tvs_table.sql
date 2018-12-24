CREATE TABLE tvs (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_lifestream INT(11) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);