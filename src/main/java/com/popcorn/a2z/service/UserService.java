package com.popcorn.a2z.service;

import com.popcorn.a2z.domain.response.UserDTO;
import com.popcorn.a2z.exception.UserNotFoundException;

import java.util.Collection;

public interface UserService {
    UserDTO getUserByUserId(Long userId) throws UserNotFoundException;

    Collection<UserDTO> getAllUsers();
}
