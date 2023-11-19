package org.hospital.controllers;

import org.hospital.api.model.AuthResponseModel;
import org.hospital.api.model.AuthRequestModel;
import org.hospital.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.Valid;
import java.util.Optional;

import static org.hospital.api.model.general.Constants.ACCESS_TOKEN_PREFIX_NAME;
import static org.hospital.api.model.general.Constants.AUTHORIZATION_HEADER_NAME;

@RestController
@RequestMapping(value = "api/v1")
public class AuthenticationController {

	@Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<AuthResponseModel> authenticate(@Valid @RequestBody AuthRequestModel authRequestModel) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequestModel));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authenticationService.logout(getJwtFromRequest());

        return ResponseEntity
                .noContent()
                .build();
    }

    private String getJwtFromRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .map(request -> request.getHeader(AUTHORIZATION_HEADER_NAME))
                .map(authHeader -> authHeader.replace(String.format("%s ", ACCESS_TOKEN_PREFIX_NAME), ""))
                .orElse("");
    }
}
