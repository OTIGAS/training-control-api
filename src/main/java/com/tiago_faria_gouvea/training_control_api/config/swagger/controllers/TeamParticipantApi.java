package com.tiago_faria_gouvea.training_control_api.config.swagger.controllers;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;
import com.tiago_faria_gouvea.training_control_api.dtos.TeamParticipantDTO;
import com.tiago_faria_gouvea.training_control_api.config.swagger.dtos.TeamParticipantDTOSchema;
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
@RequestMapping("/team-participants")
@Tag(
    name = "Participantes",
    description = "Endpoint para gerenciamento de participantes de turma"
)
public interface TeamParticipantApi {
  @Operation(
      summary = "Cria um novo participante para a turma",
      requestBody = @RequestBody(
          description = "Dados do participante e turma a ser criado",
          content = @Content(
              schema = @Schema(implementation = TeamParticipantDTOSchema.class)
          )
      )
  )
  @ApiResponse(responseCode = "201")
  @PostMapping
  ResponseEntity<Void> save(@RequestBody TeamParticipantDTO teamParticipantDTO);

  @Operation(summary = "Exclui um participante da turma")
  @ApiResponse(responseCode = "200")
  @DeleteMapping("/{code}")
  ResponseEntity<Void> delete(@PathVariable("code") Integer teamParticipantCode);

  @Operation(
      summary = "Lista os participantes de uma turma pelo código da turma",
      responses = @ApiResponse(
          responseCode = "200",
          content = @Content(
              array = @ArraySchema(
                  schema = @Schema(implementation = Employee.class)
              )
          )
      )
  )
  @GetMapping("/team-code/{teamCode}")
  ResponseEntity<List<Employee>> findByCourseCode(
      @PathVariable("teamCode") Integer courseCode
  );
}
