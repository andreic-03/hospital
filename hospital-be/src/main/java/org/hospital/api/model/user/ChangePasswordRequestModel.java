package org.hospital.api.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordRequestModel {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
