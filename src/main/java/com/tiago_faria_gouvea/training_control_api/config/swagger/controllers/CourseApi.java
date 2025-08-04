package com.tiago_faria_gouvea.training_control_api.config.swagger.controllers;

import com.tiago_faria_gouvea.training_control_api.domains.Course;
import com.tiago_faria_gouvea.training_control_api.dtos.CourseDTO;
import com.tiago_faria_gouvea.training_control_api.config.swagger.domains.CourseSchema;
import com.tiago_faria_gouvea.training_control_api.config.swagger.dtos.CourseDTOSchema;
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
@RequestMapping("/courses")
@Tag(name = "Curso", description = "Endpoint para gerenciamento de cursos")
public interface CourseApi {
  @Operation(
      summary = "Cria um novo curso",
      requestBody = @RequestBody(
          description = "Dados do curso a ser criado",
          content = @Content(
              schema = @Schema(implementation = CourseDTOSchema.class)
          )
      )
  )
  @ApiResponse(responseCode = "201")
  @PostMapping
  ResponseEntity<Void> save(@RequestBody CourseDTO courseDTO);

  @Operation(
      summary = "Atualiza um curso existente",
      requestBody = @RequestBody(
          description = "Dados do curso a ser atualizado",
          content = @Content(
              schema = @Schema(implementation = CourseDTOSchema.class)
          )
      )
  )
  @ApiResponse(responseCode = "200")
  @PutMapping("/{code}")
  ResponseEntity<Void> update(
      @PathVariable("code") Integer courseCode,
      @RequestBody CourseDTO courseDTO
  );

  @Operation(summary = "Exclui um curso")
  @ApiResponse(responseCode = "200")
  @DeleteMapping("/{code}")
  ResponseEntity<Void> delete(@PathVariable("code") Integer courseCode);

  @Operation(summary = "Lista todos os cursos")
  @ApiResponse(
      responseCode = "200",
      content = @Content(array = @ArraySchema(
          schema = @Schema(implementation = CourseSchema.class)
      ))
  )
  @GetMapping
  ResponseEntity<List<Course>> findAll();
}

