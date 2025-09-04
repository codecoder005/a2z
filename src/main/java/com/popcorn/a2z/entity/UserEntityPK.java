package com.popcorn.a2z.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserEntityPK implements Serializable {
    @SequenceGenerator(name = "seq_user_id_generator", initialValue = 10001, allocationSize = 1, sequenceName = "tbl_users_user_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_user_id_generator")
    private Long userId;
}
