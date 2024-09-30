package com.perscholas.task_management_system_11.controller;

import com.perscholas.task_management_system_11.model.Task;
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
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model) {
        log.info("Fetching all tasks");
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/new")
    public String showCreateTaskForm(Model model) {
        log.info("Displaying new task form");
        Task task = new Task();
        model.addAttribute("task", task);
        return "task-form";
    }



    @GetMapping("/edit/{id}")
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
        return "task-form";
    }

    @PostMapping("/save")
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

   @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
       log.info("Deleting task with ID: {}", id);
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
   }

}
