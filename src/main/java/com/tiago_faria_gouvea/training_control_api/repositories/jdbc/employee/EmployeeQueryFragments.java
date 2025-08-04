package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.employee;

public class EmployeeQueryFragments {
  public static final String tableNameEmployee = "Funcionario";
  public static final String tableAliasEmployee = "F";
  public static final String columnSuffixEmployee = "employee";

  public static final String SELECT_COLUMNS = String.format("""
            %s.Codigo AS Codigo_%s,
            %s.Nome AS Nome_%s,
            %s.CPF AS CPF_%s,
            %s.Nascimento AS Nascimento_%s,
            %s.Cargo AS Cargo_%s,
            %s.Admissao AS Admissao_%s,
            %s.Status AS Status_%s
          """,
      tableAliasEmployee, columnSuffixEmployee,
      tableAliasEmployee, columnSuffixEmployee,
      tableAliasEmployee, columnSuffixEmployee,
      tableAliasEmployee, columnSuffixEmployee,
      tableAliasEmployee, columnSuffixEmployee,
      tableAliasEmployee, columnSuffixEmployee,
      tableAliasEmployee, columnSuffixEmployee
  );

  public static final String BASE_QUERY = String.format("""
            SELECT
              %s
            FROM %s %s
          """,
      SELECT_COLUMNS,
      tableNameEmployee, tableAliasEmployee
  );
}
