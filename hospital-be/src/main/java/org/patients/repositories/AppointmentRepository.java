package org.patients.repositories;

import org.patients.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment as a WHERE a.startDate = CURRENT_DATE")
    List<Appointment> findAllAppointmentsFromToday();
}
