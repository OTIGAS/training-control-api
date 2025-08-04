package com.tiago_faria_gouvea.training_control_api.controllers;

import com.tiago_faria_gouvea.training_control_api.domains.Team;
import com.tiago_faria_gouvea.training_control_api.dtos.TeamDTO;
import com.tiago_faria_gouvea.training_control_api.services.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
  private final TeamService service;

  public TeamController(
      TeamService service
  ) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Void> save(@Valid @RequestBody TeamDTO teamDTO) {
    this.service.save(teamDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{code}")
  public ResponseEntity<Void> update(
      @PathVariable("code") Integer teamCode,
      @Valid @RequestBody TeamDTO teamDTO
  ) {
    this.service.update(teamCode, teamDTO);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/{code}")
  public ResponseEntity<Void> delete(@PathVariable("code") Integer teamCode) {
    this.service.delete(teamCode);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/course-code/{courseCode}")
  public ResponseEntity<List<Team>> findByCourseCode(
      @PathVariable("courseCode") Integer courseCode
  ) {
    List<Team> teams = this.service.findByCourseCode(courseCode);
    return ResponseEntity.status(HttpStatus.OK).body(teams);
  }
}
