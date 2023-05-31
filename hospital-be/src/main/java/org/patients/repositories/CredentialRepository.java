package org.patients.repositories;

import org.patients.models.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Credential findByUsername(final String username);
}
