package com.popcorn.a2z.advice;

import com.google.gson.Gson;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GraphQLSpecificExceptionHandler {
    private final Gson jsonHelper;

    @GraphQlExceptionHandler(BindException.class)
    public GraphQLError handleBindException(BindException exception) {
        log.error("GraphQLSpecificExceptionHandler::handleBindException {}", exception.getMessage());
        GlobalRestAPIControllerAdvice.ProblemDetailImpl problemDetail = new GlobalRestAPIControllerAdvice.ProblemDetailImpl();
        Map<String, Object> errors = new LinkedHashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exception.getMessage()); // One or more fields are failing validation criteria
        problemDetail.setProperties(errors);
        problemDetail.setMessage("One or more fields are failing validation criteria");

        return GraphqlErrorBuilder.newError()
                .message("One or more fields are failing validation criteria: " + jsonHelper.toJson(problemDetail))
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }

    @GraphQlExceptionHandler(ValidationException.class)
    public GraphQLError handleValidationException(ValidationException exception) {
        log.error("GraphQLSpecificExceptionHandler::handleValidationException {}", exception.getMessage());
        GlobalRestAPIControllerAdvice.ProblemDetailImpl problemDetail = new GlobalRestAPIControllerAdvice.ProblemDetailImpl();
        Map<String, Object> errors = new LinkedHashMap<>();

        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exception.getMessage()); // One or more fields are failing validation criteria
        problemDetail.setProperties(errors);
        problemDetail.setMessage("One or more fields are failing validation criteria");

        return GraphqlErrorBuilder.newError()
                .message("One or more fields are failing validation criteria: " + jsonHelper.toJson(problemDetail))
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }
}
