package org.hospital.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientUpdateRequestModel {
    private String firstName;
    private String lastName;
    private Long cnp;
    private Long age;
    private String gender;
    private String diagnosis;
    private String observations;
    private String indications;
    private List<MedicRequestModel> medics;
    private List<AppointmentDTO> appointments;

}
