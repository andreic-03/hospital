package org.hospital.api.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRequestModel {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;

    @Column(unique = true)
    @Email(message = "Email must be a valid email address")
    @NotBlank(message = "Email is mandatory")
    private String email;
    private String gender;
    private String status;
    private Set<String> roles;

}
