package com.tiago_faria_gouvea.training_control_api.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponseDTO {
  private LocalDateTime timestamp;
  private Integer status;
  private String error;
  private String message;
}