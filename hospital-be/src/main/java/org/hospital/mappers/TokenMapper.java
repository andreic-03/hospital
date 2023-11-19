package org.hospital.mappers;

import org.hospital.persistence.entity.TokenEntity;
import org.hospital.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);

    @Mapping(target = "id", ignore = true)
    TokenEntity toTokenEntity(final String token, final UserEntity user);
}
