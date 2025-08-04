package com.tiago_faria_gouvea.training_control_api.repositories;

import com.tiago_faria_gouvea.training_control_api.domains.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseRepository {
  Integer save(Course course);

  Integer update(Integer courseCode, Course course);

  Integer delete(Integer courseCode);

  List<Course> findAll();

  Optional<Course> findByName(String name);

  Optional<Course> findByCode(Integer code);
}
