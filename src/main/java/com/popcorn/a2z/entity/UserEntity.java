package com.popcorn.a2z.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Gender {
        MALE,
        FEMALE,
        TRANSGENDER,
        UNDISCLOSED
    }
}
