package org.patients.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "cnp")
    private Long cnp;

    @Column(name = "age")
    private Long age;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "mobilePhone")
    private Long mobilePhone;

    @Column(name = "observations")
    private String observations;

    @Column(name = "indications")
    private String indications;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    List<Appointment> appointments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "patient_medic",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "medic_id"))
    private List<Medic> medics;

    public Patient() {}

    private Patient(Builder source) {
        this.patientId = source.id;
        this.firstName = source.firstName;
        this.lastName = source.lastName;
        this.cnp = source.cnp;
        this.age = source.age;
        this.diagnosis = source.diagnosis;
        this.mobilePhone = source.mobilePhone;
        this.medics = source.medics;
        this.observations = source.observations;
        this.indications = source.indications;
        this.appointments = source.appointments;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long id) {
        this.patientId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getCnp() {
        return cnp;
    }

    public void setCnp(Long cnp) {
        this.cnp = cnp;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Long mobile_phone) {
        this.mobilePhone = mobile_phone;
    }

    public List<Medic> getMedics() {
        return medics;
    }

    public void setMedics(List<Medic> medics) {
        this.medics = medics;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public static class Builder {
        protected long id;
        protected String firstName;
        protected String lastName;
        protected Long cnp;
        protected Long age;
        protected String diagnosis;
        protected Long mobilePhone;
        protected List<Medic> medics;
        protected String observations;
        protected String indications;
        protected List<Appointment> appointments;

        public Builder(Long id, String firstName, String lastName, List<Appointment> appointments, Long cnp, Long age, String diagnosis,
                       Long mobilePhone, List<Medic> medics, String observations, String indications) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.cnp = cnp;
            this.age = age;
            this.diagnosis = diagnosis;
            this.mobilePhone = mobilePhone;
            this.medics = medics;
            this.observations = observations;
            this.indications = indications;
            this.appointments = appointments;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(getPatientId(), patient.getPatientId()) &&
                Objects.equals(getFirstName(), patient.getFirstName()) &&
                Objects.equals(getLastName(), patient.getLastName()) &&
                Objects.equals(getCnp(), patient.getCnp()) &&
                Objects.equals(getAge(), patient.getAge()) &&
                Objects.equals(getDiagnosis(), patient.getDiagnosis()) &&
                Objects.equals(getMobilePhone(), patient.getMobilePhone()) &&
                Objects.equals(getMedics(), patient.getMedics()) &&
                Objects.equals(getObservations(), patient.getObservations()) &&
                Objects.equals(getIndications(), patient.getIndications());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPatientId(), getFirstName(), getLastName(), getCnp(), getAge(), getDiagnosis(), getMobilePhone(), getMedics(), getObservations(), getIndications());
    }
}
