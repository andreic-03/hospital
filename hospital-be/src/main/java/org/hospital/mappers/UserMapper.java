package org.hospital.mappers;

import org.hospital.api.model.UserRequestModel;
import org.hospital.api.model.UserResponseModel;
import org.hospital.api.model.UserUpdateRequestModel;
import org.hospital.persistence.entity.RoleEntity;
import org.hospital.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(source = "medic.medicId", target = "medicId")
    @Mapping(source = "patient.patientId", target = "patientId")
    UserResponseModel toUserModel(UserEntity userEntity);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "jwtTokens", ignore = true)
    UserEntity toUserEntity(UserRequestModel user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "jwtTokens", ignore = true)
    void updateUserEntity(@MappingTarget UserEntity entity, UserUpdateRequestModel updateEntity);

    default String toRoleStr(RoleEntity role) {
        return role.getName();
    }
}
