package org.hospital.services;

import org.hospital.api.model.PatientCreateRequestModel;
import org.hospital.api.model.PatientResponseModel;
import org.hospital.api.model.PatientUpdateRequestModel;
import org.hospital.api.model.UserRegisterStepTwoRequestModel;

public interface PatientService {

    PatientResponseModel findById(final Long id);

    PatientResponseModel createPatient(final PatientCreateRequestModel patientCreateRequestModel);

    PatientResponseModel findPatientByFirstNameAndLastName(final String firstName, final String lastName);

    PatientResponseModel findPatientByCnp(final Long cnp);

    PatientResponseModel findPatientByMedic(final Long medicId);

    PatientResponseModel updatePatient(final PatientUpdateRequestModel patientUpdateRequestModel, final Long id);

    void delete(final Long id);

    PatientResponseModel registerUserStepTwo(final UserRegisterStepTwoRequestModel userRegisterStepTwoRequestModel);
}
