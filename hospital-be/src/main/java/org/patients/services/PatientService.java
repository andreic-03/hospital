package org.patients.services;

import org.patients.models.dto.PatientDTO;

public interface PatientService {

    PatientDTO findById(final Long id);

    PatientDTO createPatient(final PatientDTO patientDTO);

    PatientDTO findPatientByFirstNameAndLastName(final String firstName, final String lastName);

    PatientDTO findPatientByCnp(final Long cnp);

    PatientDTO findPatientByMedic(final Long medicId);

//    PatientDTO updatePatient(final PatientDTO patientDTO);
}
