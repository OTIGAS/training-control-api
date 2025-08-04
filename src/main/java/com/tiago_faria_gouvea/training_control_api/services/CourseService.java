package com.tiago_faria_gouvea.training_control_api.services;

import com.tiago_faria_gouvea.training_control_api.domains.Course;
import com.tiago_faria_gouvea.training_control_api.dtos.CourseDTO;
import com.tiago_faria_gouvea.training_control_api.config.exception.ConflictException;
import com.tiago_faria_gouvea.training_control_api.config.exception.ResourceNotFoundException;
import com.tiago_faria_gouvea.training_control_api.repositories.ICourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
  private final ICourseRepository repository;

  public CourseService(
      ICourseRepository repository
  ) {
    this.repository = repository;
  }

  public void save(CourseDTO courseDTO) {
    this.repository.findByName(courseDTO.getName()).ifPresent(course -> {
      throw new ConflictException("O nome já está em uso");
    });

    Course course = mappingToDomain(courseDTO);

    this.repository.save(course);
  }

  public void update(Integer courseCode, CourseDTO courseDTO) {
    Course courseFound = this.repository.findByCode(courseCode)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Curso não encontrado"
        ));

    if (!courseFound.getName().equals(courseDTO.getName())) {
      this.repository.findByName(courseDTO.getName())
          .ifPresent(course -> {
            throw new ConflictException("O nome já está em uso");
          });
    }

    Course course = mappingToDomain(courseDTO);

    this.repository.update(courseCode, course);
  }

  public void delete(Integer courseCode) {
    this.repository.findByCode(courseCode)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Curso não encontrado"
        ));

    this.repository.delete(courseCode);
  }

  public List<Course> findAll() {
    return this.repository.findAll();
  }

  public Course mappingToDomain(CourseDTO courseDTO) {
    return Course.builder()
        .name(courseDTO.getName())
        .description(courseDTO.getDescription())
        .duration(courseDTO.getDuration())
        .build();
  }
}
