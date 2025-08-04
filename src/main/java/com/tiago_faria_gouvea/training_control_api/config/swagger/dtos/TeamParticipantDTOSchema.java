package com.tiago_faria_gouvea.training_control_api.config.swagger.dtos;

import com.tiago_faria_gouvea.training_control_api.dtos.TeamParticipantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "TeamParticipant",
    description = "Relação entre uma turma e um funcionário participante"
)
public class TeamParticipantDTOSchema extends TeamParticipantDTO {
  @Schema(description = "Código da turma", example = "1")
  private Integer teamCode;

  @Schema(description = "Código do funcionário participante", example = "1")
  private Integer participantCode;
}
