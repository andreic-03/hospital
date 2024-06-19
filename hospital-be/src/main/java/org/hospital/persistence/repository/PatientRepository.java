package org.hospital.persistence.repository;

import org.hospital.persistence.entity.PatientEntity;
import org.hospital.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    PatientEntity findPatientByFirstNameAndLastName(final String firstName, final String lastName);

    PatientEntity findPatientByCnp(final String cnp);

    @Query("SELECT p FROM PatientEntity p WHERE p.createdBy = :user ORDER BY p.createdOn DESC")
    List<PatientEntity> findPatientByCreatedBy(@Param("user") final UserEntity user);

    List<PatientEntity> findByMedicsMedicId(@Param("medicId")Long medicId);

    @Query(value = "SELECT * FROM patient WHERE ts @@ to_tsquery('romanian', :searchTerm) = true",
            nativeQuery = true)
    List<PatientEntity> filterPatients(@Param("searchTerm") String searchTerm);
}
