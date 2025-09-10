package com.popcorn.a2z.restapi;

import com.popcorn.a2z.domain.request.EnrollmentRequest;
import com.popcorn.a2z.domain.response.EnrollmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface EnrollmentAPI {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EnrollmentResponse> enroll(@Valid @RequestBody EnrollmentRequest enrollmentRequest);
}
