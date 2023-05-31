package org.patients.services;

import org.patients.errorhandling.Errors;
import org.patients.errorhandling.UncheckedException;
import org.patients.models.Appointment;
import org.patients.models.Medic;
import org.patients.models.Patient;
import org.patients.models.dto.PatientDTO;
import org.patients.models.mappers.MedicMapper;
import org.patients.models.mappers.PatientMapper;
import org.patients.repositories.PatientRepository;
import org.patients.util.ValidationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private static final PatientMapper PATIENT_MAPPER = PatientMapper.INSTANCE;
    private static final MedicMapper MEDIC_MAPPER = MedicMapper.INSTANCE;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicService medicService;

    @Override
    public PatientDTO findById(Long id) {
        return PATIENT_MAPPER.convertPatientEntityToDTO(findPatientById(id));
    }

    @Override
    public PatientDTO createPatient(final PatientDTO patientDTO) {
        Patient patient = patientRepository.saveAndFlush(PATIENT_MAPPER.convertPatientDTOtoEntity(patientDTO));

        setPatientIdForAllAppointments(patient);
        setMedicIdForPatient(patient);

        return PATIENT_MAPPER.convertPatientEntityToDTO(patient);
    }

    @Override
    public PatientDTO findPatientByFirstNameAndLastName(final String firstName, final String lastName) {
        return PATIENT_MAPPER.convertPatientEntityToDTO(patientRepository.findPatientByFirstNameAndLastName(firstName, lastName));
    }

    @Override
    public PatientDTO findPatientByCnp(final Long cnp) {

        Patient patient = patientRepository.findPatientByCnp(cnp);

        if (patient == null ){
            throw new UncheckedException(Errors.Functional.CNP_NOT_FOUND);
        }

        return PATIENT_MAPPER.convertPatientEntityToDTO(patient);
    }

    @Override
    public PatientDTO findPatientByMedic(final Long medicId) {

        Patient patient = patientRepository.findPatientByMedicId(medicId);

        if (patient == null ){
            throw new UncheckedException(Errors.Functional.MEDIC_NOT_FOUND);
        }

        return PATIENT_MAPPER.convertPatientEntityToDTO(patient);
    }
//
//    @Override
//    public PatientDTO updatePatient(PatientDTO patientDTO) {
//        findPatientById(patientDTO.getPatientId());
//
//        Patient patient = PATIENT_MAPPER.convertAppointmentDTOtoEntity(patientDTO);
//        setPatientIdForAllAppointments(patient);
//
//        return PATIENT_MAPPER.convertAppointmentEntityToDTO(patientRepository.saveAndFlush(patient));
//    }

    private Patient findPatientById(Long id) {
        return ValidationsUtil.validateEntityExistence(patientRepository.findById(id), "patient.id", id);
    }

    private void setPatientIdForAllAppointments(Patient patient) {
        List<Appointment> appointments = patient.getAppointments().stream()
                .peek(appointment -> appointment.setPatientId(patient.getPatientId()))
                .collect(Collectors.toList());

        patient.setAppointments(appointments);
    }

    private void setMedicIdForPatient(Patient patient) {
        List<Medic> medics = patient.getMedics().stream()
                .map(medic -> MEDIC_MAPPER.convertMedicDTOtoEntity(medicService.findById(medic.getMedicId())))
                .collect(Collectors.toList());

        patient.setMedics(medics);
    }
}
