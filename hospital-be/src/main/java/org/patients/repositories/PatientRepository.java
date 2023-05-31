package org.patients.repositories;

import org.patients.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findPatientByFirstNameAndLastName(final String firstName, final String lastName);

    Patient findPatientByCnp(final Long cnp);

    @Query("SELECT p FROM Patient p JOIN p.medics m WHERE m.medicId = :medicId")
    Patient findPatientByMedicId(@Param("medicId")Long medicId);
}
