package com.popcorn.a2z.advice;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalRestAPIControllerAdvice {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ProblemDetail> handleException(Exception exception, WebRequest webRequest) {
        log.error("GlobalRestAPIControllerAdvice::handleException message= {}", exception.getMessage());
        exception.printStackTrace();
        ProblemDetail problemDetail = new ProblemDetailImpl();
        problemDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setDetail(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problemDetail);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException exception) {
        log.error("GlobalRestAPIControllerAdvice::BindException {}", exception.getMessage());
        ProblemDetailImpl problemDetail = new ProblemDetailImpl();
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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problemDetail);
    }

    @Getter
    @Setter
    protected static class ProblemDetailImpl extends ProblemDetail {
        private String message;
    }
}
