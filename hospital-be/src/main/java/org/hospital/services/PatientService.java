package org.hospital.services;

import org.hospital.api.model.patient.PatientCreateRequestModel;
import org.hospital.api.model.patient.PatientResponseModel;
import org.hospital.api.model.patient.PatientUpdateRequestModel;
import org.hospital.api.model.user.UserRegisterStepTwoRequestModel;
import org.hospital.persistence.entity.UserEntity;

import java.util.List;

public interface PatientService {

    PatientResponseModel findById(final Long id);

    PatientResponseModel createPatient(final PatientCreateRequestModel patientCreateRequestModel);

    PatientResponseModel findPatientByFirstNameAndLastName(final String firstName, final String lastName);

    PatientResponseModel findPatientByCnp(final String cnp);

    PatientResponseModel findPatientByMedic(final Long medicId);

    PatientResponseModel updatePatient(final PatientUpdateRequestModel patientUpdateRequestModel, final Long id);

    void delete(final Long id);

    PatientResponseModel registerUserStepTwo(final UserRegisterStepTwoRequestModel userRegisterStepTwoRequestModel);

    List<PatientResponseModel> getPatientsCreatedByCurrentUser(final UserEntity user);
}
