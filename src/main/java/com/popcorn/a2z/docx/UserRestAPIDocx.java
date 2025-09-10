package com.popcorn.a2z.docx;

import com.popcorn.a2z.domain.request.SearchUsersRequest;
import com.popcorn.a2z.domain.response.UserDTO;
import com.popcorn.a2z.restapi.UserRestAPI;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface UserRestAPIDocx extends UserRestAPI {
    ResponseEntity<Collection<UserDTO>> getAllUsers();

    ResponseEntity<UserDTO> getUserByUserId(Long userId);

    ResponseEntity<Collection<UserDTO>> searchUsers(SearchUsersRequest searchUsersRequest);
}
