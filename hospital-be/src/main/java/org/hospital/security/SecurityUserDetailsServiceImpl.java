package org.hospital.security;

import lombok.AllArgsConstructor;
import org.hospital.persistence.entity.UserEntity;
import org.hospital.persistence.repository.UserRepository;
import org.hospital.security.model.AppUserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        return userEntity
                .map(AppUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
