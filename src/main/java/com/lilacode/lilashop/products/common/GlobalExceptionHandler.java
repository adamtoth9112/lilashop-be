package com.lilacode.lilashop.products.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lilacode.lilashop.products.common.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return handleException("Invalid request", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return handleException("Resource not found", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handleGenericException(Exception ex) {
        return handleException("An unexpected error occurred", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    private Mono<ResponseEntity<String>> handleException(String error, String message, HttpStatus status) {
        try {
            String responseBody = objectMapper.writeValueAsString(new ErrorResponse(error, message));
            return Mono.just(ResponseEntity.status(status).body(responseBody));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"An error occurred while processing the error response.\"}"));
        }
    }

    private record ErrorResponse(String error, String message) { }
}
