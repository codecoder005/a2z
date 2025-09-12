package com.popcorn.a2z.controller.graphql;

import com.popcorn.a2z.domain.request.EnrollmentRequest;
import com.popcorn.a2z.domain.response.UserDTO;
import com.popcorn.a2z.gqloperation.EnrollmentMutationOperations;
import com.popcorn.a2z.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EnrollmentGraphQLController {
    private final UserService userService;

    @MutationMapping(name = "enrollments")
    public EnrollmentMutationOperations enrollmentMutation() {
        log.info("EnrollmentGraphQLController::enrollmentMutation");
        return new EnrollmentMutationOperations();
    }

    @SchemaMapping(typeName = "EnrollmentMutationOperations", field = "enroll")
    public Collection<UserDTO> enroll(@Valid @Argument EnrollmentRequest request) {
        log.info("EnrollmentGraphQLController::enroll");
        return userService.getAllUsers();
    }
}
