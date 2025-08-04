package com.tiago_faria_gouvea.training_control_api.config.swagger.domains;

import com.tiago_faria_gouvea.training_control_api.config.swagger.dtos.TeamDTOSchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "Team",
    description = "Turma de treinamento associada a um curso e um grupo de funcionários"
)
public class TeamSchema extends TeamDTOSchema {
  @Schema(
      description = "Código da turma",
      example = "10",
      accessMode = Schema.AccessMode.READ_ONLY
  )
  private Integer code;

  @Schema(description = "Curso associado à turma")
  private CourseSchema course;
}
