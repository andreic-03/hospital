package org.hospital.services.auth;

import org.hospital.api.model.AuthRequestModel;
import org.hospital.api.model.AuthResponseModel;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

	AuthResponseModel login(AuthRequestModel authRequestModel);

	void logout(String jwtToken);

}
