CREATE TABLE clients_tvs (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_client INT(11) NOT NULL,
    id_tv INT(11) NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);

CREATE TABLE clients_rents (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_client INT(11) NOT NULL,
    id_rent INT(11) NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);