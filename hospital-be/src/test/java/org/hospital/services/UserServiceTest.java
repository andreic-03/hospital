package org.hospital.services;

import org.hospital.api.model.user.*;
import org.hospital.configuration.exception.model.HospitalBadRequestException;
import org.hospital.configuration.exception.model.HospitalException;
import org.hospital.configuration.exception.model.HospitalNotFoundException;
import org.hospital.mappers.RegisterAccountTokenMapper;
import org.hospital.mappers.RegisterAccountTokenMapperImpl;
import org.hospital.mappers.UserMapper;
import org.hospital.mappers.UserMapperImpl;
import org.hospital.persistence.entity.RegisterAccountTokenEntity;
import org.hospital.persistence.entity.RoleEntity;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.persistence.entity.UserStatus;
import org.hospital.persistence.repository.RegisterAccountTokenRepository;
import org.hospital.persistence.repository.RoleRepository;
import org.hospital.persistence.repository.UserRepository;
import org.hospital.services.crypto.CryptoHashService;
import org.hospital.services.random.SecureRandomGeneratorService;
import org.hospital.services.security.TokenService;
import org.hospital.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hospital.configuration.exception.model.ErrorType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RegisterAccountTokenRepository registerAccountTokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecureRandomGeneratorService secureRandomGeneratorService;
    @Mock
    private CryptoHashService cryptoHashService;
    @Mock
    private TokenService tokenService;
    @Spy
    private UserMapper userMapper = new UserMapperImpl();
    @Spy
    private RegisterAccountTokenMapper registerAccountTokenMapper = new RegisterAccountTokenMapperImpl();

    private static TestUtils testUtils;

    @BeforeAll()
    public static void setUp() {
        testUtils = new TestUtils();
    }

    @BeforeEach
    public void injectMapper() {
        ReflectionTestUtils.setField(userService, "userMapper", userMapper);
        ReflectionTestUtils.setField(userService, "registerAccountTokenMapper", registerAccountTokenMapper);
        ReflectionTestUtils.setField(userService, "expireIn", 5);
    }

    @Test
    void findUseById_success() {
        Optional<UserEntity> user = Optional.of(testUtils.createUserEntity());

        when(userRepository.findById(1L)).thenReturn(user);

        UserResponseModel userResponseModel = userService.findById(1L);

        verify(userRepository).findById(1L);

        assertAll(() -> {
            assertEquals("login", userResponseModel.getUsername());
            assertEquals("email@email.com", userResponseModel.getEmail());
            assertEquals("0745123123", userResponseModel.getPhoneNumber());
            assertEquals("ACTIVE", userResponseModel.getStatus());
        });
    }

    @Test
    void findUserById_shouldThrowUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        HospitalNotFoundException exception = assertThrows(HospitalNotFoundException.class,
                () -> userService.findById(1L));

        assertAll(() -> {
            assertEquals(USER_NOT_FOUND.getDefaultErrorMessage(), exception.getMessage());
            assertEquals(USER_NOT_FOUND.getDefaultErrorMessage(), exception.getErrorType().getDefaultErrorMessage());
            assertEquals(USER_NOT_FOUND.getErrorCode(), exception.getErrorType().getErrorCode());
        });

        verify(userRepository).findById(1L);
    }

    @Test
    void findUseByUsername_success() {
        Optional<UserEntity> user = Optional.of(testUtils.createUserEntity());

        when(userRepository.findByUsername("login")).thenReturn(user);

        UserResponseModel userResponseModel = userService.findByUsername("login");

        verify(userRepository).findByUsername("login");

        assertAll(() -> {
            assertEquals("login", userResponseModel.getUsername());
            assertEquals("email@email.com", userResponseModel.getEmail());
            assertEquals("0745123123", userResponseModel.getPhoneNumber());
            assertEquals("ACTIVE", userResponseModel.getStatus());
        });
    }

    @Test
    void findUserByUsername_shouldThrowUserNotFound() {
        when(userRepository.findByUsername("login")).thenReturn(Optional.empty());

        HospitalNotFoundException exception = assertThrows(HospitalNotFoundException.class,
                () -> userService.findByUsername("login"));

        verify(userRepository).findByUsername("login");

        assertAll(() -> {
            assertEquals(USER_NOT_FOUND.getDefaultErrorMessage(), exception.getMessage());
            assertEquals(USER_NOT_FOUND.getDefaultErrorMessage(), exception.getErrorType().getDefaultErrorMessage());
            assertEquals(USER_NOT_FOUND.getErrorCode(), exception.getErrorType().getErrorCode());
        });

    }

    @Test
    void createUser_success() {
        UserRequestModel requestModel = testUtils.createUserRequestModel();
        Set<String> roles = requestModel.getRoles();

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ADMIN");
        Optional<RoleEntity> optionalRole = Optional.of(roleEntity);

        when(roleRepository.findByName("ADMIN")).thenReturn(optionalRole);
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");

        UserEntity savedUserEntity = new UserEntity();
        savedUserEntity.setUsername("login");
        savedUserEntity.setEmail("email@email.com");
        savedUserEntity.setPhoneNumber("0745123123");
        savedUserEntity.setStatus(UserStatus.ACTIVE);
        savedUserEntity.setRoles(Collections.singleton(roleEntity));

        when(userRepository.saveAndFlush(any(UserEntity.class))).thenReturn(savedUserEntity);

        UserResponseModel responseModel = userService.create(requestModel);

        verify(roleRepository).findByName("ADMIN");
        verify(passwordEncoder).encode("pass");
        verify(userRepository).saveAndFlush(any(UserEntity.class));

        assertAll(() -> {
            assertEquals("login", responseModel.getUsername());
            assertEquals("ACTIVE", responseModel.getStatus());
            assertEquals("email@email.com", responseModel.getEmail());
            assertEquals("0745123123", responseModel.getPhoneNumber());
            assertEquals(roles, responseModel.getRoles());
        });
    }

    @Test
    void registerUserStepOne_success() {
        UserServiceImpl spyUserService = Mockito.spy(userService);
        doNothing().when(spyUserService).sendRegistrationEmail(any(UserRegisterStepOneRequestModel.class), anyString());

        UserRegisterStepOneRequestModel requestModel = new UserRegisterStepOneRequestModel();
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");

        requestModel.setUsername("login");
        requestModel.setPassword("pass");
        requestModel.setEmail("email@email.com");
        requestModel.setPhoneNumber("0745123123");
        requestModel.setRoles(roles);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ADMIN");
        Optional<RoleEntity> optionalRole = Optional.of(roleEntity);

        when(roleRepository.findByName("ADMIN")).thenReturn(optionalRole);
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
        when(secureRandomGeneratorService.generateRandomString()).thenReturn("randomStringGenerated");
        when(cryptoHashService.hashContent("randomStringGenerated")).thenReturn("randomStringGeneratedEncoded");

        UserEntity savedUserEntity = new UserEntity();
        savedUserEntity.setUsername("login");
        savedUserEntity.setEmail("email@email.com");
        savedUserEntity.setPhoneNumber("0745123123");
        savedUserEntity.setStatus(UserStatus.ACTIVE);
        savedUserEntity.setRoles(Collections.singleton(roleEntity));

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);
        registerAccountTokenRepository.save(any(RegisterAccountTokenEntity.class));

        UserRegisterStepOneResponseModel responseModel = spyUserService.registerUserStepOne(requestModel);

        verify(roleRepository).findByName("ADMIN");
        verify(passwordEncoder).encode("pass");
        verify(userRepository).save(any(UserEntity.class));
        verify(secureRandomGeneratorService).generateRandomString();
        verify(cryptoHashService).hashContent("randomStringGenerated");
        verify(spyUserService).sendRegistrationEmail(any(UserRegisterStepOneRequestModel.class), anyString());

        assertAll(() -> {
            assertEquals("email@email.com", responseModel.getEmail());
            assertEquals("login", responseModel.getUsername());
        });
    }

    @Test
    void registerUserStepOne_shouldThrowEmailAlreadyExists() {
        UserRegisterStepOneRequestModel requestModel = new UserRegisterStepOneRequestModel();
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");

        requestModel.setUsername("login");
        requestModel.setPassword("pass");
        requestModel.setEmail("email@email.com");
        requestModel.setPhoneNumber("0745123123");
        requestModel.setRoles(roles);

        when(userRepository.findByEmail("email@email.com")).thenReturn(testUtils.createUserEntity());

        HospitalException exception = assertThrows(HospitalException.class, () -> {
            userService.registerUserStepOne(requestModel);
        });

        assertAll(() -> {
            assertEquals(EMAIL_ALREADY_EXISTS.getDefaultErrorMessage(), exception.getMessage());
            assertEquals(EMAIL_ALREADY_EXISTS.getDefaultErrorMessage(), exception.getErrorType().getDefaultErrorMessage());
            assertEquals(EMAIL_ALREADY_EXISTS.getErrorCode(), exception.getErrorType().getErrorCode());
        });
    }

    @Test
    void deleteUser_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(testUtils.createUserEntity()));

        userService.deleteById(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        HospitalNotFoundException exception = assertThrows(HospitalNotFoundException.class,
                () -> userService.deleteById(1L));

        verify(userRepository).findById(1L);
        verify(userRepository, never()).deleteById(1L);

        assertAll(() -> {
            assertEquals(USER_NOT_FOUND.getDefaultErrorMessage(), exception.getMessage());
            assertEquals(USER_NOT_FOUND.getDefaultErrorMessage(), exception.getErrorType().getDefaultErrorMessage());
            assertEquals(USER_NOT_FOUND.getErrorCode(), exception.getErrorType().getErrorCode());
        });
    }

    @Test
    void updateUser_success() {
        UserEntity existingUser = testUtils.createUserEntity();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(existingUser));

        UserUpdateRequestModel userUpdateRequestModel = new UserUpdateRequestModel();
        userUpdateRequestModel.setEmail("updatedEmail@e.com");

        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setUsername(existingUser.getUsername());
        savedUser.setPhoneNumber(existingUser.getPhoneNumber());
        savedUser.setStatus(existingUser.getStatus());
        savedUser.setRoles(existingUser.getRoles());
        savedUser.setEmail("updatedEmail@e.com");

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        UserResponseModel responseModel = userService.update(1L, userUpdateRequestModel);

        verify(userRepository).findById(1L);
        verify(userRepository).save(any(UserEntity.class));

        assertAll(() -> {
            assertEquals("updatedEmail@e.com", responseModel.getEmail());
            assertEquals(existingUser.getUsername(), responseModel.getUsername());
            assertEquals(existingUser.getPhoneNumber(), responseModel.getPhoneNumber());
            assertEquals(existingUser.getStatus().toString(), responseModel.getStatus());
            assertEquals(existingUser.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet()), responseModel.getRoles());
        });
    }

    @Test
    void updateUser_shouldThrowUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserUpdateRequestModel userUpdateRequestModel = new UserUpdateRequestModel();
        userUpdateRequestModel.setEmail("updatedEmail@e.com");

        HospitalNotFoundException exception = assertThrows(HospitalNotFoundException.class,
                () -> userService.update(1L, userUpdateRequestModel));

        verify(userRepository).findById(1L);
        verify(userRepository, never()).deleteById(1L);

        assertAll(() -> {
            assertEquals(USER_NOT_FOUND.getDefaultErrorMessage(), exception.getMessage());
            assertEquals(USER_NOT_FOUND.getDefaultErrorMessage(), exception.getErrorType().getDefaultErrorMessage());
            assertEquals(USER_NOT_FOUND.getErrorCode(), exception.getErrorType().getErrorCode());
        });
    }

    @Test
    void changePassword_success() {
        UserEntity existingUser = testUtils.createUserEntity();
        existingUser.setPassword("encodedOldPass");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        ChangePasswordRequestModel changePasswordRequestModel = new ChangePasswordRequestModel();
        changePasswordRequestModel.setNewPassword("changedPass");
        changePasswordRequestModel.setOldPassword("pass");

        when(passwordEncoder.matches(changePasswordRequestModel.getOldPassword(), existingUser.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("changedPass")).thenReturn("encodedChangedPass");

        userService.changeUserPassword(1L, changePasswordRequestModel);

        verify(passwordEncoder).matches(changePasswordRequestModel.getOldPassword(), "encodedOldPass");
        verify(passwordEncoder).encode("changedPass");
        verify(userRepository).save(existingUser);
        verify(tokenService).invalidateAllUserSession(existingUser);

        assertEquals("encodedChangedPass", existingUser.getPassword());
    }

    @Test
    void changePassword_shouldThrowOldPassNotMatch() {
        UserEntity existingUser = testUtils.createUserEntity();
        existingUser.setPassword("encodedOldPass"); // Ensure the password is set to the encoded old password

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        ChangePasswordRequestModel changePasswordRequestModel = new ChangePasswordRequestModel();
        changePasswordRequestModel.setNewPassword("newPass");
        changePasswordRequestModel.setOldPassword("wrongOldPass");

        when(passwordEncoder.matches("wrongOldPass", existingUser.getPassword())).thenReturn(false);

        HospitalBadRequestException exception = assertThrows(HospitalBadRequestException.class, () -> {
            userService.changeUserPassword(1L, changePasswordRequestModel);
        });

        verify(passwordEncoder).matches("wrongOldPass", "encodedOldPass");
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(tokenService, never()).invalidateAllUserSession(any(UserEntity.class));

        assertAll(() -> {
            assertEquals(OLD_PASSWORD_DID_NOT_MATCH.getDefaultErrorMessage(), exception.getMessage());
            assertEquals(OLD_PASSWORD_DID_NOT_MATCH.getDefaultErrorMessage(), exception.getErrorType().getDefaultErrorMessage());
            assertEquals(OLD_PASSWORD_DID_NOT_MATCH.getErrorCode(), exception.getErrorType().getErrorCode());
        });

    }
}
