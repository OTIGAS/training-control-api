package com.tiago_faria_gouvea.training_control_api.controllers;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;
import com.tiago_faria_gouvea.training_control_api.dtos.TeamParticipantDTO;
import com.tiago_faria_gouvea.training_control_api.services.TeamParticipantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team-participants")
public class TeamParticipantController {
  private final TeamParticipantService service;

  public TeamParticipantController(
      TeamParticipantService service
  ) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Void> save(
      @Valid @RequestBody TeamParticipantDTO teamParticipantDTO
  ) {
    this.service.save(teamParticipantDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{code}")
  public ResponseEntity<Void> delete(
      @PathVariable("code") Integer teamParticipantCode
  ) {
    this.service.delete(teamParticipantCode);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/team-code/{teamCode}")
  public ResponseEntity<List<Employee>> findByCourseCode(
      @PathVariable("teamCode") Integer courseCode
  ) {
    List<Employee> teamParticipants = this.service
        .findParticipantsByTeam(courseCode);
    return ResponseEntity.status(HttpStatus.OK).body(teamParticipants);
  }
}
