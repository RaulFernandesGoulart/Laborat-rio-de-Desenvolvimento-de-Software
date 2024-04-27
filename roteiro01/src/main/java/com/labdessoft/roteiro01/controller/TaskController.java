package com.labdessoft.roteiro01.controller;



import com.example.roteiro01.entity.Task;
import com.example.roteiro01.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        tasks.forEach(task -> {
            if (task.getCompleted() == null) {
                task.setCompleted(false);
            }
            if (task.isTaskTypeNull()) {
                task.setTaskType(0);
            }
        });
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/manage")
    @Operation(summary = "Manage tasks from the list")
    public ResponseEntity<List<Task>> manageTasks() {
        List<Task> taskList = taskService.manageTasks();
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/complete")
    @Operation(summary = "Complete tasks from the list")
    public ResponseEntity<List<Task>> completeTasks() {
        List<Task> taskList = taskService.completeTasks();
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/prioritize")
    @Operation(summary = "Prioritize tasks from the list")
    public ResponseEntity<List<Task>> prioritizeTasks() {
        List<Task> taskList = taskService.prioritizeTasks();
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/categorize")
    @Operation(summary = "Categorize tasks from the list")
    public ResponseEntity<List<Task>> categorizeTasks() {
        List<Task> taskList = taskService.categorizeTasks();
        return ResponseEntity.ok(taskList);
    }
}







