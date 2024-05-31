package org.hospital.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.api.model.user.*;
import org.hospital.common.model.NotificationDetails;
import org.hospital.common.util.NotificationDetailsUtil;
import org.hospital.configuration.exception.model.ErrorType;
import org.hospital.configuration.exception.model.HospitalBadRequestException;
import org.hospital.configuration.exception.model.HospitalException;
import org.hospital.configuration.exception.model.HospitalNotFoundException;
import org.hospital.configuration.porperties.EmailNotificationProperties;
import org.hospital.mappers.MedicMapper;
import org.hospital.mappers.PatientMapper;
import org.hospital.mappers.RegisterAccountTokenMapper;
import org.hospital.mappers.UserMapper;
import org.hospital.persistence.entity.*;
import org.hospital.persistence.repository.*;
import org.hospital.services.crypto.CryptoHashService;
import org.hospital.services.notification.dispatcher.NotificationDispatcherService;
import org.hospital.services.random.SecureRandomGeneratorService;
import org.hospital.services.security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hospital.common.model.NotificationDetails.REGISTER_LINK_KEY;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MedicRepository medicRepository;
    private final PatientRepository patientRepository;
    private final RegisterAccountTokenRepository registerAccountTokenRepository;
    private final UserMapper userMapper;
    private final MedicMapper medicMapper;
    private final PatientMapper patientMapper;
    private final RegisterAccountTokenMapper registerAccountTokenMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotificationProperties emailNotificationProperties;
    private final SecureRandomGeneratorService secureRandomGeneratorService;
    private final NotificationDispatcherService notificationDispatcherService;
    private final CryptoHashService cryptoHashService;
    private final TokenService tokenService;

    @Value("${app.password-reset.expire-in}")
    private Integer expireIn;

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
                .orElseThrow(() -> new HospitalNotFoundException(ErrorType.USER_NOT_FOUND));
    }

    @Override
    public UserResponseModel findByUsername(final String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserModel)
                .orElseThrow(() -> new HospitalNotFoundException(ErrorType.USER_NOT_FOUND));
    }

    @Override
    public UserResponseModel create(@Valid final UserRequestModel requestModel) {
        UserEntity userEntity = userMapper.toUserEntity(requestModel);

        Set<RoleEntity> roles = mapRoles(requestModel.getRoles());
        userEntity.setRoles(roles);

        userEntity.setPassword(passwordEncoder.encode(requestModel.getPassword()));
        userEntity.setStatus(UserStatus.ACTIVE);

        UserEntity user = userRepository.saveAndFlush(userEntity);

        if (requestModel.getMedic() != null) {
            MedicEntity medic = medicMapper.toMedicEntity(requestModel.getMedic());
            medic.setUser(user);

            medicRepository.save(medic);
        }

        if (requestModel.getPatient() != null) {
            PatientEntity patient = patientMapper.toPatientEntity(requestModel.getPatient());
            patient.setUser(user);

            patientRepository.save(patient);
        }

        return userMapper.toUserModel(user);
    }

    @Override
    public UserRegisterStepOneResponseModel registerUserStepOne(final UserRegisterStepOneRequestModel userRegisterStepOneRequestModel) {
        if (getUserByEmail(userRegisterStepOneRequestModel.getEmail()) != null) {
            throw new HospitalException(ErrorType.EMAIL_ALREADY_EXISTS);
        }

        UserEntity userEntity = userMapper.stepOneToUserEntity(userRegisterStepOneRequestModel);

        Set<RoleEntity> roles = mapRoles(userRegisterStepOneRequestModel.getRoles());
        userEntity.setRoles(roles);

        userEntity.setPassword(passwordEncoder.encode(userRegisterStepOneRequestModel.getPassword()));
        userEntity.setStatus(UserStatus.WAITING_CONFIRMATION);

        final var token = secureRandomGeneratorService.generateRandomString();
        final var hashedToken = cryptoHashService.hashContent(token);
        final var expireAt = LocalDateTime.now().plusMinutes(expireIn);
        final var registerAccountTokenEntity = registerAccountTokenMapper.toRegisterAccountTokenEntity(hashedToken, userEntity, expireAt);


        userRepository.save(userEntity);
        registerAccountTokenRepository.save(registerAccountTokenEntity);

        sendRegistrationEmail(userRegisterStepOneRequestModel, token);

        return userMapper.stepOneToUserModel(userEntity);
    }

    @Override
    public void deleteById(final Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseModel update(final Long id, final UserUpdateRequestModel userModel) {
        UserEntity existingUser = getUserById(id);

        userMapper.updateUserEntity(existingUser, userModel);

        return userMapper.toUserModel(userRepository.save(existingUser));
    }

    @Override
    public void changeUserPassword(final Long id, final ChangePasswordRequestModel changePasswordRequestModel) {
        UserEntity user = getUserById(id);

        final var oldPassword = changePasswordRequestModel.getOldPassword();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new HospitalBadRequestException(ErrorType.OLD_PASSWORD_DID_NOT_MATCH);
        }

        final var newHashedPassword = passwordEncoder.encode(changePasswordRequestModel.getNewPassword());
        user.setPassword(newHashedPassword);
        userRepository.save(user);

        tokenService.invalidateAllUserSession(user);
    }

    private UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new HospitalNotFoundException(ErrorType.USER_NOT_FOUND));
    }

    private Set<RoleEntity> mapRoles(Set<String> roles) {
        return roles
                .stream()
                .map(roleRepository::findByName)
                .map(roleEntity -> roleEntity.orElseThrow(() -> new HospitalNotFoundException(ErrorType.ROLE_NOT_FOUND)))
                .collect(Collectors.toSet());
    }

    protected void sendRegistrationEmail(UserRegisterStepOneRequestModel userRegisterStepOneRequestModel, String token) {
        final var notificationRegisterLink = NotificationDetailsUtil.getAdditionalProperty(
                emailNotificationProperties,
                NotificationDetails.NotificationType.REGISTER_ACCOUNT,
                REGISTER_LINK_KEY
                ).replace(NotificationDetails.TOKEN_PLACEHOLDER_KEY, token);

        final var notificationDetails = new NotificationDetails(NotificationDetails.NotificationType.REGISTER_ACCOUNT)
                .addKeyValueItem(NotificationDetails.TO_KEY, userRegisterStepOneRequestModel.getEmail())
                .addKeyValueItem(NotificationDetails.USERNAME_KEY, userRegisterStepOneRequestModel.getUsername())
                .addKeyValueItem(REGISTER_LINK_KEY, notificationRegisterLink);

        log.info("Send register email to: {}", userRegisterStepOneRequestModel.getEmail());

        try {
            notificationDispatcherService.dispatchNotification(notificationDetails);
            log.info("Notification successfully sent to: {}", userRegisterStepOneRequestModel.getEmail());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
