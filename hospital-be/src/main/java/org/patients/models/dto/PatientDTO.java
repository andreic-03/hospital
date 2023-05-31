package org.patients.models.dto;

import java.util.List;
import java.util.Objects;

public class PatientDTO {
    private Long patientId;
    private String firstName;
    private String lastName;
    private Long cnp;
    private Long age;
    private String diagnosis;
    private Long mobilePhone;
    private String observations;
    private String indications;
    private List<MedicDTO> medics;

    private List<AppointmentDTO> appointments;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
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

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public List<MedicDTO> getMedics() {
        return medics;
    }

    public void setMedics(List<MedicDTO> medics) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDTO that = (PatientDTO) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(cnp, that.cnp) && Objects.equals(age, that.age) && Objects.equals(diagnosis, that.diagnosis) && Objects.equals(mobilePhone, that.mobilePhone) && Objects.equals(observations, that.observations) && Objects.equals(indications, that.indications) && Objects.equals(medics, that.medics) && Objects.equals(appointments, that.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, firstName, lastName, cnp, age, diagnosis, mobilePhone, observations, indications, medics, appointments);
    }
}
