package com.labdessoft.roteiro01.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @GetMapping("/task")
    @Operation(summary = "Lista todas as tarefas da lista")
    public String listAll() {
        return "Listar todas as tasks";
    }

    @GetMapping("/task/{id}")
    @Operation(summary = "Recuperar uma tarefa da lista pelo ID")
    public String retrieveTaskById() {
        return "Recuperar uma tarefa da lista pelo ID";
    }

    @PostMapping("/task")
    @Operation(summary = "Incluir uma nova tarefa na lista de tarefas")
    public String addTask() {
        return "Incluir uma nova tarefa na lista de tarefas";
    }

    @GetMapping("/task/{id}/description")
    @Operation(summary = "Descrever uma tarefa pelo ID")
    public String describeTaskById() {
        return "Descrever uma tarefa pelo ID";
    }
}






