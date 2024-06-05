package org.hospital.api.model.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientCreateRequestModel {
    private String firstName;
    private String lastName;
    private String citizenship;
    private String country;
    private String county;
    private String city;
    private String address;
    private String maritalStatus;
    private String cnp;
}
