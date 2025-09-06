package com.popcorn.a2z.domain.response;

import com.popcorn.a2z.entity.AddressEntity;
import com.popcorn.a2z.entity.BankAccountEntity;
import com.popcorn.a2z.entity.UserEntity;
import com.popcorn.a2z.entity.UserEntityPK;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UserEntityPK userEntityPK;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserEntity.Gender gender;

    private AddressEntity address;
    private List<BankAccountEntity> bankAccounts;

    private String createdBy;
    private LocalDateTime createdOn;
    private String updatedBy;
    private LocalDateTime updatedOn;
}
