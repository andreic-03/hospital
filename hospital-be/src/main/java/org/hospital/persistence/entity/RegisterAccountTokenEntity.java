package org.hospital.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "register_account_token")
@Getter
@Setter
@NoArgsConstructor
public class RegisterAccountTokenEntity extends AuditingEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "token")
    private String token;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @Column(name = "used")
    private Boolean used;
}
