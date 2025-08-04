package com.tiago_faria_gouvea.training_control_api.repositories;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;
import com.tiago_faria_gouvea.training_control_api.enums.EmployeeStatus;
import com.tiago_faria_gouvea.training_control_api.repositories.jdbc.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class IEmployeeRepositoryTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  private EmployeeRepository employeeRepository;

  @BeforeEach
  void setUp() {
    employeeRepository = new EmployeeRepository(jdbcTemplate);
  }

  private Employee createSampleEmployee() {
    return Employee.builder()
        .code(1)
        .name("João Silva")
        .cpf("12345678900")
        .birthDate(LocalDate.of(1990, 1, 1))
        .role("Analista")
        .admission(LocalDate.of(2020, 5, 15))
        .status(EmployeeStatus.ACTIVE)
        .build();
  }


  @Test
  @DisplayName("should find employee by code and return Optional employee")
  void findByCodeEmployeeCase1() {
    Employee employee = createSampleEmployee();

    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(1)
    )).thenReturn(employee);

    Optional<Employee> result = employeeRepository.findByCode(1);

    assertTrue(result.isPresent());
    assertEquals(employee, result.get());
  }

  @Test
  @DisplayName("should return empty Optional when employee not found by code")
  void findByCodeEmployeeCase2() {
    Mockito.when(jdbcTemplate.queryForObject(
        Mockito.anyString(),
        Mockito.any(RowMapper.class),
        Mockito.eq(1)
    )).thenThrow(new EmptyResultDataAccessException(1));

    Optional<Employee> result = employeeRepository.findByCode(1);

    assertTrue(result.isEmpty());
  }

}