package com.popcorn.a2z.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalRestAPIControllerAdvice {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ProblemDetail> handleException(Exception exception, WebRequest webRequest) {
        log.error("GlobalRestAPIControllerAdvice::handleException");
        ProblemDetail problemDetail = new ProblemDetailImpl();
        problemDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problemDetail);
    }

    protected static class ProblemDetailImpl extends ProblemDetail {

    }
}
