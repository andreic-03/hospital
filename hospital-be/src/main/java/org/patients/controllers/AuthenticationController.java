package org.patients.controllers;

import org.patients.models.dto.AuthenticationResponseDTO;
import org.patients.models.dto.CredentialDTO;
import org.patients.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1")
public class AuthenticationController {

	@Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@Valid @RequestBody CredentialDTO credentialDTO) {
        return ResponseEntity.ok(new AuthenticationResponseDTO(authenticationService.authenticate(credentialDTO)));
    }
}
