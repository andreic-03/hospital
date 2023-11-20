package org.hospital.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "medic")
@Getter
@Setter
@NoArgsConstructor
public class MedicEntity extends AuditingEntity {
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
    private List<PatientEntity> patients;

}
