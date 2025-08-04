package com.tiago_faria_gouvea.training_control_api.config.exception;

public class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}
