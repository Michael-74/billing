DROP table lifestream;

ALTER TABLE internets
    MODIFY speed INT(11) NOT NULL DEFAULT 0,
    CHANGE COLUMN status is_status TINYINT(1) NOT NULL DEFAULT 0;

ALTER TABLE clients
    MODIFY is_status TINYINT(1) NOT NULL DEFAULT 0,
    MODIFY is_promised_pay TINYINT(1) NOT NULL DEFAULT 0;

ALTER TABLE tasks
    MODIFY price INT(11) NOT NULL DEFAULT 0;

ALTER TABLE tvs
    CHANGE COLUMN status is_status TINYINT(1) NOT NULL DEFAULT 0;

ALTER TABLE rents
    CHANGE COLUMN status is_status TINYINT(1) NOT NULL DEFAULT 0;