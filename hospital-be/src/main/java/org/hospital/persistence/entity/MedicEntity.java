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

    @Column
    private String gender;

    @Column(name = "specialization", nullable = false)
    @Enumerated(EnumType.STRING)
    private MedicSpecialization specialization;

    @ManyToMany(mappedBy = "medics")
    private List<PatientEntity> patients;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
