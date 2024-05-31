package org.hospital.mappers;

import org.hospital.api.model.user.UserResponseModel;
import org.hospital.persistence.entity.*;
import org.hospital.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private static UserMapper userMapper = new UserMapperImpl();

    private static TestUtils testUtils;

    @BeforeAll()
    public static void setUp() {
        testUtils = new TestUtils();
    }

    @Test
    void testUserMapper() {
        UserEntity user = testUtils.createUserEntity();

        UserResponseModel userResponseModel = userMapper.toUserModel(user);

        assertAll(() -> {
            assertEquals(user.getUsername(), userResponseModel.getUsername());
            assertEquals(user.getEmail(), userResponseModel.getEmail());
            assertEquals(user.getPhoneNumber(), userResponseModel.getPhoneNumber());
        });
    }

    @Test
    void testUserMapperWithPatient() {
        UserEntity user = testUtils.createUserPatient();

        UserResponseModel userResponseModel = userMapper.toUserModel(user);

        assertAll(() -> {
            assertEquals(user.getUsername(), userResponseModel.getUsername());
            assertEquals(user.getEmail(), userResponseModel.getEmail());
            assertEquals(user.getPhoneNumber(), userResponseModel.getPhoneNumber());
            assertEquals(user.getStatus().toString(), userResponseModel.getStatus());
            assertEquals(user.getPatient().getPatientId(), userResponseModel.getPatientId());
        });
    }


    @Test
    void testUserMapperWithMedic() {
        UserEntity user = testUtils.createUserMedic();

        UserResponseModel userResponseModel = userMapper.toUserModel(user);

        assertAll(() -> {
            assertEquals(user.getUsername(), userResponseModel.getUsername());
            assertEquals(user.getEmail(), userResponseModel.getEmail());
            assertEquals(user.getPhoneNumber(), userResponseModel.getPhoneNumber());
            assertEquals(user.getStatus().toString(), userResponseModel.getStatus());
            assertEquals(user.getMedic().getMedicId(), userResponseModel.getMedicId());
        });
    }

}
