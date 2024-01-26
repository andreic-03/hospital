package org.hospital.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class MedicResponseModel {
    private Long medicId;
    private String firstName;
    private String lastName;
    private String mobilePhone;
    private String email;
    private String gender;

}
