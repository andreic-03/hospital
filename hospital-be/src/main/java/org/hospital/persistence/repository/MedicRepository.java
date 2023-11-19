package org.hospital.persistence.repository;

import org.hospital.persistence.entity.MedicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicRepository extends JpaRepository<MedicEntity, Long> {

}
