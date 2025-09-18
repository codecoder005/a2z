package com.popcorn.a2z.service.impl;

import com.popcorn.a2z.domain.response.UserDTO;
import com.popcorn.a2z.entity.UserEntity;
import com.popcorn.a2z.entity.UserEntityPK;
import com.popcorn.a2z.exception.UserNotFoundException;
import com.popcorn.a2z.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    @DisplayName(value = "getUserByUserId Should Return a User")
    void getUserByUserIdShouldReturnAUser() {
        UserEntityPK userEntityPK = new UserEntityPK(101L);
        UserEntity userEntity = UserEntity.builder().userEntityPK(userEntityPK).firstName("John").build();
        UserDTO userDTO = UserDTO.builder().userEntityPK(userEntityPK).firstName("John").build();

        when(userRepository.findById(any(UserEntityPK.class))).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(any(), any())).thenReturn(userDTO);
        UserDTO userByUserId = userService.getUserByUserId(101L);

        assert userByUserId != null;
        assert userByUserId.getUserEntityPK() != null;
        assert userByUserId.getUserEntityPK().getUserId() != null;
        assert userByUserId.getUserEntityPK().getUserId().equals(101L);
        assert userByUserId.getFirstName() != null;
        assert userByUserId.getFirstName().equals("John");
    }

    @Test
    @DisplayName(value = "getUserByUserId should throw UserNotFoundException")
    void getUserByUserIdShouldThrowUserNotFoundException() {
        long userId = 101L;
        when(userRepository.findById(any(UserEntityPK.class))).thenThrow(new UserNotFoundException("No user found with userId: " + userId));

        var exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUserId(101L);
        });

        assert exception != null;
        assert exception instanceof UserNotFoundException;
        assert exception.getMessage() != null;
        assert exception.getMessage().equals("No user found with userId: " + userId);
    }

    @Test
    @DisplayName(value = "getAllUsers should return all users")
    void getAllUsersShouldReturnAllUsers() {
        UserEntityPK userEntityPK = new UserEntityPK(101L);
        UserEntity userEntity = UserEntity.builder().userEntityPK(userEntityPK).firstName("John").build();
        List<UserEntity> allUserEntities = Collections.singletonList(userEntity);
        UserDTO userDTO = UserDTO.builder().userEntityPK(userEntityPK).firstName("John").build();

        when(userRepository.findAll()).thenReturn(allUserEntities);
        when(modelMapper.map(any(), any())).thenReturn(userDTO);
        List<UserDTO> allUsers = (List<UserDTO>) userService.getAllUsers();

        assert allUsers != null && allUsers.size() == 1;
        assert allUsers.getFirst() != null && allUsers.getFirst().getUserEntityPK() != null;
        assert allUsers.getFirst().getUserEntityPK().getUserId() != null;
        assert allUsers.getFirst().getUserEntityPK().getUserId().equals(101L);
        assert allUsers.getFirst().getFirstName().equals("John");
    }
}