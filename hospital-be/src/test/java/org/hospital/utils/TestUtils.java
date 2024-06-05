package org.hospital.utils;

import org.hospital.api.model.user.UserRequestModel;
import org.hospital.persistence.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class TestUtils {

    public UserEntity createUserEntity() {
        RoleEntity role = new RoleEntity();
        role.setName("ADMIN");
        TokenEntity token = new TokenEntity();
        token.setToken("token");

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setUsername("login");
        user.setPassword("pass");
        user.setEmail("email@email.com");
        user.setPhoneNumber("0745123123");
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(Collections.singleton(role));
        user.setJwtTokens(Collections.singleton(token));

        return user;
    }

    public UserEntity createUserPatient() {
        RoleEntity role = new RoleEntity();
        role.setName("PATIENT");
        TokenEntity token = new TokenEntity();
        token.setToken("token");

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setUsername("login");
        user.setPassword("pass");
        user.setEmail("email@email.com");
        user.setPhoneNumber("0745123123");
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(Collections.singleton(role));
        user.setJwtTokens(Collections.singleton(token));
        user.setPatient(createPatientWithUser(user));

        return user;
    }

    public UserEntity createUserMedic() {
        RoleEntity role = new RoleEntity();
        role.setName("MEDIC");
        TokenEntity token = new TokenEntity();
        token.setToken("token");

        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setUsername("login");
        user.setPassword("pass");
        user.setEmail("email@email.com");
        user.setPhoneNumber("0745123123");
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(Collections.singleton(role));
        user.setJwtTokens(Collections.singleton(token));
        user.setMedic(createMedicWithUser(user));

        return user;
    }

    public PatientEntity createPatientWithUser(UserEntity user) {
        PatientEntity patient = new PatientEntity();

        patient.setPatientId(1L);
        patient.setFirstName("first");
        patient.setLastName("last");
        patient.setCnp("1234567890123");
        patient.setGender("MALE");
        patient.setCitizenship("RO");
        patient.setDateOfBirth("03.03.1999");
        patient.setAddress("Str ASD, Nr 1");
        patient.setCity("Cluj-Napoca");
        patient.setCountry("Romania");
        patient.setCounty("Cluj");
        patient.setMaritalStatus("Married");
        patient.setUser(user);

        return patient;
    }

    public MedicEntity createMedicWithUser(UserEntity user) {
        MedicEntity medic = new MedicEntity();

        medic.setMedicId(1L);
        medic.setFirstName("first");
        medic.setLastName("last");
        medic.setUser(user);

        return medic;
    }

    public UserRequestModel createUserRequestModel() {
        UserRequestModel requestModel = new UserRequestModel();

        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");

        requestModel.setUsername("login");
        requestModel.setPassword("pass");
        requestModel.setEmail("email@email.com");
        requestModel.setPhoneNumber("0745123123");
        requestModel.setRoles(roles);

        return requestModel;
    }
}
