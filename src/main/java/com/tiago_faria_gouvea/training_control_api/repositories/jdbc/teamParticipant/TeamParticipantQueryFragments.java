package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.teamParticipant;

import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.course.CourseQueryFragments;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.employee.EmployeeQueryFragments;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.team.TeamQueryFragments;

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
