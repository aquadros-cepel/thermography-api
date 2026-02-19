
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
        '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC',
        'admin@localhost',
        true,
        'system',
        now()
);

-- Insert UserInfo for admin user
INSERT INTO user_info
    (
    id,
    position,
    phone_number,
    user_id,
    company_id
    )
SELECT
    gen_random_uuid(),
    'System Administrator',
    '+55 11 99999-9999',
    u.id,
    NULL
FROM jhi_user u
WHERE u.login = 'admin';

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






