
INSERT INTO jhi_authority
    (name)
VALUES
    ('ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO jhi_authority
    (name)
VALUES
    ('ROLE_USER')
ON CONFLICT DO NOTHING;

SELECT pg_get_serial_sequence('jhi_user', 'id');

CREATE SEQUENCE jhi_user_id_seq
START 1;
ALTER TABLE jhi_user
  ALTER COLUMN id
SET
DEFAULT nextval
('jhi_user_id_seq');


INSERT INTO jhi_user
    (
    id,
    login,
    password_hash,
    email,
    activated,
    created_by,
    created_date
    )
VALUES
    (
        nextval('jhi_user_id_seq'),
        'admin',
        '$2a$10$1N6pQ0L0ePQ0Kq0lG3N7UOby8xZAWZ35nZz5d.6Q1kBe4PvV5g7M6',
        'admin@localhost',
        true,
        'system',
        now()
);

INSERT INTO jhi_user_authority
    (user_id, authority_name)
SELECT id, 'ROLE_ADMIN'
FROM jhi_user
WHERE login = 'admin';

INSERT INTO jhi_user_authority
    (user_id, authority_name)
SELECT id, 'ROLE_USER'
FROM jhi_user
WHERE login = 'admin';





