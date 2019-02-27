CREATE TABLE log_actions (
    id INT(11) NOT NULL AUTO_INCREMENT,
    type_action VARCHAR(255) NOT NULL,
    id_user INT(11) NOT NULL DEFAULT 0,
    is_success TINYINT(1) DEFAULT 0,
    request TEXT,
    response TEXT,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);
