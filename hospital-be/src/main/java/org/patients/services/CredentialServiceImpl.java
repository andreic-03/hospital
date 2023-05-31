package org.patients.services;

import org.patients.models.Credential;
import org.patients.repositories.CredentialRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {
    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Credential> findAll() {
            return credentialRepository.findAll();
    }

    @Override
    public Credential findById(final Long id) {
        return credentialRepository.findById(id).orElseThrow(() -> {
            System.out.println("NOT FOUND...");
            return new RuntimeException("Credential not found");
        });
    }

    @Override
    public Credential findByUsername(String username) {
        return credentialRepository.findByUsername(username);
    }

    @Override
    public Credential create(final Credential credential) {
        if(credential.getId() != null) {
            System.out.println("Id must be null");
            throw new RuntimeException("Id must be null");
        }
        return credentialRepository.saveAndFlush(credential);
    }

    @Override
    public void deleteById(final Long id) {
        credentialRepository.deleteById(id);
    }

    @Override
    public Credential update(Credential credential) {
        Credential existingCredential = credentialRepository.findById(credential.getId())
                .orElseThrow(() -> {
                    System.out.println("NOT FOUND...");
                    return new RuntimeException("Credential not found");
                });

        BeanUtils.copyProperties(credential, existingCredential);
        return credentialRepository.saveAndFlush(existingCredential);
    }

    @Override
    public Credential registerNewUserAccount(Credential credential) {

        credential.setUserId(credential.getUserId());
        credential.setUsername(credential.getUsername());
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));

        return credentialRepository.saveAndFlush(credential);
    }
}
