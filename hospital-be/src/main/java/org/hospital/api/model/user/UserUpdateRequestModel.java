package org.hospital.api.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
