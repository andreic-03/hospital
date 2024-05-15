package org.hospital.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseModel {
    private Long id;
    private String username;
    private String status;
    private String email;
    private Set<String> roles;
    private Long medicId;
    private Long patientId;
}
