package org.patients.services;

import org.patients.models.Appointment;
import org.patients.models.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {

    AppointmentDTO findById(final Long id);

    List<Appointment> findAllAppointmentsFromToday();

    AppointmentDTO createAppointment(final AppointmentDTO appointmentDTO);

    AppointmentDTO updateAppointment(final AppointmentDTO appointmentDTO);
}
