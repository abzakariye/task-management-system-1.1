package com.perscholas.task_management_system_11.service;

import com.perscholas.task_management_system_11.model.Employee;
import com.perscholas.task_management_system_11.model.Task;
import com.perscholas.task_management_system_11.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

//    public Optional<Task> getTasksByEmployee(Employee employee) {
//        return getTaskById(employee.getId());
//    }

    public List<Task> getTasksByEmployee(Employee employee) {
        return employee.getTasks();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setStatus(updatedTask.getStatus());
                    return taskRepository.save(task);
                })
                .orElseThrow(()-> new RuntimeException("Task not found"));
    }

    public void updateTaskStatus(Long taskId, String status) {

        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            task.get().setStatus(status);
            taskRepository.save(task.get());
        }

//        taskRepository.findById(taskId)
//                .map(task -> {
//                    task.setStatus(status);
//                    return taskRepository.save(task);
//                }).orElseThrow(() -> new RuntimeException("Task not found"));

    }
}
