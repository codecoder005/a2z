package com.popcorn.a2z.controller.rest;

import com.popcorn.a2z.domain.response.JsonPostDTO;
import com.popcorn.a2z.service.impl.JsonPlaceholderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/json-placeholder")
@Slf4j
public class JsonPlaceholderController {
    private final JsonPlaceholderServiceImpl jsonPlaceholderService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<JsonPostDTO>> getAllPosts() {
        log.info("JsonPlaceholderController::getAllPosts");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonPlaceholderService.getAllPosts());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonPostDTO> getPostById(@PathVariable(name = "id") Long id) {
        log.info("JsonPlaceholderController::getPostById");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonPlaceholderService.getPostById(id));
    }
}
