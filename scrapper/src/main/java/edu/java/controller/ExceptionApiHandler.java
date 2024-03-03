package edu.java.controller;

import edu.java.controller.dto.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> err(Exception e) {
        return ResponseEntity
            .badRequest()
            .body(new ApiErrorResponse(
                "Error while processing your request",
                "400",
                e.getClass().getSimpleName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()
            ));
    }
}
