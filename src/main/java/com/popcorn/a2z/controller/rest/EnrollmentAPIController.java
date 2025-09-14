package com.popcorn.a2z.controller.rest;

import com.popcorn.a2z.docx.EnrollmentAPIDocx;
import com.popcorn.a2z.domain.request.EnrollmentRequest;
import com.popcorn.a2z.domain.response.EnrollmentResponse;
import com.popcorn.a2z.restapi.EnrollmentAPI;
import com.popcorn.a2z.search.SortingOrder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentAPIController implements EnrollmentAPI, EnrollmentAPIDocx {
    private final ModelMapper modelMapper;

    @PostMapping(value = "/{countryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnrollmentResponse> enroll(String countryId, String sortBy, SortingOrder sortingOrder, EnrollmentRequest.Gender gender, String clientId, String ipAddress, @Valid @RequestBody EnrollmentRequest enrollmentRequest) {
        log.info("EnrollmentAPIController::enroll {}", countryId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(modelMapper.map(enrollmentRequest, EnrollmentResponse.class));
    }
}
