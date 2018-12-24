DROP table schedules;

CREATE TABLE tasks (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price VARCHAR(255) NOT NULL DEFAULT 0,
    type_write_off ENUM('onetime', 'daily', 'monthly') NOT NULL,
    datetime VARCHAR(255) NOT NULL,
    day_before_start INT(3),
    month_before_start INT(3),
    day_before_end INT(3),
    month_before_end INT(3),
    is_write_off_rent INT(1) NOT NULL DEFAULT 0,
    is_installments INT(1) NOT NULL DEFAULT 0,
    price_installments VARCHAR(255) NOT NULL DEFAULT 0,
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`)
);