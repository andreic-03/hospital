package org.patients.services;

import org.patients.models.Credential;
import org.patients.models.User;
import org.patients.models.dto.UserDTO;
import org.patients.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialService credentialService;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertFromEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(final Long id) {
        return userRepository.findById(id)
                .map(this::convertFromEntityToDTO)
                .orElseThrow(() -> {
            System.out.println("NOT FOUND...");
            return new RuntimeException("User not found");
        });
    }

    @Override
    public UserDTO create(@Valid final UserDTO userDTO) {
        if(userDTO.getId() != null) {
            System.out.println("Id must be null");
            throw new RuntimeException("Id must be null");
        }
        User user = userRepository.saveAndFlush(convertFromDTOToEntity(userDTO));

        Credential credential = new Credential();
        credential.setUserId(user.getId());
        credential.setUsername(userDTO.getCredentials().getUsername());
        credential.setPassword(userDTO.getCredentials().getPassword());

        credentialService.registerNewUserAccount(credential);

        return convertFromEntityToDTO(user);
    }

    @Override
    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> {
                    System.out.println("NOT FOUND...");
                    return new RuntimeException("User not found");
                });

        BeanUtils.copyProperties(userDTO, existingUser);
        return convertFromEntityToDTO(userRepository.saveAndFlush(existingUser));
    }

    private UserDTO convertFromEntityToDTO(final User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    private User convertFromDTOToEntity(final UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
