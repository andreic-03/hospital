package org.hospital.services;

import jakarta.validation.Valid;
import org.hospital.api.model.user.*;

import java.util.List;

public interface UserService {
    List<UserResponseModel> findAll();

    UserResponseModel findById(final Long id);
    UserResponseModel findByUsername(final String id);

    UserResponseModel create(@Valid final UserRequestModel userDTO);

    void deleteById(final Long id);

    UserResponseModel update(final Long id, final UserUpdateRequestModel userModel);

    UserRegisterStepOneResponseModel registerUserStepOne(final UserRegisterStepOneRequestModel userRegisterStepOneRequestModel);

    void changeUserPassword(final Long id, final ChangePasswordRequestModel changePasswordRequestModel);
}
