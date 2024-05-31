INSERT INTO medic (first_name, last_name, gender, cnp, specialization, user_id)
VALUES (
        'Medic12', 'TestM12', 'Masculin', '1921245213652', 'SURGEON',
        (SELECT id FROM "users" WHERE username = 'testmedic12'));