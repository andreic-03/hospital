package org.hospital.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {
    private Long patientId;
    private LocalDateTime startDate;
    private String appointmentDetails;
}
