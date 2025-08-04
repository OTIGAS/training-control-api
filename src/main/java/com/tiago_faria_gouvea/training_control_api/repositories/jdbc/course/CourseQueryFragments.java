package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course;

/**
 * Fragmentos de SQL usados pra montar consultas da tabela Curso.
 *
 * Essa classe organiza os pedaços de SQL que a gente usa quando precisa trabalhar
 * com dados de cursos — como nome, descrição e duração.
 *
 * O que tem aqui:
 * - SELECT_COLUMNS: seleciona as colunas da tabela Curso com alias (pra evitar conflitos em JOINs).
 * - BASE_QUERY: SELECT básico direto da tabela Curso.
 *
 * Geralmente usado quando outras entidades (como Turma) precisam trazer as infos do curso
 * junto na mesma query.
 */
public class CourseQueryFragments {
  public static final String tableNameCourse = "Curso";
  public static final String tableAliasCourse = "C";
  public static final String columnSuffixCourse = "course";

  public static final String SELECT_COLUMNS = String.format("""
            %s.Codigo AS Codigo_%s,
            %s.Nome AS Nome_%s,
            %s.Descricao AS Descricao_%s,
            %s.Duracao AS Duracao_%s
          """,
      tableAliasCourse, columnSuffixCourse,
      tableAliasCourse, columnSuffixCourse,
      tableAliasCourse, columnSuffixCourse,
      tableAliasCourse, columnSuffixCourse
  );

  public static final String BASE_QUERY = String.format("""
            SELECT
              %s
            FROM %s %s
          """,
      SELECT_COLUMNS,
      tableNameCourse, tableAliasCourse
  );
}