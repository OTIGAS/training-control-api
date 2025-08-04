package com.tiago_faria_gouvea.training_control_api.config.swagger.domains;

import com.tiago_faria_gouvea.training_control_api.config.swagger.dtos.CourseDTOSchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Course", description = "Representa um curso de treinamento")
public class CourseSchema extends CourseDTOSchema {
  @Schema(
      description = "Código único do curso",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY
  )
  private Integer code;
}
