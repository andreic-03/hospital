package org.hospital.mappers;

import org.hospital.api.model.AuthResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    AuthResponseModel toAuthResponseModel(final String username, final String accessToken);
}
