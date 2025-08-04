package com.tiago_faria_gouvea.training_control_api.config.exception;

import com.tiago_faria_gouvea.training_control_api.dtos.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(errorResponse(ex, HttpStatus.NOT_FOUND));
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<Object> handleConflict(ConflictException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(errorResponse(ex, HttpStatus.CONFLICT));
  }

  @ExceptionHandler(InvalidDateRangeException.class)
  public ResponseEntity<Object> handleInvalidDateRange(
      InvalidDateRangeException ex
  ) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(errorResponse(ex, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(CourseChangeNotAllowedException.class)
  public ResponseEntity<Object> handleCourseChangeNotAllowed(
      CourseChangeNotAllowedException ex
  ) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(errorResponse(ex, HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleRuntime(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(errorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR));
  }

  private ExceptionResponseDTO errorResponse(Exception ex, HttpStatus status) {
    return ExceptionResponseDTO.builder()
        .timestamp(LocalDateTime.now())
        .status(status.value())
        .error(status.getReasonPhrase())
        .message(ex.getMessage())
        .build();
  }
}
