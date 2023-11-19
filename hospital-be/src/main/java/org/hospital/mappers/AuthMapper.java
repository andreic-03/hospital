package org.hospital.mappers;

import org.hospital.api.model.AuthResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    AuthResponseModel toAuthResponseModel(final String username, final String accessToken);
}
