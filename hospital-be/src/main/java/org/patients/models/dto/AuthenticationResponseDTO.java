package org.patients.models.dto;

import java.util.Objects;

public class AuthenticationResponseDTO {
    private final String jwtToken;

    public AuthenticationResponseDTO(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationResponseDTO that = (AuthenticationResponseDTO) o;
        return jwtToken.equals(that.jwtToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwtToken);
    }
}
