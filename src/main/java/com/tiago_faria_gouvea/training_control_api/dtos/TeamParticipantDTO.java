package com.tiago_faria_gouvea.training_control_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamParticipantDTO {
  @NotNull(message = "Turma é obrigatória")
  private Integer teamCode;
  @NotNull(message = "Participante é obrigatória")
  private Integer participantCode;
}
