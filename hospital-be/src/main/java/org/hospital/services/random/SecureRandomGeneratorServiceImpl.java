package org.hospital.services.random;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class SecureRandomGeneratorServiceImpl implements SecureRandomGeneratorService {
    @Override
    public String generateRandomString() {
        final var random = new SecureRandom();
        final byte bytes[] = new byte[64];
        random.nextBytes(bytes);
        final var encoder = Base64.getUrlEncoder().withoutPadding();
        final var token = encoder.encodeToString(bytes);
        return token;
    }
}
