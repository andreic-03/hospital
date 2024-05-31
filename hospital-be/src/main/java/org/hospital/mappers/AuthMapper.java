package org.hospital.mappers;

import org.hospital.api.model.auth.AuthResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    AuthResponseModel toAuthResponseModel(final String username, final String accessToken);
}
