INSERT INTO patient (first_name, last_name, citizenship, date_of_birth, country,
                     county, city, address, marital_status, gender, cnp, diagnosis, observations, indications, user_id)
VALUES (
        'Patient12', 'Test12', 'Romanian', '03.08.1990', 'Romania',
        'Cluj', 'Cluj-Napoca', 'Taberei 20D', 'Casatorit', 'Masculin', '1921245213652', '', '', '',
        (SELECT id FROM "users" WHERE username = 'testpatient12'));