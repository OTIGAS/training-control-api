package com.tiago_faria_gouvea.training_control_api.config.swagger.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "CourseTO", description = "Representa um curso de treinamento")
public class CourseDTOSchema {
  @Schema(description = "Nome do curso", example = "Java Básico")
  private String name;

  @Schema(
      description = "Descrição resumida do conteúdo do curso",
      example = "Curso introdutório de Java para iniciantes"
  )
  private String description;

  @Schema(description = "Duração do curso em minutos", example = "90")
  private Integer duration;
}
