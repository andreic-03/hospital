package org.hospital.api.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterStepTwoRequestModel {
    private String token;
    private String firstName;
    private String lastName;
    private Long cnp;
    private Long age;
    private String gender;
}
