package org.hospital.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.api.model.appointment.AppointmentRequestModel;
import org.hospital.api.model.appointment.AppointmentResponseModel;
import org.hospital.configuration.exception.model.ErrorType;
import org.hospital.configuration.exception.model.HospitalException;
import org.hospital.persistence.entity.AppointmentEntity;
import org.hospital.mappers.AppointmentMapper;
import org.hospital.persistence.entity.MedicEntity;
import org.hospital.persistence.entity.PatientEntity;
import org.hospital.persistence.repository.AppointmentRepository;
import org.hospital.persistence.repository.MedicRepository;
import org.hospital.persistence.repository.PatientRepository;
import org.hospital.util.ValidationsUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentMapper appointmentMapper;

    private AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;
    private MedicRepository medicRepository;

    @Override
    public AppointmentResponseModel findById(final Long id) {
        return appointmentMapper.toAppointmentModel(findAppointmentById(id));
    }

    @Transactional
    @Override
    public AppointmentResponseModel createAppointment(final AppointmentRequestModel appointmentRequestModel) {
        PatientEntity patient = patientRepository.findById(appointmentRequestModel.getPatientId())
                .orElseThrow(() -> new HospitalException(ErrorType.PATIENT_NOT_FOUND));
        MedicEntity medic = medicRepository.findById(appointmentRequestModel.getMedicId())
                .orElseThrow(() -> new HospitalException(ErrorType.MEDIC_NOT_FOUND));

        AppointmentEntity appointmentEntity = appointmentMapper.toAppointmentEntity(appointmentRequestModel);
        appointmentEntity.setPatient(patient);
        appointmentEntity.setMedic(medic);

        patient.getMedics().add(medic);
        patientRepository.save(patient);
        medic.getPatients().add(patient);
        medicRepository.save(medic);

        AppointmentEntity savedAppointment = appointmentRepository.saveAndFlush(appointmentEntity);

        return appointmentMapper.toAppointmentModel(savedAppointment);
    }

    @Override
    public AppointmentResponseModel updateAppointment(AppointmentRequestModel appointmentRequestModel, Long id) {
        findAppointmentById(id);

        AppointmentEntity appointment = appointmentMapper.toAppointmentEntity(appointmentRequestModel);

        return appointmentMapper.toAppointmentModel(appointmentRepository.saveAndFlush(appointment));
    }

    @Override
    public List<AppointmentEntity> findAllAppointmentsFromToday() {
        return appointmentRepository.findAllAppointmentsFromToday();
    }

    @Transactional
    @Override
    public void deleteAppointment(Long appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new HospitalException(ErrorType.APPOINTMENT_NOT_FOUND));

        PatientEntity patient = appointment.getPatient();
        MedicEntity medic = appointment.getMedic();

        patient.getAppointments().remove(appointment);
        medic.getAppointments().remove(appointment);
        patient.getMedics().remove(medic);

        patientRepository.save(patient);
        medicRepository.save(medic);

        appointmentRepository.delete(appointment);

//        logger.info("Deleted appointment with id {}", appointmentId);
    }

    private AppointmentEntity findAppointmentById(Long id) {
        return ValidationsUtil.validateEntityExistence(appointmentRepository.findById(id), "appointment.id", id);
    }

}
