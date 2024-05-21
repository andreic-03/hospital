package org.hospital.mappers;

import org.hospital.api.model.PatientCreateRequestModel;
import org.hospital.api.model.PatientUpdateRequestModel;
import org.hospital.api.model.user.UserRegisterStepTwoRequestModel;
import org.hospital.persistence.entity.PatientEntity;
import org.mapstruct.*;
import org.hospital.api.model.PatientResponseModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {
    PatientEntity toPatientEntity(PatientCreateRequestModel patientResponseModel);

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    PatientEntity updatePatientEntity(@MappingTarget PatientEntity entity, PatientUpdateRequestModel patientUpdateRequestModel);

    PatientResponseModel toPatientModel(PatientEntity patient);

    PatientEntity toPatientStepTwoEntity(UserRegisterStepTwoRequestModel userRegisterStepTwoRequestModel);
}
