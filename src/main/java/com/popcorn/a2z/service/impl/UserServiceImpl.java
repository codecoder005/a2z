package com.popcorn.a2z.service.impl;

import com.popcorn.a2z.domain.request.SearchUsersRequest;
import com.popcorn.a2z.domain.response.UserDTO;
import com.popcorn.a2z.entity.UserEntityPK;
import com.popcorn.a2z.exception.UserNotFoundException;
import com.popcorn.a2z.repository.UserRepository;
import com.popcorn.a2z.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO getUserByUserId(Long userId) {
        log.info("UserServiceImpl::getUserByUserId");
        return userRepository.findById(new UserEntityPK(userId))
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new UserNotFoundException("No user found with userId: " + userId));
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        log.info("UserServiceImpl::getAllUsers");
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public Collection<UserDTO> searchUsers(SearchUsersRequest searchUsersRequest) {
        log.info("UserServiceImpl::searchUsers");
        return userRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, UserDTO.class))
                .toList();
    }
}
