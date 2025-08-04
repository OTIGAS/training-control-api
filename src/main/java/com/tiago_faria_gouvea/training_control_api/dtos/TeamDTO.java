package com.tiago_faria_gouvea.training_control_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
  @NotNull(message = "Data de início é obrigatória")
  private LocalDate startDate;
  @NotNull(message = "Data de término é obrigatória")
  private LocalDate endDate;
  @NotNull(message = "Localização é obrigatória")
  private String location;
  @NotNull(message = "Código do curso é obrigatório")
  private Integer courseCode;
}
