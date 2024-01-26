package org.hospital.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRequestModel {

    private String username;
    private String password;
    private String status;
    private Set<String> roles;
    private MedicRequestModel medic;
    private PatientCreateRequestModel patient;

}
