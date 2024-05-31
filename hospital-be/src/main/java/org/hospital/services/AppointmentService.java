package org.hospital.services;

import org.hospital.api.model.appointment.AppointmentRequestModel;
import org.hospital.api.model.appointment.AppointmentResponseModel;
import org.hospital.persistence.entity.AppointmentEntity;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseModel findById(final Long id);

    List<AppointmentEntity> findAllAppointmentsFromToday();

    AppointmentResponseModel createAppointment(final AppointmentRequestModel appointmentRequestModel);

    AppointmentResponseModel updateAppointment(final AppointmentRequestModel appointmentRequestModel, final Long id);

    void deleteAppointment(final Long appointmentId);
}
