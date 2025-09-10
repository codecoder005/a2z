package com.popcorn.a2z.domain.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponse {
    private Boolean isChallenged;
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

    private LocalDate dob;
    private LocalDate attendedOn;
    private LocalDate travellingOn;
    private LocalDate maturityDate;

    private String searchPattern;
    private List<String> papers;

    private EnrollmentAddress address;

    public enum Gender {
        MALE, FEMALE, TRANSGENDER, UNDISCLOSED
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EnrollmentAddress {
        private String addressLine1;
        private String addressLine2;
        private String landmark;
        private String city;
        private String state;
        private String country;
        private String zipcode;
        private EnrollmentAddressType addressType;
    }

    public enum EnrollmentAddressType {
        PERMANENT,
        COMMUNICATION,
        CURRENT
    }
}
