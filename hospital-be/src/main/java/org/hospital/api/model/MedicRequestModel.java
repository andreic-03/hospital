package org.hospital.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hospital.persistence.entity.MedicSpecialization;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class MedicRequestModel {
    private String firstName;
    private String lastName;
    private String mobilePhone;
    private String email;
    private String gender;
    private MedicSpecialization specialization;

}
