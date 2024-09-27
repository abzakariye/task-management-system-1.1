package com.perscholas.task_management_system_11.controller;

import com.perscholas.task_management_system_11.model.Task;
import com.perscholas.task_management_system_11.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// @RestController // This is for REST ENDPOINT CONTROLLER (POSTMAN)
@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks-list";
    }

    @GetMapping("/new")
    public String showCreateTaskForm(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "task-form";
    }



    @GetMapping("/edit{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
        }else {
            return "redirect:/tasks";
        }
        return "task-form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task) {
        if (task.getId() != null) {
            taskService.updateTask(task.getId(), task);
        }else {
            taskService.createTask(task);
        }

        return "redirect:/tasks";
    }

   @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
   }

}
