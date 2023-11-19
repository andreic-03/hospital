package org.hospital.persistence.repository;

import org.hospital.persistence.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    PatientEntity findPatientByFirstNameAndLastName(final String firstName, final String lastName);

    PatientEntity findPatientByCnp(final Long cnp);

    @Query("SELECT p FROM PatientEntity p JOIN p.medics m WHERE m.medicId = :medicId")
    PatientEntity findPatientByMedicId(@Param("medicId")Long medicId);
}
