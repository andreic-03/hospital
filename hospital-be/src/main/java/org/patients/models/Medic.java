package org.patients.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "medic")
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medic_id")
    private Long medicId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_phone")
    private Long mobilePhone;

    @Column(unique = true)
    @Email(message = "Email must be a valid email address")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @ManyToMany(mappedBy = "medics")
    private List<Patient> patients;

    public Medic() {}

    private Medic(Medic.Builder source) {
        this.medicId = source.medicId;
        this.firstName = source.firstName;
        this.lastName = source.lastName;
        this.mobilePhone = source.mobilePhone;
        this.email = source.email;
    }

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

    public static class Builder {
        protected long medicId;
        protected String firstName;
        protected String lastName;
        protected Long mobilePhone;
        protected String email;

        public Builder(Long medicId, String firstName, String lastName, Long mobilePhone, String email) {
            this.medicId = medicId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.mobilePhone = mobilePhone;
            this.email = email;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medic medic = (Medic) o;
        return Objects.equals(medicId, medic.medicId) && Objects.equals(firstName, medic.firstName) && Objects.equals(lastName, medic.lastName) && Objects.equals(mobilePhone, medic.mobilePhone) && Objects.equals(email, medic.email) && Objects.equals(patients, medic.patients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicId, firstName, lastName, mobilePhone, email, patients);
    }
}
