package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.teamParticipant;

import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course.CourseQueryFragments;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.employee.EmployeeQueryFragments;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.team.TeamQueryFragments;

/**
 * Fragmentos de SQL usados para montar consultas envolvendo a tabela TurmaParticipante.
 *
 * Essa classe ajuda a organizar e reaproveitar pedaços de SQL (como SELECTs e JOINs)
 * usados em consultas mais complexas. Aqui a gente monta tudo que é necessário para
 * buscar dados da turma, do curso e do funcionário relacionados a um participante.
 *
 * O que tem aqui:
 * - SELECT_COLUMNS: colunas da tabela TurmaParticipante com alias (para não dar conflito).
 * - BASE_JOIN: os LEFT JOINs com as tabelas Team, Course e Employee.
 * - BASE_QUERY: SELECT completo já com os JOINs e colunas prontas.
 *
 * Exemplo de uso: no método findByCode, a query completa é montada usando o BASE_QUERY,
 * com um WHERE no final pra buscar um participante específico.
 */
public class TeamParticipantQueryFragments {
  public static final String tableNameTeamParticipant = "TurmaParticipante";
  public static final String tableAliasTeamParticipant = "TP";
  public static final String columnSuffixTeamParticipant = "teamParticipant";

  public static final String SELECT_COLUMNS = String.format("""
            %s.Codigo AS Codigo_%s,
            %s.Turma AS Turma_%s,
            %s.Funcionario AS Funcionario_%s
          """,
      tableAliasTeamParticipant, columnSuffixTeamParticipant,
      tableAliasTeamParticipant, columnSuffixTeamParticipant,
      tableAliasTeamParticipant, columnSuffixTeamParticipant
  );

  public static final String BASE_JOIN = String.format("""
            LEFT JOIN %s %s ON %s.Turma = %s.Codigo
            LEFT JOIN %s %s ON %s.Curso = %s.Codigo
            LEFT JOIN %s %s ON %s.Funcionario = %s.Codigo
          """,
      // Team
      TeamQueryFragments.tableNameTeam,
      TeamQueryFragments.tableAliasTeam,
      tableAliasTeamParticipant,
      TeamQueryFragments.tableAliasTeam,
      // Course
      CourseQueryFragments.tableNameCourse,
      CourseQueryFragments.tableAliasCourse,
      TeamQueryFragments.tableAliasTeam,
      CourseQueryFragments.tableAliasCourse,
      // Employee
      EmployeeQueryFragments.tableNameEmployee,
      EmployeeQueryFragments.tableAliasEmployee,
      tableAliasTeamParticipant,
      EmployeeQueryFragments.tableAliasEmployee
  );

  public static final String BASE_QUERY = String.format("""
            SELECT
              %s,
              %s,
              %s,
              %s
            FROM %s %s
            %s
          """,
      SELECT_COLUMNS,
      TeamQueryFragments.SELECT_COLUMNS,
      CourseQueryFragments.SELECT_COLUMNS,
      EmployeeQueryFragments.SELECT_COLUMNS,
      tableNameTeamParticipant, tableAliasTeamParticipant,
      BASE_JOIN
  );
}
