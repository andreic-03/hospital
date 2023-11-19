package org.hospital.services.security;


import org.hospital.persistence.entity.UserEntity;

public interface TokenService {
    void invalidateAllUserSession(UserEntity userEntity);
    boolean isUserSessionKnown(String jwtToken);
}
