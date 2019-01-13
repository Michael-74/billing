ALTER TABLE clients
    MODIFY created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE internets
    MODIFY created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE internets_tasks
    MODIFY created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE packages
    MODIFY created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE presets
    ADD created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE rents
    MODIFY created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE tasks
    MODIFY created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE tvs
    MODIFY created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE tvs_tasks
    MODIFY created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE users
    ADD created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;