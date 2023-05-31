package org.patients.controllers;

import org.patients.models.Credential;
import org.patients.models.dto.UserDTO;
import org.patients.models.dto.UserNameInfoDTO;
import org.patients.services.CredentialService;
import org.patients.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CredentialService credentialService;

    @GetMapping(value = "info/{username}")
    public UserDTO getCurrentUser(@PathVariable final String username) {
        final Long credentialId = credentialService.findByUsername(username).getId();
        return userService.findById(credentialId);
    }

    @GetMapping
    public List<UserDTO> list() {
        return userService.findAll();
    }

    @GetMapping(value = "{id}")
    public UserDTO get(@PathVariable final Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody final UserDTO userDTO) {
        userDTO.setId(null);
        return userService.create(userDTO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        userService.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public UserDTO update(@PathVariable final Long id, @Valid @RequestBody final UserDTO userDTO) {
        userDTO.setId(id);
        return userService.update(userDTO);
    }
}
