package org.hospital.mappers;

import org.hospital.api.model.MedicRequestModel;
import org.hospital.api.model.MedicResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.hospital.persistence.entity.MedicEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicMapper {
    MedicEntity toMedicEntity(MedicRequestModel medicRequestModel);

    MedicResponseModel toUserModel(MedicEntity medic);
}
