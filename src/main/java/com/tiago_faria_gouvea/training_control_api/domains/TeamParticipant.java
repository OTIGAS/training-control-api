package com.tiago_faria_gouvea.training_control_api.domains;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamParticipant {
  private Integer code;
  private Integer teamCode;
  private Team team;
  private Integer participantCode;
  private Employee participant;
}
