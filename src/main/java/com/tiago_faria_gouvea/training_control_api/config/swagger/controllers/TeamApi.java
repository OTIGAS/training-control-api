package com.tiago_faria_gouvea.training_control_api.config.swagger.controllers;

import com.tiago_faria_gouvea.training_control_api.domains.Team;
import com.tiago_faria_gouvea.training_control_api.dtos.TeamDTO;
import com.tiago_faria_gouvea.training_control_api.config.swagger.domains.TeamSchema;
import com.tiago_faria_gouvea.training_control_api.config.swagger.dtos.TeamDTOSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@Tag(
    name = "Turma",
    description = "Endpoint para gerenciamento de turmas de treinamento"
)
public interface TeamApi {
  @Operation(
      summary = "Cria uma nova turma de treinamento",
      requestBody = @RequestBody(
          description = "Dados da turma a ser criada",
          content = @Content(
              schema = @Schema(implementation = TeamDTOSchema.class)
          )
      )
  )
  @ApiResponse(responseCode = "201")
  @PostMapping
  ResponseEntity<Void> save(@RequestBody TeamDTO teamDTO);

  @Operation(
      summary = "Atualiza uma turma de treinamento existente",
      requestBody = @RequestBody(
          description = "Dados da turma a ser atualizado",
          content = @Content(
              schema = @Schema(implementation = TeamDTOSchema.class)
          )
      )
  )
  @ApiResponse(responseCode = "200")
  @PutMapping("/{code}")
  ResponseEntity<Void> update(
      @PathVariable("code") Integer teamCode,
      @RequestBody TeamDTO teamDTO
  );

  @Operation(summary = "Exclui uma turma de treinamento")
  @ApiResponse(responseCode = "200")
  @DeleteMapping("/{code}")
  ResponseEntity<Void> delete(@PathVariable("code") Integer teamCode);

  @Operation(summary = "Lista turmas de treinamento por código do curso")
  @ApiResponse(
      responseCode = "200",
      content = @Content(array = @ArraySchema(
          schema = @Schema(implementation = TeamSchema.class)
      ))
  )
  @GetMapping("/course-code/{courseCode}")
  ResponseEntity<List<Team>> findByCourseCode(
      @PathVariable("courseCode") Integer courseCode
  );
}
