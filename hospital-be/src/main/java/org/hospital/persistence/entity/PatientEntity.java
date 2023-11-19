package org.hospital.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "patient")
@Getter
@Setter
@NoArgsConstructor
public class PatientEntity extends AuditingEntity {

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
    List<AppointmentEntity> appointments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "patient_medic",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "medic_id"))
    private List<MedicEntity> medics;

}
