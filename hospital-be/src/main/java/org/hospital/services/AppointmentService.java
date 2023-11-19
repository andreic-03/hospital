package org.hospital.services;

import org.hospital.persistence.entity.AppointmentEntity;
import org.hospital.api.model.AppointmentDTO;

import java.util.List;

public interface AppointmentService {

    AppointmentDTO findById(final Long id);

    List<AppointmentEntity> findAllAppointmentsFromToday();

    AppointmentDTO createAppointment(final AppointmentDTO appointmentDTO);

    AppointmentDTO updateAppointment(final AppointmentDTO appointmentDTO);
}
