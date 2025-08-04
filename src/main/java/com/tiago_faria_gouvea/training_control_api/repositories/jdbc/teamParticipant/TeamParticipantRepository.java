package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.teamParticipant;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;
import com.tiago_faria_gouvea.training_control_api.domains.TeamParticipant;
import com.tiago_faria_gouvea.training_control_api.repositories.ITeamParticipantRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.employee.EmployeeRepository;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.team.TeamRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.tiago_faria_gouvea.training_control_api.repositories.jdbc.team.TeamQueryFragments.tableAliasTeam;
import static com.tiago_faria_gouvea.training_control_api.repositories.jdbc.teamParticipant.TeamParticipantQueryFragments.*;

@Repository
public class TeamParticipantRepository implements ITeamParticipantRepository {
  private final JdbcTemplate jdbcTemplate;
  private final TeamRepository teamRepository;
  private final EmployeeRepository employeeRepository;

  public TeamParticipantRepository(
      JdbcTemplate jdbcTemplate,
      TeamRepository teamRepository,
      EmployeeRepository employeeRepository
  ) {
    this.jdbcTemplate = jdbcTemplate;
    this.teamRepository = teamRepository;
    this.employeeRepository = employeeRepository;
  }

  public Integer save(TeamParticipant teamParticipant) {
    String sqlCommand = """
            INSERT INTO TurmaParticipante (Turma, Funcionario) VALUES (?, ?)
        """;

    return jdbcTemplate.update(
        sqlCommand,
        teamParticipant.getTeamCode(),
        teamParticipant.getParticipantCode()
    );
  }

  public Integer delete(Integer teamParticipantCode) {
    String sqlCommand = "DELETE FROM TurmaParticipante WHERE Codigo = ?";
    return jdbcTemplate.update(sqlCommand, teamParticipantCode);
  }

  public List<Employee> findParticipantsByTeam(Integer teamCode) {
    String sqlCommand = String.format(
        "%s WHERE %s.Codigo = ? ORDER BY %s.Turma, %s.Funcionario",
        BASE_QUERY,
        tableAliasTeam,
        tableAliasTeamParticipant,
        tableAliasTeamParticipant
    );

    return jdbcTemplate.query(
        sqlCommand,
        this.employeeRepository::mapRowEmployee,
        teamCode
    );
  }

  public Optional<TeamParticipant> findByCode(Integer code) {
    String sqlCommand = String.format(
        "%s WHERE %s.Codigo = ? ORDER BY %s.Turma, %s.Funcionario",
        BASE_QUERY,
        tableAliasTeamParticipant,
        tableAliasTeamParticipant,
        tableAliasTeamParticipant
    );

    try {
      TeamParticipant teamParticipant = jdbcTemplate.queryForObject(
          sqlCommand,
          this::mapRowTeamParticipant,
          code
      );
      return Optional.ofNullable(teamParticipant);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * Mapeia uma linha do ResultSet para um objeto TeamParticipant.
   *
   * Usa os aliases definidos em TeamParticipantQueryFragments pra acessar as colunas,
   * tipo "Codigo_teamParticipant", "Turma_teamParticipant" e "Funcionario_teamParticipant".
   *
   * Se o código da turma existir, já chama o mapeamento do time com o teamRepository.
   * Se o código do participante existir, faz o mesmo com o funcionário via employeeRepository.
   *
   * Isso ajuda a montar o objeto completo com os dados relacionados na mesma consulta.
   *
   * @param rs ResultSet com os dados da query
   * @param rowNum índice da linha atual (geralmente não usado)
   * @return objeto TeamParticipant com times e participantes carregados, se houver
   * @throws SQLException em caso de erro ao ler dados do ResultSet
   */
  public TeamParticipant mapRowTeamParticipant(
      ResultSet rs, int rowNum
  ) throws SQLException {
    TeamParticipant teamParticipant = TeamParticipant.builder()
        .code(rs.getInt("Codigo_" + columnSuffixTeamParticipant))
        .teamCode(
            rs.getObject("Turma_" + columnSuffixTeamParticipant, Integer.class)
        )
        .participantCode(
            rs.getObject(
                "Funcionario_" + columnSuffixTeamParticipant,
                Integer.class
            )
        )
        .build();

    if (teamParticipant.getTeamCode() != null) {
      teamParticipant.setTeam(teamRepository.mapRowTeam(rs, rowNum));
    }

    if (teamParticipant.getParticipantCode() != null) {
      teamParticipant.setParticipant(
          employeeRepository.mapRowEmployee(rs, rowNum)
      );
    }

    return teamParticipant;
  }
}
