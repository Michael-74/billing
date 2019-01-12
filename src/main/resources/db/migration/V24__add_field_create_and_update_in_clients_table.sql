ALTER TABLE clients
    ADD id_internet INT(11) DEFAULT NULL,
    ADD discount INT(11) DEFAULT 0,
    ADD type_discount VARCHAR(100) DEFAULT NULL,
    ADD loyalty INT(11) DEFAULT NULL,
    ADD is_status TINYINT(1) DEFAULT 0,
    ADD is_promised_pay TINYINT(1) DEFAULT 0,
    ADD note TEXT DEFAULT NULL,
    ADD created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    ADD updated_at timestamp NULL DEFAULT CURRENT_TIMESTAMP;