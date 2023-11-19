package org.hospital.services;

import org.hospital.api.model.AuthRequestModel;
import org.hospital.api.model.AuthResponseModel;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

	AuthResponseModel authenticate(AuthRequestModel authRequestModel);

	void logout(String jwtToken);

}
