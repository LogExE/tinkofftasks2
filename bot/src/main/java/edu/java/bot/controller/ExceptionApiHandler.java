package edu.java.bot.controller;

import edu.java.bot.controller.dto.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ExceptionApiHandler {
    private static final String BAD_REQ_CODE = "400";

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> notFoundRes(NoResourceFoundException e) {
        return ResponseEntity
            .badRequest()
            .body(
                createErrResp("Resource not found", BAD_REQ_CODE, e)
            );
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> wrongType(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity
            .badRequest()
            .body(
                createErrResp("Unsupported media type", BAD_REQ_CODE, e)
            );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> notReadable(HttpMessageNotReadableException e) {
        return ResponseEntity
            .badRequest()
            .body(
                createErrResp("Data not readable", BAD_REQ_CODE, e)
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> internalError(Exception e) {
        return ResponseEntity
            .internalServerError()
            .body(
                createErrResp("Internal error while processing your request", "500", e)
            );
    }

    public ApiErrorResponse createErrResp(String descr, String code, Exception e) {
        return new ApiErrorResponse(
            descr,
            code,
            e.getClass().getSimpleName(),
            e.getMessage(),
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }
}
