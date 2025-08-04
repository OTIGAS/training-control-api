package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.employee;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;
import com.tiago_faria_gouvea.training_control_api.enums.EmployeeStatus;
import com.tiago_faria_gouvea.training_control_api.repositories.IEmployeeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.tiago_faria_gouvea.training_control_api.repositories.jdbc.employee.EmployeeQueryFragments.*;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
  private final JdbcTemplate jdbcTemplate;

  public EmployeeRepository(
      JdbcTemplate jdbcTemplate
  ) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Optional<Employee> findByCode(Integer code) {
    String sqlCommand = String.format(
        "%s WHERE %s.Codigo = ?",
        BASE_QUERY,
        tableAliasEmployee
    );

    try {
      Employee course = jdbcTemplate.queryForObject(
          sqlCommand,
          this::mapRowEmployee,
          code
      );
      return Optional.ofNullable(course);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * Mapeia uma linha do ResultSet para um objeto Employee.
   *
   * Usa os aliases definidos em EmployeeQueryFragments para acessar as colunas
   * corretamente (ex: "Codigo_employee", "Nome_employee", etc).
   *
   * Esse método é usado em consultas que usam esses fragmentos, garantindo que o
   * mapeamento fique alinhado com os nomes das colunas na query.
   *
   * @param rs ResultSet com os dados retornados da consulta
   * @param rowNum índice da linha atual (normalmente não usado aqui)
   * @return objeto Employee preenchido com os dados da linha
   * @throws SQLException se ocorrer erro ao ler os dados do ResultSet
   */
  public Employee mapRowEmployee(ResultSet rs, int rowNum) throws SQLException {
    return Employee.builder()
        .code(rs.getInt("Codigo_" + columnSuffixEmployee))
        .name(rs.getString("Nome_" + columnSuffixEmployee))
        .cpf(rs.getString("CPF_" + columnSuffixEmployee))
        .birthDate(
            rs.getDate("Nascimento_" + columnSuffixEmployee).toLocalDate()
        )
        .role(rs.getString("Cargo_" + columnSuffixEmployee))
        .admission(rs.getDate("Admissao_" + columnSuffixEmployee).toLocalDate())
        .status(EmployeeStatus.fromCode(
            rs.getInt("Status_" + columnSuffixEmployee)
        ))
        .build();
  }
}
