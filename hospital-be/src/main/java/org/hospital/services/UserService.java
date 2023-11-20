package org.hospital.services;

import jakarta.validation.Valid;
import org.hospital.api.model.UserRequestModel;
import org.hospital.api.model.UserResponseModel;
import org.hospital.api.model.UserUpdateRequestModel;

import java.util.List;

public interface UserService {
    List<UserResponseModel> findAll();

    UserResponseModel findById(final Long id);
    UserResponseModel findByUsername(final String id);

    UserResponseModel create(@Valid final UserRequestModel userDTO);

    void deleteById(final Long id);

    UserResponseModel update(final Long id, final UserUpdateRequestModel userModel);
}
