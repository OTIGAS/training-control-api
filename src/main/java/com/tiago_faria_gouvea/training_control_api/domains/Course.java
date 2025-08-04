package com.tiago_faria_gouvea.training_control_api.domains;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
  private Integer code;
  private String name;
  private String description;
  private Integer duration;
}
