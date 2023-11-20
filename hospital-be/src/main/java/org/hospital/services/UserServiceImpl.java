package org.hospital.services;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.UserRequestModel;
import org.hospital.api.model.UserResponseModel;
import org.hospital.api.model.UserUpdateRequestModel;
import org.hospital.errorhandling.Errors;
import org.hospital.errorhandling.UncheckedException;
import org.hospital.mappers.UserMapper;
import org.hospital.persistence.entity.RoleEntity;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.persistence.entity.UserStatus;
import org.hospital.persistence.repository.RoleRepository;
import org.hospital.persistence.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseModel> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseModel findById(final Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserModel)
                .orElseThrow(() -> new UncheckedException(Errors.Functional.USER_NOT_FOUND));
    }

    @Override
    public UserResponseModel findByUsername(final String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserModel)
                .orElseThrow(() -> new UncheckedException(Errors.Functional.USER_NOT_FOUND));
    }

    @Override
    public UserResponseModel create(@Valid final UserRequestModel userDTO) {
        if(userDTO.getId() != null) {
            System.out.println("Id must be null");
            throw new RuntimeException("Id must be null");
        }
        UserEntity userEntity = userMapper.toUserEntity(userDTO);

        Set<RoleEntity> roles = mapRoles(userDTO.getRoles());
        userEntity.setRoles(roles);

        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setStatus(UserStatus.ACTIVE);

        UserEntity user = userRepository.saveAndFlush(userEntity);

        return userMapper.toUserModel(user);
    }

    @Override
    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseModel update(final Long id, final UserUpdateRequestModel userModel) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UncheckedException(Errors.Functional.USER_NOT_FOUND));

        userMapper.updateUserEntity(existingUser, userModel);

        UserEntity savedUser = userRepository.save(existingUser);

        return userMapper.toUserModel(savedUser);
    }

    private Set<RoleEntity> mapRoles(Set<String> roles) {
        return roles
                .stream()
                .map(roleRepository::findByName)
                .map(roleEntity -> roleEntity.orElseThrow(() -> new UncheckedException(Errors.Functional.ROLE_NOT_FOUND)))
                .collect(Collectors.toSet());
    }

}
