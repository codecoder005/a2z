package com.popcorn.a2z.domain.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentRequest {
    @NotNull(message = "'isChallenged' can not be null")
    @AssertFalse(message = "'isChallenged' can not be true")
    private Boolean isChallenged;

    @NotNull(message = "'isSalaried' can not be null")
    @AssertTrue(message = "'isSalaried' can not be false")
    private Boolean isSalaried;

    @NotNull(message = "'age' can not be null")
    @Min(value = 18, message = "'age' should not be less than 18")
    @Positive(message = "'age' can not be negative")
    @Digits(integer = 3, fraction = 0, message = "'age' numeric value out of bounds (<{integer} digits>.<{fraction} digits> expected)")
    private Byte age;

    @NotNull(message = "'height' can not be null")
    @Min(value = 165, message = "'height' should not be less than 165cm")
    @Max(value = 250, message = "'height' should not be more than 250cm")
    @Digits(integer = 3, fraction = 0, message = "'height' numeric value out of bounds (<{integer} digits>.<{fraction} digits> expected)")
    private Short height;

    @NotNull(message = "'weight' can not be null")
    @DecimalMin(value = "65.0", message = "'weight' should not be less than 65.0 kg")
    @DecimalMax(value = "150.0", message = "'weight' should not be more than 150.0 kg")
    @Digits(integer = 3, fraction = 3, message = "'weight' numeric value out of bounds (<{integer} digits>.<{fraction} digits> expected)")
    private Float weight;

    @NotNull(message = "'salary' can not be null")
    @DecimalMin(value = "10000.0", message = "'salary' should not be less than $10_000")
    private Double salary;

    @NotNull(message = "'revenue' can not be null")
    @DecimalMin(value = "1000000.0", message = "'revenue' should not be less than $1_000_000")
    private BigDecimal revenue;

    @NotNull(message = "'email' can not be null")
    @Email(message =  "'email' is invalid")
    private String email;

    @NotNull(message = "'username' can not be null")
    @NotEmpty(message = "'username' can not be empty")
    private String username;

    @NotNull(message = "'password' can not be null")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "'password' must be at least 8 characters long, contain upper & lowercase letters, a number, and a special character"
    )
    private String password;

    @NotNull(message = "'firstName' can not be null")
    private String firstName;
    private String lastName;
    private Gender gender;

    @NotNull(message = "'dob' can not be null")
    @Past(message = "'dob' can not be present or future date")
    private LocalDate dob;

    @NotNull(message = "'attendedOn' can not be null")
    @PastOrPresent(message = "'attendedOn' can not be a future date")
    private LocalDate attendedOn;

    @NotNull(message = "'travellingOn' can not be null")
    @FutureOrPresent(message = "'travellingOn' can not be a back date")
    private LocalDate travellingOn;

    @NotNull(message = "'maturityDate' can not be null")
    @Future(message = "'maturityDate' should be a future date")
    private LocalDate maturityDate;

    @NotNull(message = "'searchPattern' can not be null")
    @Pattern(regexp = ".*", message = "'searchPattern' is invalid")
    private String searchPattern;

    @NotNull(message = "'papers' can not be null")
    @NotEmpty(message = "'papers' can not be empty")
    @Size(min = 1, message = "'papers' can not be empty. min 1 required")
    private List<String> papers;

    @NotNull(message = "'address' can not be null")
    @Valid
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
        @NotNull(message = "'addressLine1' can not be null")
        @NotEmpty(message = "'addressLine1' can not be empty")
        @NotBlank(message = "'addressLine1' can not be blank")
        private String addressLine1;

        private String addressLine2;
        private String landmark;

        @NotNull(message = "'city' can not be null")
        @NotEmpty(message = "'city' can not be empty")
        @NotBlank(message = "'city' can not be blank")
        private String city;

        @NotNull(message = "'state' can not be null")
        @NotEmpty(message = "'state' can not be empty")
        @NotBlank(message = "'state' can not be blank")
        private String state;

        @NotNull(message = "'country' can not be null")
        @NotEmpty(message = "'country' can not be empty")
        @NotBlank(message = "'country' can not be blank")
        private String country;

        @NotNull(message = "'zipcode' can not be null")
        @NotEmpty(message = "'zipcode' can not be empty")
        @NotBlank(message = "'zipcode' can not be blank")
        private String zipcode;

        private EnrollmentAddressType addressType;
    }

    public enum EnrollmentAddressType {
        PERMANENT,
        COMMUNICATION,
        CURRENT
    }
}
