package org.hospital.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @Column(name = "gender")
    private String gender;

    @Column(name = "cnp")
    private String cnp;

    @Column(name = "specialty", nullable = false)
    @Enumerated(EnumType.STRING)
    private MedicSpecialty specialty;

    @ManyToMany(mappedBy = "medics")
    private List<PatientEntity> patients = new ArrayList<>();

    @OneToMany(mappedBy = "medic",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<AppointmentEntity> appointments = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
