package org.hospital.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hospital.api.model.auth.AuthRequestModel;
import org.hospital.api.model.auth.AuthResponseModel;
import org.hospital.services.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static org.hospital.api.model.general.Constants.*;

@RestController
@RequestMapping(value = API_AUTH)
@AllArgsConstructor
@Validated
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseModel> login(@Valid @RequestBody AuthRequestModel authRequestModel) {
        return ResponseEntity.ok(authenticationService.login(authRequestModel));
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
