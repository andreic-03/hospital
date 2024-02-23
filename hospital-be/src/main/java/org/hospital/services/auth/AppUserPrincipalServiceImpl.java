package org.hospital.services.auth;

import lombok.extern.slf4j.Slf4j;
import org.hospital.configuration.exception.model.ErrorType;
import org.hospital.configuration.exception.model.HospitalUnauthorizedException;
import org.hospital.security.model.AppUserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class AppUserPrincipalServiceImpl implements AppUserPrincipalService {
    @Override
    public Optional<AppUserPrincipal> getLoggedUserInfo() {
        if (Objects.nonNull(SecurityContextHolder.getContext()) && Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())
                && Objects.nonNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal()) &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AppUserPrincipal) {
            return Optional.of((AppUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        return Optional.empty();
    }

    @Override
    public AppUserPrincipal getLoggedUserInfoOrThrow() {
        return this.getLoggedUserInfo()
                .orElseThrow(() -> {
                    log.error("No authenticated user!");
                    return new HospitalUnauthorizedException(ErrorType.UNAUTHORIZED);
                });
    }
}
