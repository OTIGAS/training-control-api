package com.tiago_faria_gouvea.training_control_api.domains;

import com.tiago_faria_gouvea.training_control_api.enums.EmployeeStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
  private Integer code;
  private String name;
  private String cpf;
  private LocalDate birthDate;
  private String role;
  private LocalDate admission;
  private EmployeeStatus status;
}
