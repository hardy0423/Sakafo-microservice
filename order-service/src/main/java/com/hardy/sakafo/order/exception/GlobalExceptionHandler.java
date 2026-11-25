package com.hardy.sakafo.order.exception;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderNotFound(
            OrderNotFoundException exception
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFound(
            CustomerNotFoundException exception
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFound( ProductNotFoundException exception) {
        return buildResponse( HttpStatus.NOT_FOUND, exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors( MethodArgumentNotValidException exception ) {
        Map<String, Object> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Validation failed",
                "messages", errors
        ));
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Map<String, Object>> handleFeignNotFound( FeignException.NotFound exception) {
        return buildResponse( HttpStatus.NOT_FOUND,"The requested customer or product was not found in the corresponding service."
        );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException( FeignException exception ) {
        return buildResponse(  HttpStatus.BAD_GATEWAY,"An error occurred while communicating with another microservice."
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound( EntityNotFoundException exception ) {
        return buildResponse( HttpStatus.NOT_FOUND, exception.getMessage()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation( ConstraintViolationException exception) {
        return buildResponse( HttpStatus.BAD_REQUEST, exception.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception exception
    ) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred."
        );
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String message
    ) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        ));
    }
}