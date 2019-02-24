ALTER TABLE clients
    ADD id_smotreshka_service VARCHAR(100) DEFAULT NULL AFTER note,
    ADD id_mikrotik_service VARCHAR(100) DEFAULT NULL AFTER note;
