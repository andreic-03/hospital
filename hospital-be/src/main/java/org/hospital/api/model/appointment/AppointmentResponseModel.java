package org.hospital.api.model.appointment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponseModel {
    private Long appointmentId;
    private LocalDateTime startDate;
    private String appointmentDetails;
    private Long patientId;
    private Long medicId;
}