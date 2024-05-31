package org.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;


import static org.mockito.Mockito.mock;

@Configuration
public class RepositoryConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        return mock(JavaMailSender.class);
    }

}
