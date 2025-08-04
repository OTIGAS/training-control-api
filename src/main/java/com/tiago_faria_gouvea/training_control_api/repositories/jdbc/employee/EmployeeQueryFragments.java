package com.tiago_faria_gouvea.training_control_api.repositories.jdbc.employee;

/**
 * Fragmentos de SQL usados pra montar consultas da tabela Funcionario.
 *
 * Essa classe ajuda a manter os pedaços de SQL organizados quando a gente precisa
 * buscar dados de funcionários (nome, CPF, cargo, etc.), principalmente em JOINs.
 *
 * O que tem aqui:
 * - SELECT_COLUMNS: seleciona todas as colunas da tabela Funcionario com alias pra evitar conflito.
 * - BASE_QUERY: SELECT básico direto da tabela Funcionario, sem JOINs.
 *
 * Normalmente usada quando outras entidades (como TurmaParticipante) precisam puxar
 * dados do funcionário junto na mesma consulta.
 */
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
