package com.tiago_faria_gouvea.training_control_api.config.swagger.dtos;

import com.tiago_faria_gouvea.training_control_api.dtos.TeamDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(
    name = "Team",
    description = "Turma de treinamento associada a um curso e um grupo de funcionários"
)
public class TeamDTOSchema extends TeamDTO {
  @Schema(
      description = "Data de início do treinamento",
      example = "2025-08-01"
  )
  private LocalDate startDate;

  @Schema(
      description = "Data de término do treinamento",
      example = "2025-08-03"
  )
  private LocalDate endDate;

  @Schema(description = "Local do treinamento", example = "Sala 101 - Prédio A")
  private String location;

  @Schema(description = "Código do curso associado à turma", example = "1")
  private Integer courseCode;
}
