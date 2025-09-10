package com.popcorn.a2z.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "tbl_bank_account",
        uniqueConstraints = @UniqueConstraint(name = "UK_account_no", columnNames = {"accountNo"}),
        indexes = @Index(name = "IDX_account_no", columnList = "accountNo")
)
@EntityListeners(AuditingEntityListener.class)
public class BankAccountEntity extends AuditingBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountUuid;

    @Column(nullable = false)
    private String accountNo;
    private String ifsc;
    @Column(nullable = false)
    private String nameAsPerBankAccount;
    private String bankName;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private BankAccountType bankAccountType;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    })
    @JsonIgnore
    private UserEntity user;

    public enum BankAccountType {
        BASIC_SAVINGS,
        SAVINGS,
        CURRENT,
        SALARY,
        LOAN,
        OVERDRAFT
    }
}
