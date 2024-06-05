package org.hospital.configuration;

import org.hospital.persistence.entity.UserEntity;
import org.hospital.security.model.AppUserPrincipal;
import org.hospital.services.auth.AppUserPrincipalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
@Configuration
public class JpaConfiguration {

    @Bean
    AuditorAware<UserEntity> auditorAware(final AppUserPrincipalService appUserPrincipalService) {
        return () -> appUserPrincipalService.getLoggedUserInfo().map(AppUserPrincipal::getUserEntity);
    }
}
