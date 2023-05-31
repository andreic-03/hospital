package org.patients.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "appointment_details")
    private String appointmentDetails;

    public Appointment() {}

    private Appointment(Builder source) {
        this.appointmentId = source.appointmentId;
        this.patientId = source.patientId;
        this.startDate = source.startDate;
        this.appointmentDetails = source.appointmentDetails;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setAppointmentId(Long id) {
        this.appointmentId = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getAppointmentDetails() {
        return appointmentDetails;
    }

    public void setAppointmentDetails(String appointmentDetails) {
        this.appointmentDetails = appointmentDetails;
    }

    public static class Builder {
        protected Long appointmentId;
        protected Long patientId;
        protected LocalDateTime startDate;
        protected String appointmentDetails;

        public Builder(Long patientId, Long appointmentId, LocalDateTime startDate, String appointmentDetails) {
            this.appointmentId = appointmentId;
            this.patientId = patientId;
            this.startDate = startDate;
            this.appointmentDetails = appointmentDetails;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(getAppointmentId(), that.getAppointmentId()) &&
                Objects.equals(getPatientId(), that.getPatientId()) &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getAppointmentDetails(), that.getAppointmentDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppointmentId(), getPatientId(), getStartDate(), getAppointmentDetails());
    }
}
