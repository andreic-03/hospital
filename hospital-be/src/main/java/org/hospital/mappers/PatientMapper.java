package org.hospital.mappers;

import org.hospital.persistence.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.hospital.api.model.PatientDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {
    PatientEntity convertPatientDTOtoEntity(PatientDTO patientDTO);

    PatientDTO convertPatientEntityToDTO(PatientEntity patient);
}
