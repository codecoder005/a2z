package com.popcorn.a2z.gqloperation;

import com.popcorn.a2z.domain.response.EnrollmentResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentMutationOperations {
    private EnrollmentResponse enroll;
}
