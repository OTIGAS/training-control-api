package com.tiago_faria_gouvea.training_control_api.repositories;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;
import com.tiago_faria_gouvea.training_control_api.domains.TeamParticipant;
import com.tiago_faria_gouvea.training_control_api.enums.EmployeeStatus;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.employee.EmployeeRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.team.TeamRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.teamParticipant.TeamParticipantRepository;
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
class ITeamParticipantRepositoryTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private TeamRepository teamRepository;

  @Mock
  private EmployeeRepository employeeRepository;

  private TeamParticipantRepository teamParticipantRepository;

  @BeforeEach
  void setUp() {
    teamParticipantRepository = new TeamParticipantRepository(jdbcTemplate, teamRepository, employeeRepository);
  }

  private TeamParticipant createSampleTeamParticipant() {
    return TeamParticipant.builder()
        .code(1)
        .teamCode(10)
        .participantCode(20)
        .build();
  }

  private Employee createSampleEmployee() {
    return Employee.builder()
        .code(1)
        .name("João Silva")
        .cpf("12345678900")
        .birthDate(LocalDate.of(1990, 1, 1))
        .role("Analista")
        .admission(LocalDate.of(2020, 5, 15))
        .status(EmployeeStatus.ACTIVE)
        .build();
  }

  @Test
  @DisplayName("should save team participant and return update count")
  void saveTeamParticipantCase1() {
    TeamParticipant teamParticipant = createSampleTeamParticipant();

    Mockito.when(jdbcTemplate.update(
        Mockito.anyString(),
        Mockito.anyInt(),
        Mockito.anyInt()
    )).thenReturn(1);

    Integer result = teamParticipantRepository.save(teamParticipant);

    assertEquals(1, result);

    Mockito.verify(jdbcTemplate).update(
        Mockito.contains("INSERT INTO TurmaParticipante"),
        Mockito.eq(teamParticipant.getTeamCode()),
        Mockito.eq(teamParticipant.getParticipantCode())
    );
  }

  @Test
  @DisplayName("should delete team participant and return update count")
  void deleteTeamParticipantCase1() {
    Mockito.when(jdbcTemplate.update(
        Mockito.anyString(),
        Mockito.anyInt()
    )).thenReturn(1);

    Integer result = teamParticipantRepository.delete(5);

    assertEquals(1, result);

    Mockito.verify(jdbcTemplate).update(
        Mockito.contains("DELETE FROM TurmaParticipante"),
        Mockito.eq(5)
    );
  }

  @Test
  @DisplayName("should find participants by team code")
  void findParticipantsByTeamCase1() {
    List<Employee> expected = List.of(createSampleEmployee());

    Mockito.when(jdbcTemplate.query(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(10)
    )).thenReturn(expected);

    List<Employee> result = teamParticipantRepository.findParticipantsByTeam(10);

    assertEquals(expected, result);

    Mockito.verify(jdbcTemplate).query(
        Mockito.contains("SELECT"),
        Mockito.any(RowMapper.class),
        Mockito.eq(10)
    );
  }

  @Test
  @DisplayName("should find team participant by code")
  void findByCodeTeamParticipantCase1() {
    TeamParticipant expected = createSampleTeamParticipant();

    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(1)
    )).thenReturn(expected);

    Optional<TeamParticipant> result = teamParticipantRepository.findByCode(1);

    assertTrue(result.isPresent());
    assertEquals(expected, result.get());

    Mockito.verify(jdbcTemplate).queryForObject(
        Mockito.contains("SELECT"),
        Mockito.any(RowMapper.class),
        Mockito.eq(1)
    );
  }

  @Test
  @DisplayName("should return empty optional when team participant not found")
  void findByCodeTeamParticipantCase2() {
    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(999)
    )).thenThrow(new EmptyResultDataAccessException(1));

    Optional<TeamParticipant> result = teamParticipantRepository.findByCode(999);

    assertTrue(result.isEmpty());
  }
}
