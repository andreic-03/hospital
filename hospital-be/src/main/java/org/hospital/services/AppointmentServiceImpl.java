package org.hospital.services;

import org.hospital.persistence.entity.AppointmentEntity;
import org.hospital.api.model.AppointmentDTO;
import org.hospital.mappers.AppointmentMapper;
import org.hospital.persistence.repository.AppointmentRepository;
import org.hospital.persistence.repository.PatientRepository;
import org.hospital.util.ValidationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final AppointmentMapper APPOINTMENT_MAPPER = AppointmentMapper.INSTANCE;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientService patientService;

    @Override
    public AppointmentDTO findById(final Long id) {
        return APPOINTMENT_MAPPER.convertAppointmentEntityToDTO(findAppointmentById(id));
    }

    @Override
    public AppointmentDTO createAppointment(final AppointmentDTO appointmentDTO) {
        patientService.findById(appointmentDTO.getPatientId());

        return APPOINTMENT_MAPPER.convertAppointmentEntityToDTO(appointmentRepository.saveAndFlush(APPOINTMENT_MAPPER.convertAppointmentDTOtoEntity((appointmentDTO))));
    }

    @Override
    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO) {
        findAppointmentById(appointmentDTO.getAppointmentId());

        AppointmentEntity appointment = APPOINTMENT_MAPPER.convertAppointmentDTOtoEntity(appointmentDTO);

        return APPOINTMENT_MAPPER.convertAppointmentEntityToDTO(appointmentRepository.saveAndFlush(appointment));
    }

    @Override
    public List<AppointmentEntity> findAllAppointmentsFromToday() {
        return appointmentRepository.findAllAppointmentsFromToday();
    }

    private AppointmentEntity findAppointmentById(Long id) {
        return ValidationsUtil.validateEntityExistence(appointmentRepository.findById(id), "appointment.id", id);
    }

}
