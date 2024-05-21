package org.hospital.api.model.user;

import lombok.Getter;
import lombok.Setter;
import org.hospital.api.model.MedicRequestModel;
import org.hospital.api.model.PatientCreateRequestModel;

import java.util.Set;

@Getter
@Setter
public class UserRequestModel {

    private String username;
    private String password;
    private String status;
    private String email;
    private String phoneNumber;
    private Set<String> roles;
    private MedicRequestModel medic;
    private PatientCreateRequestModel patient;

}
