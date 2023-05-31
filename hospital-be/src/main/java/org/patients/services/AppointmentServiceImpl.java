package org.patients.services;

import org.patients.models.Appointment;
import org.patients.models.dto.AppointmentDTO;
import org.patients.models.mappers.AppointmentMapper;
import org.patients.repositories.AppointmentRepository;
import org.patients.repositories.PatientRepository;
import org.patients.util.ValidationsUtil;
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

        Appointment appointment = APPOINTMENT_MAPPER.convertAppointmentDTOtoEntity(appointmentDTO);

        return APPOINTMENT_MAPPER.convertAppointmentEntityToDTO(appointmentRepository.saveAndFlush(appointment));
    }

    @Override
    public List<Appointment> findAllAppointmentsFromToday() {
        return appointmentRepository.findAllAppointmentsFromToday();
    }

    private Appointment findAppointmentById(Long id) {
        return ValidationsUtil.validateEntityExistence(appointmentRepository.findById(id), "appointment.id", id);
    }

}
