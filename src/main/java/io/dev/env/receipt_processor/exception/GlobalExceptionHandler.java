package io.dev.env.receipt_processor.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler for handling application-wide exceptions.
 * <p>
 * This class provides centralized exception handling for the application.
 * It uses {@link RestControllerAdvice} to intercept and handle exceptions
 * thrown from REST controllers, returning appropriate HTTP responses.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions when a requested receipt is not found.
     *
     * @param ex      The exception thrown when a receipt is not found.
     * @param request The web request details.
     * @return A {@link ResponseEntity} with a 404 NOT FOUND status and an error message.
     */
    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<Object> handleReceiptNotFoundException(ReceiptNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation errors when request parameters are invalid.
     *
     * @param ex      The exception thrown when validation fails.
     * @param request The web request details.
     * @return A {@link ResponseEntity} with a 400 BAD REQUEST status and an error message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity<>("The receipt is invalid.", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions when request path parameters have invalid types.
     *
     * @param ex      The exception thrown when a path variable type is incorrect.
     * @param request The web request details.
     * @return A {@link ResponseEntity} with a 400 BAD REQUEST status and an error message.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handlePathArgValidationException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation exceptions for query parameters.
     *
     * @param ex      The exception thrown when a query parameter violates a constraint.
     * @param request The web request details.
     * @return A {@link ResponseEntity} with a 400 BAD REQUEST status and an error message.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleQueryArgValidationException(ConstraintViolationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all unexpected exceptions not explicitly handled by other methods.
     *
     * @param ex      The generic exception thrown.
     * @param request The web request details.
     * @return A {@link ResponseEntity} with a 500 INTERNAL SERVER ERROR status and an error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}