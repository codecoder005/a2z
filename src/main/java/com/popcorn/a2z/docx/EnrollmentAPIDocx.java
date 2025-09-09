package com.popcorn.a2z.docx;

import com.popcorn.a2z.domain.request.EnrollmentRequest;
import com.popcorn.a2z.domain.response.EnrollmentResponse;
import com.popcorn.a2z.restapi.EnrollmentAPI;
import org.springframework.http.ResponseEntity;

public interface EnrollmentAPIDocx extends EnrollmentAPI {
    @Override
    ResponseEntity<EnrollmentResponse> enroll(EnrollmentRequest enrollmentRequest);
}
