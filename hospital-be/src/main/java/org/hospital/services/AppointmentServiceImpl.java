package org.hospital.services;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentMapper appointmentMapper;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientService patientService;

    @Override
    public AppointmentDTO findById(final Long id) {
        return appointmentMapper.convertAppointmentEntityToDTO(findAppointmentById(id));
    }

    @Override
    public AppointmentDTO createAppointment(final AppointmentDTO appointmentDTO) {
        patientService.findById(appointmentDTO.getPatientId());

        return appointmentMapper.convertAppointmentEntityToDTO(appointmentRepository.saveAndFlush(appointmentMapper.convertAppointmentDTOtoEntity((appointmentDTO))));
    }

    @Override
    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO, Long id) {
        findAppointmentById(id);

        AppointmentEntity appointment = appointmentMapper.convertAppointmentDTOtoEntity(appointmentDTO);

        return appointmentMapper.convertAppointmentEntityToDTO(appointmentRepository.saveAndFlush(appointment));
    }

    @Override
    public List<AppointmentEntity> findAllAppointmentsFromToday() {
        return appointmentRepository.findAllAppointmentsFromToday();
    }

    private AppointmentEntity findAppointmentById(Long id) {
        return ValidationsUtil.validateEntityExistence(appointmentRepository.findById(id), "appointment.id", id);
    }

}
