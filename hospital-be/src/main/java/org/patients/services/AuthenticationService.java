package org.patients.services;

import org.patients.models.dto.CredentialDTO;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

	String authenticate(CredentialDTO credentialDTO);

	Authentication authenticate(String token);

}
