package com.popcorn.a2z.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends AuditingBaseEntity{
    @EmbeddedId
    private UserEntityPK userEntityPK;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private AddressEntity address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<BankAccountEntity> bankAccounts;

    public enum Gender {
        MALE,
        FEMALE,
        TRANSGENDER,
        UNDISCLOSED
    }
}
