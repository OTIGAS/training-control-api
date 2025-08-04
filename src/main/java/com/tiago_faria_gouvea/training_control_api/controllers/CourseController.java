package com.tiago_faria_gouvea.training_control_api.controllers;

import com.tiago_faria_gouvea.training_control_api.domains.Course;
import com.tiago_faria_gouvea.training_control_api.dtos.CourseDTO;
import com.tiago_faria_gouvea.training_control_api.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
  private final CourseService service;

  public CourseController(
      CourseService service
  ) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Void> save(@Valid @RequestBody CourseDTO courseDTO) {
    this.service.save(courseDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{code}")
  public ResponseEntity<Void> update(
      @PathVariable("code") Integer courseCode,
      @RequestBody CourseDTO courseDTO
  ) {
    this.service.update(courseCode, courseDTO);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/{code}")
  public ResponseEntity<Void> delete(@PathVariable("code") Integer courseCode) {
    this.service.delete(courseCode);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping
  public ResponseEntity<List<Course>> findAll() {
    List<Course> courses = this.service.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(courses);
  }
}
