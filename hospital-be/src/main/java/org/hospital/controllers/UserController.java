package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.user.*;
import org.hospital.security.model.AppUserPrincipal;
import org.hospital.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hospital.api.model.general.Constants.*;

@RestController
@RequestMapping(API_USERS)
@AllArgsConstructor
@Validated
public class UserController {
    private UserService userService;

    @GetMapping(value = "info")
    public UserResponseModel getCurrentUser(@AuthenticationPrincipal AppUserPrincipal user) {
        return userService.findByUsername(user.getUsername());
    }

    @GetMapping
    public List<UserResponseModel> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "{id}")
    public UserResponseModel getUserById(@PathVariable final Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public UserResponseModel createUser(@RequestBody final UserRequestModel userDTO) {
        return userService.create(userDTO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable final Long id) {
        userService.deleteById(id);
    }

    @PutMapping
    public UserResponseModel updateUser(@AuthenticationPrincipal AppUserPrincipal user,
                                    @Valid @RequestBody final UserUpdateRequestModel userModel) {
        return userService.update(user.getUserEntity().getId(), userModel);
    }

    @PostMapping("/registration/step-one")
    public UserRegisterStepOneResponseModel userRegisterStepOne(@RequestBody final UserRegisterStepOneRequestModel userRegisterStepOneRequestModel){
        return userService.registerUserStepOne(userRegisterStepOneRequestModel);
    }

    @PutMapping("/password-reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserPassword(@AuthenticationPrincipal AppUserPrincipal user,
                                   @RequestBody @Valid ChangePasswordRequestModel changePasswordRequestModel) {
        userService.changeUserPassword(user.getUserEntity().getId(), changePasswordRequestModel);
    }
}
