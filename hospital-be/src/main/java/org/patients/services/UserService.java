package org.patients.services;


import org.patients.models.dto.UserDTO;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<UserDTO> findAll();

    UserDTO findById(final Long id);

    UserDTO create(@Valid final UserDTO userDTO);

    void deleteById(final Long id);

    UserDTO update(final UserDTO userDTO);
}
