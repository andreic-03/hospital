package org.hospital.persistence.repository;

import org.hospital.persistence.entity.MedicEntity;
import org.hospital.persistence.entity.MedicSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicRepository extends JpaRepository<MedicEntity, Long> {

    MedicEntity findMedicByFirstNameAndLastName(final String firstName, final String lastName);

    MedicEntity findMedicByCnp(final String cnp);

    List<MedicEntity> findMedicsBySpecialty(final MedicSpecialty specialty);

    @Query(value = "SELECT * FROM medic WHERE ts @@ to_tsquery('romanian', :searchTerm) = true",
            nativeQuery = true)
    List<MedicEntity> filterMedics(@Param("searchTerm") String searchTerm);
}
