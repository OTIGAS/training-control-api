package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.team;

import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course.CourseQueryFragments;

/**
 * Fragmentos de SQL usados para montar consultas relacionadas à tabela Turma.
 *
 * Essa classe serve pra organizar os pedaços de SQL que envolvem a entidade Turma
 * (como colunas, JOINs e a query principal), facilitando a reutilização e evitando
 * duplicação de código.
 *
 * O que tem aqui:
 * - SELECT_COLUMNS: seleciona as colunas da tabela Turma com alias personalizados.
 * - BASE_JOIN: LEFT JOIN com a tabela Curso, já que cada turma tem um curso.
 * - BASE_QUERY: SELECT completo com JOIN, pronto pra ser usado em métodos de busca.
 *
 * Normalmente isso é usado em consultas via JdbcTemplate, quando você precisa buscar
 * uma turma já trazendo os dados do curso junto.
 */
public class TeamQueryFragments {
  public static final String tableNameTeam = "Turma";
  public static final String tableAliasTeam = "T";
  public static final String columnSuffixTeam = "team";

  public static final String SELECT_COLUMNS = String.format("""
            %s.Codigo AS Codigo_%s,
            %s.Inicio AS Inicio_%s,
            %s.Fim AS Fim_%s,
            %s.Local AS Local_%s,
            %s.Curso AS Curso_%s
          """,
      tableAliasTeam, columnSuffixTeam,
      tableAliasTeam, columnSuffixTeam,
      tableAliasTeam, columnSuffixTeam,
      tableAliasTeam, columnSuffixTeam,
      tableAliasTeam, columnSuffixTeam
  );

  public static final String BASE_JOIN = String.format("""
            LEFT JOIN %s %s ON %s.Curso = %s.Codigo
          """,
      CourseQueryFragments.tableNameCourse,
      CourseQueryFragments.tableAliasCourse,
      tableAliasTeam,
      CourseQueryFragments.tableAliasCourse
  );

  public static final String BASE_QUERY = String.format("""
            SELECT
              %s,
              %s
            FROM %s %s
            %s
          """,
      SELECT_COLUMNS,
      CourseQueryFragments.SELECT_COLUMNS,
      tableNameTeam, tableAliasTeam,
      BASE_JOIN
  );
}
