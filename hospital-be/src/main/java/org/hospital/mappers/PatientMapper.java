package org.hospital.mappers;

import org.hospital.persistence.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.hospital.api.model.PatientDTO;

@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientEntity convertPatientDTOtoEntity(PatientDTO patientDTO);

    PatientDTO convertPatientEntityToDTO(PatientEntity patient);
}
