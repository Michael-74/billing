ALTER TABLE log_smotreshkas
  ADD method VARCHAR(255) DEFAULT NULL AFTER type_action,
  ADD id_client int(11) DEFAULT 0 AFTER id_user;
