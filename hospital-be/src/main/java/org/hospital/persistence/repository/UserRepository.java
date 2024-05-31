package org.hospital.persistence.repository;

import org.hospital.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity findByEmail(String email);
//    Page<UserEntity> findAll(Specification spec, Pageable pageable);
}
