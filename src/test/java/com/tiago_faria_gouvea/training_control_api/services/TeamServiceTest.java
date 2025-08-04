package com.tiago_faria_gouvea.training_control_api.services;

import com.tiago_faria_gouvea.training_control_api.domains.Course;
import com.tiago_faria_gouvea.training_control_api.domains.Team;
import com.tiago_faria_gouvea.training_control_api.dtos.TeamDTO;
import com.tiago_faria_gouvea.training_control_api.config.exception.CourseChangeNotAllowedException;
import com.tiago_faria_gouvea.training_control_api.config.exception.InvalidDateRangeException;
import com.tiago_faria_gouvea.training_control_api.config.exception.ResourceNotFoundException;
import com.tiago_faria_gouvea.training_control_api.repositories.ICourseRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.ITeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

  @Mock
  private ITeamRepository repository;

  @Mock
  private ICourseRepository courseRepository;

  @InjectMocks
  private TeamService teamService;

  private TeamDTO validTeamDTO;
  private TeamDTO invalidDateTeamDTO;
  private Team team;

  @BeforeEach
  void setup() {
    validTeamDTO = new TeamDTO();
    validTeamDTO.setStartDate(LocalDate.of(2024, 1, 1));
    validTeamDTO.setEndDate(LocalDate.of(2024, 12, 31));
    validTeamDTO.setLocation("Room 101");
    validTeamDTO.setCourseCode(10);

    invalidDateTeamDTO = new TeamDTO();
    invalidDateTeamDTO.setStartDate(LocalDate.of(2025, 1, 1));
    invalidDateTeamDTO.setEndDate(LocalDate.of(2024, 12, 31));
    invalidDateTeamDTO.setLocation("Room 101");
    invalidDateTeamDTO.setCourseCode(10);

    team = Team.builder()
        .startDate(validTeamDTO.getStartDate())
        .endDate(validTeamDTO.getEndDate())
        .location(validTeamDTO.getLocation())
        .courseCode(validTeamDTO.getCourseCode())
        .build();
  }

  @Test
  @DisplayName(
      "should save team when start date is before or equal to end date"
  )
  void saveTeamCase1() {
    Mockito.when(courseRepository.findByCode(Mockito.anyInt()))
        .thenReturn(Optional.of(new Course()));
    teamService.save(validTeamDTO);
    Mockito.verify(repository).save(Mockito.any(Team.class));
  }

  @Test
  @DisplayName(
      "should throw InvalidDateRangeException when start date is after end date on save"
  )
  void saveTeamCase2() {
    assertThrows(
        InvalidDateRangeException.class,
        () -> teamService.save(invalidDateTeamDTO)
    );
    Mockito.verify(repository, Mockito.never()).save(Mockito.any());
  }

  @Test
  @DisplayName(
      "should update team when dates are valid and course code does not change"
  )
  void updateTeamCase1() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.of(team));
    teamService.update(1, validTeamDTO);
    Mockito.verify(repository).update(Mockito.eq(1), Mockito.any(Team.class));
  }

  @Test
  @DisplayName(
      "should throw InvalidDateRangeException when start date is after end date on update"
  )
  void updateTeamCase2() {
    assertThrows(
        InvalidDateRangeException.class,
        () -> teamService.update(1, invalidDateTeamDTO)
    );
    Mockito.verify(repository, Mockito.never())
        .update(Mockito.anyInt(), Mockito.any());
  }

  @Test
  @DisplayName(
      "should throw ResourceNotFoundException when updating non-existing team"
  )
  void updateTeamCase3() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class,
        () -> teamService.update(1, validTeamDTO)
    );
    Mockito.verify(repository, Mockito.never())
        .update(Mockito.anyInt(), Mockito.any());
  }

  @Test
  @DisplayName(
      "should throw CourseChangeNotAllowedException when trying to change course code on update"
  )
  void updateTeamCase4() {
    Team differentCourseTeam = Team.builder()
        .startDate(validTeamDTO.getStartDate())
        .endDate(validTeamDTO.getEndDate())
        .location(validTeamDTO.getLocation())
        .courseCode(999)
        .build();
    Mockito.when(repository.findByCode(1))
        .thenReturn(Optional.of(differentCourseTeam));
    assertThrows(
        CourseChangeNotAllowedException.class,
        () -> teamService.update(1, validTeamDTO)
    );
    Mockito.verify(repository, Mockito.never())
        .update(Mockito.anyInt(), Mockito.any());
  }

  @Test
  @DisplayName("should delete team when team exists")
  void deleteTeamCase1() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.of(team));
    teamService.delete(1);
    Mockito.verify(repository).delete(1);
  }

  @Test
  @DisplayName(
      "should throw ResourceNotFoundException when deleting non-existing team"
  )
  void deleteTeamCase2() {
    Mockito.when(repository.findByCode(1)).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> teamService.delete(1));
    Mockito.verify(repository, Mockito.never()).delete(Mockito.anyInt());
  }

  @Test
  @DisplayName("should return list of teams filtered by course code")
  void findByCourseTeamCase1() {
    List<Team> expected = List.of(team);
    Mockito.when(repository.findByCourseCode(10)).thenReturn(expected);
    List<Team> result = teamService.findByCourseCode(10);
    assertEquals(expected, result);
  }
}