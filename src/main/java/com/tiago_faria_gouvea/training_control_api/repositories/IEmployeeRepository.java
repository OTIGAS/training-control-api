package com.tiago_faria_gouvea.training_control_api.repositories;

import com.tiago_faria_gouvea.training_control_api.domains.Employee;

import java.util.Optional;

public interface IEmployeeRepository {
  Optional<Employee> findByCode(Integer code);
}
