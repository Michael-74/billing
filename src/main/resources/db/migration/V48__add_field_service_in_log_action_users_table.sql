ALTER TABLE log_action_users
  ADD service VARCHAR(255) DEFAULT NULL AFTER type_action;
