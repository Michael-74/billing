ALTER TABLE tasks CHANGE day_before_start day_start INT(2);
ALTER TABLE tasks CHANGE month_before_start month_start INT(2);
ALTER TABLE tasks CHANGE day_before_end day_end INT(2);
ALTER TABLE tasks CHANGE month_before_end month_end INT(2);
ALTER TABLE tasks MODIFY is_write_off_rent TINYINT(1) DEFAULT NULL;
ALTER TABLE tasks MODIFY is_installments TINYINT(1) DEFAULT NULL;