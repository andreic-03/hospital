package org.patients.controllers;

import org.patients.models.Credential;
import org.patients.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/credential")
public class CredentialController {

    @Autowired
    CredentialService credentialService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Credential create(@RequestBody final Credential credential) {
        return credentialService.registerNewUserAccount(credential);
    }
}
