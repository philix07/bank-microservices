package com.felix.loans.exception;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  // Handle ResourceNotFoundException (for resource that is not exists within the server)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  // Handle ConstraintViolationException (for @RequestParam, @PathVariable validation)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
    List<String> violations = ex.getConstraintViolations()
      .stream()
      .map(ConstraintViolation::getMessage)
      .toList();

    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(),
      LocalDateTime.now(),
      "ConstraintViolationException",
      violations.toString(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.badRequest().body(errorResponse);
  }

  // Handle MethodArgumentNotValidException (for @Valid on DTOs)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
    // Getting all validation error messages
    List<String> errorMessages = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .toList();

    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(),
      LocalDateTime.now(),
      "Validation Exception",
      String.join(", ", errorMessages),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(LoanAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleLoadAlreadyExistsException(LoanAlreadyExistsException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.CONFLICT.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

}
