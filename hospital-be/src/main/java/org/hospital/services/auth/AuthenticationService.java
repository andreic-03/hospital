package org.hospital.services.auth;

import org.hospital.api.model.auth.AuthRequestModel;
import org.hospital.api.model.auth.AuthResponseModel;

public interface AuthenticationService {

	AuthResponseModel login(AuthRequestModel authRequestModel);

	void logout(String jwtToken);

}
