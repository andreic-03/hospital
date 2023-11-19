package org.hospital.services.auth;

import org.hospital.security.model.AppUserPrincipal;

import java.util.Optional;

public interface AppUserPrincipalService {
    Optional<AppUserPrincipal> getLoggedUserInfo();

    AppUserPrincipal getLoggedUserInfoOrThrow();
}
