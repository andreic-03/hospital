package org.hospital.config;

import org.hospital.security.JwtTokenUtil;
import org.hospital.services.internationalization.TranslateService;
import org.hospital.services.security.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;


import static org.mockito.Mockito.mock;

@Configuration
@Profile("controller-test")
public class ControllerConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        return mock(JavaMailSender.class);
    }

    @Bean
    public TranslateService translateService() {
        return mock(TranslateService.class);
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return mock(JwtTokenUtil.class);
    }

    @Bean
    public TokenService tokenService() {
        return mock(TokenService.class);
    }

}
