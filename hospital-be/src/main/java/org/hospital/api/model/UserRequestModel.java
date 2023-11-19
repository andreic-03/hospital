package org.hospital.api.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
