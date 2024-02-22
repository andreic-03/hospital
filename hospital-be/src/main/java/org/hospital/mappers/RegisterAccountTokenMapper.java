package org.hospital.mappers;

import org.hospital.persistence.entity.RegisterAccountTokenEntity;
import org.hospital.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegisterAccountTokenMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "used", constant = "false")
    @Mapping(source = "user", target = "user")
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "version", ignore = true)
    RegisterAccountTokenEntity toRegisterAccountTokenEntity(final String token, final UserEntity user, final LocalDateTime expireAt);
}
