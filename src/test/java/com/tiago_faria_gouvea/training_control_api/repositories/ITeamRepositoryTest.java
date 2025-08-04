package com.tiago_faria_gouvea.training_control_api.repositories;

import com.tiago_faria_gouvea.training_control_api.domains.Team;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course.CourseRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.team.TeamRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ITeamRepositoryTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private CourseRepository courseRepository;

  private TeamRepository teamRepository;

  @BeforeEach
  void setUp() {
    teamRepository = new TeamRepository(jdbcTemplate, courseRepository);
  }

  private Team createSampleTeam() {
    return Team.builder()
        .code(1)
        .startDate(LocalDate.of(2024, 1, 10))
        .endDate(LocalDate.of(2024, 4, 15))
        .location("Sala 101")
        .courseCode(1)
        .build();
  }

  @Test
  @DisplayName("should save team and return update count")
  void saveTeamCase1() {
    Team team = createSampleTeam();

    Mockito.when(jdbcTemplate.update(
        Mockito.anyString(),
        Mockito.any(),
        Mockito.any(),
        Mockito.anyString(),
        Mockito.anyInt()
    )).thenReturn(1);

    Integer result = teamRepository.save(team);

    assertEquals(1, result);

    Mockito.verify(jdbcTemplate).update(
        Mockito.contains("INSERT INTO Turma"),
        Mockito.eq(team.getStartDate()),
        Mockito.eq(team.getEndDate()),
        Mockito.eq(team.getLocation()),
        Mockito.eq(team.getCourseCode())
    );
  }

  @Test
  @DisplayName("should update team and return update count")
  void updateTeamCase1() {
    Team team = createSampleTeam();

    Mockito.when(jdbcTemplate.update(
        Mockito.anyString(),
        Mockito.any(),
        Mockito.any(),
        Mockito.anyString(),
        Mockito.anyInt(),
        Mockito.anyInt()
    )).thenReturn(1);

    Integer result = teamRepository.update(10, team);

    assertEquals(1, result);

    Mockito.verify(jdbcTemplate).update(
        Mockito.contains("UPDATE Turma"),
        Mockito.eq(team.getStartDate()),
        Mockito.eq(team.getEndDate()),
        Mockito.eq(team.getLocation()),
        Mockito.eq(team.getCourseCode()),
        Mockito.eq(10)
    );
  }

  @Test
  @DisplayName("should delete team and return update count")
  void deleteTeamCase1() {
    Mockito.when(jdbcTemplate.update(
        Mockito.anyString(),
        Mockito.anyInt()
    )).thenReturn(1);

    Integer result = teamRepository.delete(5);

    assertEquals(1, result);

    Mockito.verify(jdbcTemplate).update(
        Mockito.contains("DELETE FROM Turma"),
        Mockito.eq(5)
    );
  }

  @Test
  @DisplayName("should find team by code and return Optional")
  void findByCodeTeamCase1() {
    Team team = createSampleTeam();

    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(10)
    )).thenReturn(team);

    Optional<Team> result = teamRepository.findByCode(10);

    assertTrue(result.isPresent());
    assertEquals(team, result.get());
  }

  @Test
  @DisplayName("should return empty Optional when team not found by code")
  void findByCodeTeamCase2() {
    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(10)
    )).thenThrow(new EmptyResultDataAccessException(1));

    Optional<Team> result = teamRepository.findByCode(10);

    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("should find teams by course code")
  void findByCourseCodeCase1() {
    List<Team> expected = List.of(createSampleTeam());

    Mockito.when(jdbcTemplate.query(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(5)
    )).thenReturn(expected);

    List<Team> result = teamRepository.findByCourseCode(5);

    assertEquals(expected, result);
  }
}
