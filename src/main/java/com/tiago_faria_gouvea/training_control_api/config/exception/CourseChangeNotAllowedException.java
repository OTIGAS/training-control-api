package com.tiago_faria_gouvea.training_control_api.config.exception;

public class CourseChangeNotAllowedException extends RuntimeException {
  public CourseChangeNotAllowedException(String message) {
    super(message);
  }
}
