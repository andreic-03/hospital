package org.patients.services;


import org.patients.models.Credential;

import java.util.List;

public interface CredentialService {
    List<Credential> findAll();

    Credential findById(final Long id);

    Credential findByUsername(final String username);

    Credential create(final Credential credential);

    void deleteById(final Long id);

    Credential update(final Credential credential);

    Credential registerNewUserAccount(Credential credential);
}
