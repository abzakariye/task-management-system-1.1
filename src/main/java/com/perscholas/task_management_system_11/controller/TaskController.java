package com.perscholas.task_management_system_11.controller;

import com.perscholas.task_management_system_11.model.Employee;
import com.perscholas.task_management_system_11.model.Task;
import com.perscholas.task_management_system_11.service.EmployeeService;
import com.perscholas.task_management_system_11.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// @RestController // This is for REST ENDPOINT CONTROLLER (POSTMAN)
@Slf4j
@Controller
// @RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/tasks")
    public String getAllTasks(Model model) {
        log.info("Fetching all tasks");
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "task-list-old";
    }

    @GetMapping("/tasks/new")
    public String showCreateTaskForm(Model model) {
        log.info("Displaying new task form");
        Task task = new Task();
        //
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("task", task);
        model.addAttribute("employees", employees);
        return "task-form-old";
    }



    @GetMapping("/tasks/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        log.info("Fetching task with ID: {}", id);
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if (optionalTask.isPresent()) {
            model.addAttribute("task", optionalTask.get());
            log.info("Task found: {}", optionalTask.get());
        }else {
            log.info("Task with ID not found: {}", id);
            return "redirect:/tasks";
        }
        return "task-form-old";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute("task") Task task) {
        if (task.getId() == null) {
            taskService.createTask(task);
            log.info("Creating new task: {}", task);
        }else {
            taskService.updateTask(task.getId(), task);
            log.info("Updating task: {}", task);
        }

        return "redirect:/tasks";
    }

   @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
       log.info("Deleting task with ID: {}", id);
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
   }

   @GetMapping("/tasks/employee")
    public String employeeTaskHub(Model model) {
        log.info("Fetching employee tasks");
        // Employee employee = getAuthenticatedEmployee();
       Optional<Employee> employee = employeeService.getEmployeeById(2L);
       List<Task> tasks = taskService.getTasksByEmployee(employee.get());
        model.addAttribute("tasks", tasks);
        model.addAttribute("employee", employee.get());
        return "task-hub";
   }

   @PostMapping("/tasks/update-status")
    public String updateTaskStatus(@RequestParam Long taskId, @RequestParam String status) {
        Optional<Employee> employee = employeeService.getEmployeeById(2L);

        Optional<Task> task = taskService.getTaskById(taskId);

        Employee employee1 = employee.get();
        Task task1 = task.get();

        // taskService.updateTaskStatus(taskId, status);

       if(task1.getEmployee().equals(employee1)) {
           taskService.updateTaskStatus(taskId, status);
           return "redirect:/tasks/employee";
       }

       return "redirect:/tasks";

//
//        if(task.isPresent() && task.get().getEmployee().getId().equals(employee.get().getId())) {
//            taskService.updateTaskStatus(taskId, status);
//
//        }

   }

//    private Employee getAuthenticatedEmployee() {
//
//        return
//    }

}
