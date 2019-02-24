ALTER TABLE tvs
    ADD id_smotreshka INT(11) DEFAULT NULL AFTER is_status,
    ADD description TEXT DEFAULT NULL AFTER name ;
