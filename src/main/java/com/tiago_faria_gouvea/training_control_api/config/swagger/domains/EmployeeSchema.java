package com.tiago_faria_gouvea.training_control_api.config.swagger.domains;

import com.tiago_faria_gouvea.training_control_api.enums.EmployeeStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "Employee", description = "Funcionário participante das turmas")
public class EmployeeSchema {
  @Schema(
      description = "Código do funcionário",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY
  )
  private Integer code;

  @Schema(description = "Nome completo do funcionário", example = "Ana Silva")
  private String name;

  @Schema(
      description = "CPF do funcionário (somente números)",
      example = "12345678901"
  )
  private String cpf;

  @Schema(description = "Data de nascimento", example = "1990-05-12")
  private LocalDate birthDate;

  @Schema(
      description = "Cargo ocupado pelo funcionário",
      example = "Analista de Sistemas"
  )
  private String role;

  @Schema(
      description = "Data de admissão do funcionário",
      example = "2020-03-01"
  )
  private LocalDate admission;

  @Schema(description = "Status do funcionário (ATIVO ou INATIVO)")
  private EmployeeStatus status;
}
