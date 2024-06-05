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

    @Query("SELECT p FROM PatientEntity p JOIN p.medics m WHERE m.medicId = :medicId")
    PatientEntity findPatientByMedicId(@Param("medicId")Long medicId);
}
