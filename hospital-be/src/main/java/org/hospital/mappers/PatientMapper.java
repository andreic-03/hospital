package org.hospital.mappers;

import org.hospital.api.model.PatientCreateRequestModel;
import org.hospital.api.model.PatientUpdateRequestModel;
import org.hospital.persistence.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.hospital.api.model.PatientResponseModel;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {
    PatientEntity toPatientEntity(PatientCreateRequestModel patientResponseModel);

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    PatientEntity updateUserEntity(@MappingTarget PatientEntity entity, PatientUpdateRequestModel patientUpdateRequestModel);

    PatientResponseModel toPatientModel(PatientEntity patient);
}
