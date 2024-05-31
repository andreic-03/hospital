package org.hospital.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "country")
    private String country;

    @Column(name = "county")
    private String county;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "gender")
    private String gender;

    @Column(name = "cnp")
    private String cnp;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "observations")
    private String observations;

    @Column(name = "indications")
    private String indications;

    @OneToMany(mappedBy = "patient",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<AppointmentEntity> appointments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "patient_medic",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "medic_id"))
    private List<MedicEntity> medics = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
