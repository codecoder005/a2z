package com.popcorn.a2z.restapi;

import com.popcorn.a2z.domain.request.EnrollmentRequest;
import com.popcorn.a2z.domain.response.EnrollmentResponse;
import com.popcorn.a2z.search.SortingOrder;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface EnrollmentAPI {
    @PostMapping(value = "/{countryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EnrollmentResponse> enroll(
            @PathVariable(name = "countryId") String countryId,
            @RequestParam(name = "sortBy") String sortBy,
            @RequestParam(name = "sortingOrder", required = false) SortingOrder sortingOrder,
            @RequestParam(name = "gender") EnrollmentRequest.Gender gender,
            @RequestHeader(name = "client-id") String clientId,
            @RequestHeader(name = "ip-address", required = false) String ipAddress,
            @Valid @RequestBody EnrollmentRequest enrollmentRequest
    );
}
