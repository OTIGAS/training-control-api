package com.tiago_faria_gouvea.training_control_api.domains;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {
  private Integer code;
  private LocalDate startDate;
  private LocalDate endDate;
  private String location;
  private Integer courseCode;
  private Course course;
}
