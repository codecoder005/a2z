package com.popcorn.a2z.controller.rest;

import com.popcorn.a2z.domain.response.PingAPIResponse;
import com.popcorn.a2z.restapi.PingAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class PingRestController implements PingAPI {
    @Override
    public ResponseEntity<PingAPIResponse> ping() {
        log.info("PingRestController::ping");
        var response = PingAPIResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Up and Running")
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
