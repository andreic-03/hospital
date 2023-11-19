package org.hospital.persistence.repository;

import org.hospital.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    @Query("SELECT a FROM AppointmentEntity as a WHERE a.startDate = CURRENT_DATE")
    List<AppointmentEntity> findAllAppointmentsFromToday();
}
