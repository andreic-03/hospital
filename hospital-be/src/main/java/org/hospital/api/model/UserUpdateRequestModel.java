package org.hospital.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserUpdateRequestModel {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
