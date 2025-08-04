package com.tiago_faria_gouvea.training_control_api.repositories;

import com.tiago_faria_gouvea.training_control_api.domains.Course;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ICourseRepositoryTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  private CourseRepository courseRepository;

  @BeforeEach
  void setUp() {
    courseRepository = new CourseRepository(jdbcTemplate);
  }

  private Course createSampleCourse() {
    return Course.builder()
        .name("Java")
        .description("Java course")
        .duration(40)
        .build();
  }

  @Test
  @DisplayName("should save course and return update count")
  void saveCourseCase1() {
    Course course = createSampleCourse();

    Mockito.when(jdbcTemplate.update(
        Mockito.anyString(),
        Mockito.anyString(),
        Mockito.anyString(),
        Mockito.anyInt()
    )).thenReturn(1);

    Integer result = courseRepository.save(course);

    assertEquals(1, result);
    Mockito.verify(jdbcTemplate).update(
        Mockito.contains("INSERT INTO Curso"),
        Mockito.eq(course.getName()),
        Mockito.eq(course.getDescription()),
        Mockito.eq(course.getDuration())
    );
  }

  @Test
  @DisplayName("should update course and return update count")
  void updateCourseCase1() {
    Course course = createSampleCourse();

    Mockito.when(jdbcTemplate.update(
        Mockito.anyString(),
        Mockito.anyString(),
        Mockito.anyString(),
        Mockito.anyInt(),
        Mockito.anyInt()
    )).thenReturn(1);

    Integer result = courseRepository.update(10, course);

    assertEquals(1, result);
    Mockito.verify(jdbcTemplate).update(
        Mockito.contains("UPDATE Curso"),
        Mockito.eq(course.getName()),
        Mockito.eq(course.getDescription()),
        Mockito.eq(course.getDuration()),
        Mockito.eq(10)
    );
  }

  @Test
  @DisplayName("should delete course and return update count")
  void deleteCourseCase1() {
    Mockito.when(jdbcTemplate.update(
        Mockito.anyString(),
        Mockito.anyInt()
    )).thenReturn(1);

    Integer result = courseRepository.delete(5);

    assertEquals(1, result);
    Mockito.verify(jdbcTemplate).update(
        Mockito.contains("DELETE FROM Curso"),
        Mockito.eq(5)
    );
  }

  @Test
  @DisplayName("should find all courses and map results")
  void findAllCourseCase1() {
    Course course = createSampleCourse();
    List<Course> expected = List.of(course);

    Mockito.when(jdbcTemplate.query(
        Mockito.anyString(),
        Mockito.any(RowMapper.class)
    )).thenReturn(expected);

    List<Course> result = courseRepository.findAll();

    assertEquals(expected, result);
    Mockito.verify(jdbcTemplate).query(
        Mockito.contains("SELECT"),
        Mockito.any(RowMapper.class)
    );
  }

  @Test
  @DisplayName("should find course by name and return Optional course")
  void findByNameCourseCase1() {
    Course course = createSampleCourse();

    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq("Java")
    )).thenReturn(course);

    Optional<Course> result = courseRepository.findByName("Java");

    assertTrue(result.isPresent());
    assertEquals(course, result.get());
  }

  @Test
  @DisplayName("should return empty Optional when course not found by name")
  void findByNameCourseCase2() {
    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq("Java")
    )).thenThrow(new EmptyResultDataAccessException(1));

    Optional<Course> result = courseRepository.findByName("Java");

    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("should find course by code and return Optional course")
  void findByCodeCourseCase1() {
    Course course = createSampleCourse();

    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(10)
    )).thenReturn(course);

    Optional<Course> result = courseRepository.findByCode(10);

    assertTrue(result.isPresent());
    assertEquals(course, result.get());
  }

  @Test
  @DisplayName("should return empty Optional when course not found by code")
  void findByCodeCourseCase2() {
    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(10)
    )).thenThrow(new EmptyResultDataAccessException(1));

    Optional<Course> result = courseRepository.findByCode(10);

    assertTrue(result.isEmpty());
  }
}
