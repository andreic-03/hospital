-- Admin
INSERT INTO user_role (user_id, role_id)
VALUES ((SELECT id FROM "users" WHERE username = 'admin'),
        (SELECT id FROM "role" WHERE name = 'ADMIN'));

INSERT INTO user_role (user_id, role_id)
VALUES ((SELECT id FROM "users" WHERE username = 'testpatient12'),
        (SELECT id FROM "role" WHERE name = 'PATIENT'));

INSERT INTO user_role (user_id, role_id)
VALUES ((SELECT id FROM "users" WHERE username = 'testmedic12'),
        (SELECT id FROM "role" WHERE name = 'MEDIC'));

