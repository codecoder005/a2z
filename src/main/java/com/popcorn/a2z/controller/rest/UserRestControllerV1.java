package com.popcorn.a2z.controller.rest;

import com.popcorn.a2z.domain.request.SearchUsersRequest;
import com.popcorn.a2z.domain.response.UserDTO;
import com.popcorn.a2z.restapi.UserRestAPI;
import com.popcorn.a2z.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestControllerV1 implements UserRestAPI {
    private final UserService userService;

    @Override
    public ResponseEntity<UserDTO> getUserByUserId(Long userId) {
        log.info("UserRestControllerV1::getUserByUserId");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getUserByUserId(userId));
    }

    @Override
    public ResponseEntity<Collection<UserDTO>> getAllUsers() {
        log.info("UserRestControllerV1::getAllUsers");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<Collection<UserDTO>> searchUsers(SearchUsersRequest searchUsersRequest) {
        log.info("UserRestControllerV1::searchUsers");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.searchUsers(searchUsersRequest));
    }
}
