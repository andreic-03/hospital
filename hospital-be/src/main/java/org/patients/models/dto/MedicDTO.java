package org.patients.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicDTO {
    private Long medicId;
    private String firstName;
    private String lastName;
    private Long mobilePhone;
    private String email;

    public Long getMedicId() {
        return medicId;
    }

    public void setMedicId(Long medicId) {
        this.medicId = medicId;
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

    public Long getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicDTO medicDTO = (MedicDTO) o;
        return Objects.equals(medicId, medicDTO.medicId) && Objects.equals(firstName, medicDTO.firstName) && Objects.equals(lastName, medicDTO.lastName) && Objects.equals(mobilePhone, medicDTO.mobilePhone) && Objects.equals(email, medicDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicId, firstName, lastName, mobilePhone, email);
    }
}
