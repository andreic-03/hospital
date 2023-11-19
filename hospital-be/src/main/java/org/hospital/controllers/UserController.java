package org.hospital.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.hospital.api.model.UserRequestModel;
import org.hospital.api.model.UserResponseModel;
import org.hospital.api.model.UserUpdateRequestModel;
import org.hospital.security.model.AppUserPrincipal;
import org.hospital.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.hospital.api.model.general.Constants.ROLE_ADMIN;
import static org.hospital.api.model.general.Constants.ROLE_EDITOR;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "info")
    public UserResponseModel getCurrentUser(@AuthenticationPrincipal AppUserPrincipal user) {
        return userService.findByUsername(user.getUsername());
    }

    @GetMapping
    public List<UserResponseModel> list() {
        return userService.findAll();
    }

    @GetMapping(value = "{id}")
    public UserResponseModel get(@PathVariable final Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseModel create(@RequestBody final UserRequestModel userDTO) {
        userDTO.setId(null);
        return userService.create(userDTO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        userService.deleteById(id);
    }

    @PutMapping
    @RolesAllowed({ROLE_EDITOR, ROLE_ADMIN})
    public UserResponseModel update(@AuthenticationPrincipal AppUserPrincipal user,
                                    @Valid @RequestBody final UserUpdateRequestModel userModel) {
        return userService.update(user.getUserEntity().getId(), userModel);
    }
}
