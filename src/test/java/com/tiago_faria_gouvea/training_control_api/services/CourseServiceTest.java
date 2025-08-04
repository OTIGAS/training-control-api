package com.tiago_faria_gouvea.training_control_api.services;

import com.tiago_faria_gouvea.training_control_api.domains.Course;
import com.tiago_faria_gouvea.training_control_api.dtos.CourseDTO;
import com.tiago_faria_gouvea.training_control_api.config.exception.ConflictException;
import com.tiago_faria_gouvea.training_control_api.config.exception.ResourceNotFoundException;
import com.tiago_faria_gouvea.training_control_api.repositories.ICourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

  @Mock
  private ICourseRepository repository;

  @InjectMocks
  private CourseService courseService;

  private CourseDTO courseDTO;
  private Course course;

  @BeforeEach
  void setup() {
    courseDTO = new CourseDTO();
    courseDTO.setName("Java");
    courseDTO.setDescription("Curso de Java");
    courseDTO.setDuration(40);

    course = Course.builder()
        .name("Java")
        .description("Curso de Java")
        .duration(40)
        .build();
  }

  @Test
  @DisplayName("should save course when name is unique")
  void saveCourseCase1() {
    Mockito.when(repository.findByName("Java")).thenReturn(Optional.empty());
    courseService.save(courseDTO);
    Mockito.verify(repository).save(Mockito.any(Course.class));
  }

  @Test
  @DisplayName(
      "should throw ConflictException when saving course with duplicated name"
  )
  void saveCourseCase2() {
    Mockito.when(repository.findByName("Java")).thenReturn(Optional.of(course));
    assertThrows(ConflictException.class, () -> courseService.save(courseDTO));
  }

  @Test
  @DisplayName(
      "should update course when course exists and name is the same"
  )
  void updateCourseCase1() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.of(course));
    courseService.update(1, courseDTO);
    Mockito.verify(repository).update(Mockito.eq(1), Mockito.any(Course.class));
  }

  @Test
  @DisplayName(
      "should throw ConflictException when updating course with a name that already exists"
  )
  void updateCourseCase2() {
    Course existing = Course.builder().name("Python").build();
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.of(existing));
    Mockito.when(repository.findByName("Java")).thenReturn(Optional.of(course));
    assertThrows(
        ConflictException.class,
        () -> courseService.update(1, courseDTO)
    );
  }

  @Test
  @DisplayName(
      "should throw ResourceNotFoundException when updating non-existing course"
  )
  void updateCourseCase3() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.empty());
    assertThrows(
        ResourceNotFoundException.class,
        () -> courseService.update(1, courseDTO)
    );
  }

  @Test
  @DisplayName("should delete course when course exists")
  void deleteCourseCase1() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.of(course));
    courseService.delete(1);
    Mockito.verify(repository).delete(1);
  }

  @Test
  @DisplayName(
      "should throw ResourceNotFoundException when deleting non-existing course"
  )
  void deleteCourseCase2() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> courseService.delete(1));
  }

  @Test
  @DisplayName("should return list of courses when findAll is called")
  void findAllCoursesCase1() {
    List<Course> expected = List.of(course);
    Mockito.when(repository.findAll()).thenReturn(expected);
    List<Course> result = courseService.findAll();
    assertEquals(expected, result);
  }
}
