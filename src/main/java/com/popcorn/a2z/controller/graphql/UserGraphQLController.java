package com.popcorn.a2z.controller.graphql;

import com.popcorn.a2z.domain.request.SearchUsersRequest;
import com.popcorn.a2z.domain.response.UserDTO;
import com.popcorn.a2z.gqloperation.UserMutationOperations;
import com.popcorn.a2z.gqloperation.UserQueryOperations;
import com.popcorn.a2z.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserGraphQLController {
    private final UserService userService;

    @QueryMapping(name = "users")
    public UserQueryOperations usersQuery() {
        log.info("UserGraphQLController::usersQuery");
        return new UserQueryOperations();
    }

    @MutationMapping(name = "users")
    public UserMutationOperations usersMutation() {
        log.info("UserGraphQLController::usersMutation");
        return new UserMutationOperations();
    }

    @SchemaMapping(typeName = "UserQueryOperations", field = "getAllUsers")
    public Collection<UserDTO> getAllUsers() {
        log.info("UserGraphQLController::getAllUsers");
        return userService.getAllUsers();
    }
    @SchemaMapping(typeName = "UserQueryOperations", field = "getUserByUserId")
    public UserDTO getUserByUserId(@Argument Long userId) {
        log.info("UserGraphQLController::getUserByUserId");
        return userService.getUserByUserId(userId);
    }
    @SchemaMapping(typeName = "UserQueryOperations", field = "searchUsers")
    public Collection<UserDTO> searchUsers(@Argument SearchUsersRequest searchUsersRequest) {
        log.info("UserGraphQLController::searchUsers");
        return userService.searchUsers(searchUsersRequest);
    }
}
