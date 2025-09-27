package com.popcorn.a2z.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "nano-service-client",
        url = "http://localhost:8081",
        path = "/api/v1/orders"
)
public interface NanoServiceClient {
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> placeOrder(@RequestBody Map<String, Object> orderRequest);
}
