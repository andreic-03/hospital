package org.hospital.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterStepOneResponseModel {
    private Long userId;
    private String username;
    private String email;
}
