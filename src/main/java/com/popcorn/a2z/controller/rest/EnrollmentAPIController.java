package com.popcorn.a2z.controller.rest;

import com.popcorn.a2z.domain.request.EnrollmentRequest;
import com.popcorn.a2z.domain.response.EnrollmentResponse;
import com.popcorn.a2z.restapi.EnrollmentAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentAPIController implements EnrollmentAPI {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnrollmentResponse> enroll(@RequestBody EnrollmentRequest enrollmentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new EnrollmentResponse());
    }
}
