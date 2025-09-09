package com.popcorn.a2z.domain.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentRequest {
    private Byte age;
    private Short height;
    private Float weight;
    private Double salary;
    private BigDecimal revenue;

    private String email;
    private String username;
    private String password;

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate doj;

    public enum Gender {
        MALE, FEMALE, TRANSGENDER, UNDISCLOSED
    }
}
