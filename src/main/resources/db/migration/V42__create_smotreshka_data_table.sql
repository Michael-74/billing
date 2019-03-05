CREATE TABLE smotreshka_datas (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_smotreshka VARCHAR(255) NOT NULL,
    id_id_smotreshka_setting INT(11) NOT NULL,
    id_client INT(11) NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);
