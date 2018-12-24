CREATE TABLE service_tvs (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_service INT(11) NOT NULL,
    id_tv INT(11) NOT NULL,
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);

CREATE TABLE service_tasks (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_service INT(11) NOT NULL,
    id_task INT(11) NOT NULL,
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);

CREATE TABLE service_clients (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_service INT(11) NOT NULL,
    id_client INT(11) NOT NULL,
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);