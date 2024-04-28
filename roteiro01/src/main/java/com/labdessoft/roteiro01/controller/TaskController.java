package com.labdessoft.roteiro01.controller;

import com.example.roteiro01.entity.Task;
import com.example.roteiro01.model.ErrorMessage;
import com.example.roteiro01.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Obtém uma tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> obterTarefa(@PathVariable Long id) {
        Task tarefa = taskService.obterTarefaPorId(id);
        return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.notFound().build();
    }

    // Cria uma nova tarefa
    @PostMapping
    public ResponseEntity<?> criarTarefa(@Valid @RequestBody Task tarefa) {
        try {
            Task tarefaCriada = taskService.criarTarefa(tarefa);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(tarefaCriada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    // Atualiza uma tarefa existente por ID
    @PutMapping("/{id}")
    public ResponseEntity<Task> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody Task tarefa) {
        Task tarefaAtualizada = taskService.atualizarTarefa(id, tarefa);
        return tarefaAtualizada != null ? ResponseEntity.ok(tarefaAtualizada) : ResponseEntity.notFound().build();
    }

    // Deleta uma tarefa existente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        taskService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

    // Obtém todas as tarefas
    @GetMapping
    public ResponseEntity<List<Task>> obterTodasTarefas() {
        List<Task> tarefas = taskService.obterTodasTarefas();
        return ResponseEntity.ok(tarefas);
    }
}
