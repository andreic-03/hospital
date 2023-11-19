package org.hospital.services.security;

import lombok.AllArgsConstructor;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.persistence.repository.TokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public void invalidateAllUserSession(final UserEntity userEntity) {
        tokenRepository.deleteAllByUser(userEntity);
    }

    @Override
    public boolean isUserSessionKnown(final String jwtToken) {
        return tokenRepository.existsByToken(jwtToken);
    }

}
