package org.hospital.services;

import lombok.AllArgsConstructor;
import org.hospital.errorhandling.Errors;
import org.hospital.errorhandling.UncheckedException;
import org.hospital.persistence.entity.AppointmentEntity;
import org.hospital.persistence.entity.MedicEntity;
import org.hospital.persistence.entity.PatientEntity;
import org.hospital.api.model.PatientDTO;
import org.hospital.mappers.MedicMapper;
import org.hospital.mappers.PatientMapper;
import org.hospital.persistence.repository.PatientRepository;
import org.hospital.util.ValidationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientMapper patientMapper;
    private final MedicMapper medicMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicService medicService;

    @Override
    public PatientDTO findById(Long id) {
        return patientMapper.convertPatientEntityToDTO(findPatientById(id));
    }

    @Override
    public PatientDTO createPatient(final PatientDTO patientDTO) {
        PatientEntity patient = patientRepository.saveAndFlush(patientMapper.convertPatientDTOtoEntity(patientDTO));

        setPatientIdForAllAppointments(patient);
        setMedicIdForPatient(patient);

        return patientMapper.convertPatientEntityToDTO(patient);
    }

    @Override
    public PatientDTO findPatientByFirstNameAndLastName(final String firstName, final String lastName) {
        return patientMapper.convertPatientEntityToDTO(patientRepository.findPatientByFirstNameAndLastName(firstName, lastName));
    }

    @Override
    public PatientDTO findPatientByCnp(final Long cnp) {

        PatientEntity patient = patientRepository.findPatientByCnp(cnp);

        if (patient == null ){
            throw new UncheckedException(Errors.Functional.CNP_NOT_FOUND);
        }

        return patientMapper.convertPatientEntityToDTO(patient);
    }

    @Override
    public PatientDTO findPatientByMedic(final Long medicId) {

        PatientEntity patient = patientRepository.findPatientByMedicId(medicId);

        if (patient == null ){
            throw new UncheckedException(Errors.Functional.MEDIC_NOT_FOUND);
        }

        return patientMapper.convertPatientEntityToDTO(patient);
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

    private PatientEntity findPatientById(Long id) {
        return ValidationsUtil.validateEntityExistence(patientRepository.findById(id), "patient.id", id);
    }

    private void setPatientIdForAllAppointments(PatientEntity patient) {
        List<AppointmentEntity> appointments = patient.getAppointments().stream()
                .peek(appointment -> appointment.setPatientId(patient.getPatientId()))
                .collect(Collectors.toList());

        patient.setAppointments(appointments);
    }

    private void setMedicIdForPatient(PatientEntity patient) {
        List<MedicEntity> medics = patient.getMedics().stream()
                .map(medic -> medicMapper.convertMedicDTOtoEntity(medicService.findById(medic.getMedicId())))
                .collect(Collectors.toList());

        patient.setMedics(medics);
    }
}
