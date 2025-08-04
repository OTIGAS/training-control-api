package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course;

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