package org.hospital.persistence.repository;

import org.hospital.persistence.entity.TokenEntity;
import org.hospital.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<TokenEntity, Long> {
    boolean existsByToken(final String token);
    void deleteByToken(final String token);
    void deleteAllByUser(final UserEntity userEntity);
}
