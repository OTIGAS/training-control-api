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
public class CourseDTO {
  @NotNull(message = "Nome é obrigatório")
  private String name;
  @NotNull(message = "Descrição é obrigatória")
  private String description;
  @NotNull(message = "Duração é obrigatório")
  private Integer duration;
}
