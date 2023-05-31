package org.patients.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.patients.models.Patient;
import org.patients.models.dto.PatientDTO;

@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    Patient convertPatientDTOtoEntity(PatientDTO patientDTO);

    PatientDTO convertPatientEntityToDTO(Patient patient);
}
