package com.perscholas.task_management_system_11.repository;

import com.perscholas.task_management_system_11.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
