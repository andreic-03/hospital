package org.hospital.api.model.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hospital.api.model.appointment.AppointmentResponseModel;
import org.hospital.api.model.medic.MedicRequestModel;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientUpdateRequestModel {
    private String firstName;
    private String lastName;
    private String citizenship;
    private String dateOfBirth;
    private String country;
    private String county;
    private String city;
    private String address;
    private String maritalStatus;
    private String cnp;
    private String gender;
    private String diagnosis;
    private String observations;
    private String indications;
    private List<MedicRequestModel> medics;
    private List<AppointmentResponseModel> appointments;

}
