package org.hospital.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientCreateRequestModel {
    private String firstName;
    private String lastName;
    private Long cnp;
    private Long age;
    private String gender;
    private String diagnosis;
    private String observations;
    private String indications;

}
