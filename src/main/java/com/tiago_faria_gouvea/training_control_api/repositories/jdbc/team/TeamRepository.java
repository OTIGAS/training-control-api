package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.team;

import com.tiago_faria_gouvea.training_control_api.domains.Team;
import com.tiago_faria_gouvea.training_control_api.repositories.ITeamRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course.CourseRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.tiago_faria_gouvea.training_control_api.repositories.jdbc.team.TeamQueryFragments.*;

@Repository
public class TeamRepository implements ITeamRepository {
  private final JdbcTemplate jdbcTemplate;
  private final CourseRepository courseRepository;

  public TeamRepository(
      JdbcTemplate jdbcTemplate,
      CourseRepository courseRepository
  ) {
    this.jdbcTemplate = jdbcTemplate;
    this.courseRepository = courseRepository;
  }

  public Integer save(Team team) {
    String sqlCommand = """
            INSERT INTO Turma (Inicio, Fim, Local, Curso) VALUES (?, ?, ?, ?)
        """;

    return jdbcTemplate.update(
        sqlCommand,
        team.getStartDate(),
        team.getEndDate(),
        team.getLocation(),
        team.getCourseCode()
    );
  }

  public Integer update(Integer teamCode, Team team) {
    String sqlCommand = """
            UPDATE Turma
            SET Inicio = ?, Fim = ?, Local = ?, Curso = ?
            WHERE Codigo = ?
        """;

    return jdbcTemplate.update(sqlCommand,
        team.getStartDate(),
        team.getEndDate(),
        team.getLocation(),
        team.getCourseCode(),
        teamCode
    );
  }

  public Integer delete(Integer teamCode) {
    String sqlCommand = "DELETE FROM Turma WHERE Codigo = ?";
    return jdbcTemplate.update(sqlCommand, teamCode);
  }

  public List<Team> findByCourseCode(Integer courseCode) {
    String sqlCommand = String.format(
        "%s WHERE Curso = ? ORDER BY %s.Inicio, %s.Fim",
        BASE_QUERY,
        tableAliasTeam,
        tableAliasTeam
    );
    return jdbcTemplate.query(sqlCommand, this::mapRowTeam, courseCode);
  }

  public Optional<Team> findByCode(Integer code) {
    String sqlCommand = String.format(
        "%s WHERE %s.Codigo = ? ORDER BY %s.Inicio, %s.Fim",
        BASE_QUERY,
        tableAliasTeam,
        tableAliasTeam,
        tableAliasTeam
    );
    try {
      Team team = jdbcTemplate.queryForObject(
          sqlCommand,
          this::mapRowTeam,
          code
      );
      return Optional.ofNullable(team);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * Mapeia uma linha do ResultSet para um objeto Team.
   *
   * Usa os aliases definidos em TeamQueryFragments para pegar as colunas certas,
   * como "Codigo_team", "Inicio_team" e por aí vai.
   *
   * Além disso, se o código do curso existir, já aproveita para chamar o mapeamento
   * do curso usando o courseRepository, trazendo o objeto Course junto.
   *
   * @param rs ResultSet com os dados da consulta
   * @param rowNum índice da linha (normalmente não usado aqui)
   * @return objeto Team completo com dados da turma e, se disponível, o curso associado
   * @throws SQLException se ocorrer problema ao ler dados do ResultSet
   */
  public Team mapRowTeam(ResultSet rs, int rowNum) throws SQLException {
    Team team = Team.builder()
        .code(rs.getInt("Codigo_" + columnSuffixTeam))
        .startDate(rs.getDate("Inicio_" + columnSuffixTeam).toLocalDate())
        .endDate(rs.getDate("Fim_" + columnSuffixTeam).toLocalDate())
        .location(rs.getString("Local_" + columnSuffixTeam))
        .courseCode(rs.getObject("Curso_" + columnSuffixTeam, Integer.class))
        .build();

    if (team.getCourseCode() != null) {
      team.setCourse(courseRepository.mapRowCourse(rs, rowNum));
    }

    return team;
  }
}
