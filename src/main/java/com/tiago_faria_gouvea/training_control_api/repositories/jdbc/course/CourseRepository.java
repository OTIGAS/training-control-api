package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course;

import com.tiago_faria_gouvea.training_control_api.domains.Course;
import com.tiago_faria_gouvea.training_control_api.repositories.ICourseRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course.CourseQueryFragments.*;

@Repository
public class CourseRepository implements ICourseRepository {
  private final JdbcTemplate jdbcTemplate;

  public CourseRepository(
      JdbcTemplate jdbcTemplate
  ) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Integer save(Course course) {
    String sqlCommand = """
            INSERT INTO Curso (Nome, Descricao, Duracao) VALUES (?, ?, ?)
        """;

    return jdbcTemplate.update(
        sqlCommand,
        course.getName(),
        course.getDescription(),
        course.getDuration()
    );
  }

  public Integer update(Integer courseCode, Course course) {
    String sqlCommand = """
            UPDATE Curso
            SET Nome = ?, Descricao = ?, Duracao = ?
            WHERE Codigo = ?
        """;

    return jdbcTemplate.update(sqlCommand,
        course.getName(),
        course.getDescription(),
        course.getDuration(),
        courseCode
    );
  }

  public Integer delete(Integer courseCode) {
    String deleteParticipantsSql =
        "DELETE TP FROM TurmaParticipante TP " +
            "JOIN Turma T ON TP.Turma = T.Codigo " +
            "WHERE T.Curso = ?";
    jdbcTemplate.update(deleteParticipantsSql, courseCode);

    String deleteClassesSql = "DELETE FROM Turma WHERE Curso = ?";
    jdbcTemplate.update(deleteClassesSql, courseCode);

    String deleteCourseSql = "DELETE FROM Curso WHERE Codigo = ?";
    return jdbcTemplate.update(deleteCourseSql, courseCode);
  }

  public List<Course> findAll() {
    String sqlCommand = String.format(
        "%s ORDER BY %s.Nome",
        BASE_QUERY,
        tableAliasCourse
    );

    return jdbcTemplate.query(sqlCommand, this::mapRowCourse);
  }

  public Optional<Course> findByName(String name) {
    String sqlCommand = String.format(
        "%s WHERE %s.Nome = ? ORDER BY %s.Nome",
        BASE_QUERY,
        tableAliasCourse,
        tableAliasCourse
    );

    try {
      Course course = jdbcTemplate.queryForObject(
          sqlCommand,
          this::mapRowCourse,
          name
      );
      return Optional.ofNullable(course);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<Course> findByCode(Integer code) {
    String sqlCommand = String.format(
        "%s WHERE %s.Codigo = ? ORDER BY %s.Nome",
        BASE_QUERY,
        tableAliasCourse,
        tableAliasCourse
    );

    try {
      Course course = jdbcTemplate.queryForObject(
          sqlCommand,
          this::mapRowCourse,
          code
      );
      return Optional.ofNullable(course);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * Mapeia uma linha do ResultSet para um objeto Course.
   *
   * Usa os aliases definidos nos CourseQueryFragments para pegar as colunas
   * com os nomes corretos (ex: "Codigo_course", "Nome_course", etc).
   *
   * Esse método é usado principalmente em consultas que já usam os fragmentos SQL,
   * garantindo que o mapeamento seja consistente com os aliases das colunas.
   *
   * @param rs ResultSet com os dados da consulta SQL
   * @param rowNum índice da linha atual (geralmente não usado aqui)
   * @return um objeto Course populado com os dados da linha
   * @throws SQLException caso tenha problema ao acessar os dados do ResultSet
   */
  public Course mapRowCourse(ResultSet rs, int rowNum) throws SQLException {
    return Course.builder()
        .code(rs.getInt("Codigo_" + columnSuffixCourse))
        .name(rs.getString("Nome_" + columnSuffixCourse))
        .description(rs.getString("Descricao_" + columnSuffixCourse))
        .duration(rs.getInt("Duracao_" + columnSuffixCourse))
        .build();
  }
}
