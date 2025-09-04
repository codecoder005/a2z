package com.popcorn.a2z.restapi;

import com.popcorn.a2z.domain.response.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

public interface UserRestAPI {
    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> getUserByUserId(@PathVariable(name = "userId") Long userId);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Collection<UserDTO>> getAllUsers();
}
