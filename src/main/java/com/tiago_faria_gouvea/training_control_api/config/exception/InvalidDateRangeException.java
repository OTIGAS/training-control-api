package com.tiago_faria_gouvea.training_control_api.config.exception;

public class InvalidDateRangeException extends RuntimeException {
  public InvalidDateRangeException(String message) {
    super(message);
  }
}