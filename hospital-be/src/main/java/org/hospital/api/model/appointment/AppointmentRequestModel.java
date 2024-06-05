package org.hospital.api.model.appointment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequestModel {
    private Long patientId;
    private LocalDateTime startDate;
    private String appointmentDetails;
    private String diagnosis;
    private String observations;
    private String indications;
    private Long medicId;
}
