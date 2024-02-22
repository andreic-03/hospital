package org.hospital.persistence.repository;

import org.hospital.persistence.entity.RegisterAccountTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RegisterAccountTokenRepository extends CrudRepository<RegisterAccountTokenEntity, Long> {

    Optional<RegisterAccountTokenEntity> findByToken(final String token);
    void deleteByCreatedOnBefore(final LocalDateTime localDateTime);
}
