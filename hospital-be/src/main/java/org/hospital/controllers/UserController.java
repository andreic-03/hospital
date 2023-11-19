package org.hospital.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.hospital.api.model.UserRequestModel;
import org.hospital.api.model.UserResponseModel;
import org.hospital.api.model.UserUpdateRequestModel;
import org.hospital.security.model.AppUserPrincipal;
import org.hospital.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @RolesAllowed(ROLE_ADMIN)
    public UserResponseModel createUser(@RequestBody final UserRequestModel userDTO) {
        userDTO.setId(null);
        return userService.create(userDTO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable final Long id) {
        userService.deleteById(id);
    }

    @PutMapping
    @RolesAllowed({ROLE_EDITOR, ROLE_ADMIN})
    public UserResponseModel updateUser(@AuthenticationPrincipal AppUserPrincipal user,
                                    @Valid @RequestBody final UserUpdateRequestModel userModel) {
        return userService.update(user.getUserEntity().getId(), userModel);
    }
}
