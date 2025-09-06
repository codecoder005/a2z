package com.popcorn.a2z.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_address")
@EntityListeners(AuditingEntityListener.class)
public class AddressEntity extends AuditingBaseEntity{
    @EmbeddedId
    private UserEntityPK userEntityPK;

    @OneToOne
    @MapsId // ensures AddressEntity uses the same PK as UserEntity
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    private String city;
    private String state;
    private String country;
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    public enum AddressType {
        PERMANENT,
        COMMUNICATION,
        CURRENT
    }
}
