package org.hospital.services;

import lombok.AllArgsConstructor;
import org.hospital.api.model.PatientCreateRequestModel;
import org.hospital.api.model.PatientResponseModel;
import org.hospital.api.model.PatientUpdateRequestModel;
import org.hospital.errorhandling.Errors;
import org.hospital.errorhandling.UncheckedException;
import org.hospital.persistence.entity.AppointmentEntity;
import org.hospital.persistence.entity.MedicEntity;
import org.hospital.persistence.entity.PatientEntity;
import org.hospital.mappers.PatientMapper;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.persistence.repository.AppointmentRepository;
import org.hospital.persistence.repository.MedicRepository;
import org.hospital.persistence.repository.PatientRepository;
import org.hospital.persistence.repository.UserRepository;
import org.hospital.util.ValidationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientMapper patientMapper;
    private final MedicRepository medicRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientResponseModel findById(Long id) {
        return patientMapper.toPatientModel(findPatientById(id));
    }

    @Override
    public PatientResponseModel createPatient(final PatientCreateRequestModel patientCreateRequestModel) {
        try {
            PatientEntity patient = patientRepository.saveAndFlush(patientMapper.toPatientEntity(patientCreateRequestModel));
            return patientMapper.toPatientModel(patient);
        } catch (DataIntegrityViolationException e) {
            throw new UncheckedException(Errors.Functional.EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public PatientResponseModel findPatientByFirstNameAndLastName(final String firstName, final String lastName) {
        PatientEntity patient = patientRepository.findPatientByFirstNameAndLastName(firstName, lastName);

        if (patient == null) {
            throw new UncheckedException(Errors.Functional.PATIENT_NOT_FOUND);
        }

        return patientMapper.toPatientModel(patient);
    }

    @Override
    public PatientResponseModel findPatientByCnp(final Long cnp) {

        PatientEntity patient = patientRepository.findPatientByCnp(cnp);

        if (patient == null ){
            throw new UncheckedException(Errors.Functional.CNP_NOT_FOUND);
        }

        return patientMapper.toPatientModel(patient);
    }

    @Override
    public PatientResponseModel findPatientByMedic(final Long medicId) {

        PatientEntity patient = patientRepository.findPatientByMedicId(medicId);

        if (patient == null ){
            throw new UncheckedException(Errors.Functional.PATIENT_FOR_MEDIC_NOT_FOUND);
        }

        return patientMapper.toPatientModel(patient);
    }

    @Override
    public PatientResponseModel updatePatient(PatientUpdateRequestModel patientUpdateRequestModel, Long id) {
        PatientEntity existingPatient = findPatientById(id);

        patientMapper.updateUserEntity(existingPatient, patientUpdateRequestModel);

        //TODO Check if user is already assigned to the medic
        if (patientUpdateRequestModel.getMedics() != null) {
            setMedicsForPatient(patientUpdateRequestModel, existingPatient);
        }
        if (patientUpdateRequestModel.getAppointments() != null) {
            setPatientIdForAllAppointments(existingPatient);
        }

        return patientMapper.toPatientModel(patientRepository.saveAndFlush(existingPatient));
    }

    @Override
    public void delete(final Long id) {
        PatientEntity patientEntity = patientRepository.findById(id)
                .orElseThrow(() -> new UncheckedException(Errors.Functional.PATIENT_NOT_FOUND));

        UserEntity user = patientEntity.getUser();

        //If there is a patient who is assigned to this medic, remove the medic from patient
        if (!patientEntity.getAppointments().isEmpty()) {
            appointmentRepository.deleteAll(patientEntity.getAppointments());
        }

        if (user != null) {
            user.setPatient(null);
        }

        patientRepository.delete(patientEntity);

        if (user != null) {
            userRepository.delete(user);
        }
    }

    private PatientEntity findPatientById(Long id) {
        return ValidationsUtil.validateEntityExistence(patientRepository.findById(id), "patient.id", id);
    }

    private void setPatientIdForAllAppointments(PatientEntity patient) {
        List<AppointmentEntity> appointments = patient.getAppointments().stream()
                .peek(appointment -> appointment.setPatientId(patient.getPatientId()))
                .collect(Collectors.toList());

        patient.setAppointments(appointments);
    }

    private void setMedicsForPatient(PatientUpdateRequestModel patientUpdateRequestModel, PatientEntity existingPatient) {
        List<MedicEntity> medics = patientUpdateRequestModel.getMedics().stream()
                .map(medic -> medicRepository.findMedicByFirstNameAndLastName(medic.getFirstName(), medic.getLastName()))
                .collect(Collectors.toList());

        existingPatient.setMedics(medics);
    }
}
