package com.tiago_faria_gouvea.training_control_api.enums;

public enum EmployeeStatus {
  INACTIVE(0),
  ACTIVE(1);

  private final int code;

  EmployeeStatus(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static EmployeeStatus fromCode(int code) {
    return switch (code) {
      case 0 -> INACTIVE;
      case 1 -> ACTIVE;
      default -> throw new RuntimeException("Código inválido: " + code);
    };
  }
}
