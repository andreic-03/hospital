package org.hospital.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegisterStepOneRequestModel {
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}
