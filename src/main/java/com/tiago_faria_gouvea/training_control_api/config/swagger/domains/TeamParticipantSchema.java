package com.tiago_faria_gouvea.training_control_api.config.swagger.domains;

import com.tiago_faria_gouvea.training_control_api.config.swagger.dtos.TeamParticipantDTOSchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "TeamParticipant",
    description = "Relação entre uma turma e um funcionário participante"
)
public class TeamParticipantSchema extends TeamParticipantDTOSchema {
  @Schema(
      description = "Código do registro de participação",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY
  )
  private Integer code;

  @Schema(description = "Turma associada")
  private TeamSchema team;

  @Schema(description = "Funcionário participante")
  private EmployeeSchema participant;
}
