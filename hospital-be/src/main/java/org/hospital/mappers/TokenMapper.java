package org.hospital.mappers;

import org.hospital.persistence.entity.TokenEntity;
import org.hospital.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TokenMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user", target = "user")
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "version", ignore = true)
    TokenEntity toTokenEntity(final String token, final UserEntity user);
}
