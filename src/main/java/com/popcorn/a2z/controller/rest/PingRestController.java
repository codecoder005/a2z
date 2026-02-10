package com.popcorn.a2z.controller.rest;

import com.popcorn.a2z.domain.response.PingAPIResponse;
import com.popcorn.a2z.restapi.PingAPI;
import com.popcorn.a2z.service.PingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PingRestController implements PingAPI {
    private final PingService pingService;

    @Override
    @SneakyThrows
    public ResponseEntity<PingAPIResponse> ping() {
        log.info("PingRestController::ping");
        var response = PingAPIResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(200)
                .message("Up and Running")
                .serverIPs(pingService.getHostIPAddresses())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
