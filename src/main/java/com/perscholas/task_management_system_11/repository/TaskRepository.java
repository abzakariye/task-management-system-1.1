package com.perscholas.task_management_system_11.repository;

import com.perscholas.task_management_system_11.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
