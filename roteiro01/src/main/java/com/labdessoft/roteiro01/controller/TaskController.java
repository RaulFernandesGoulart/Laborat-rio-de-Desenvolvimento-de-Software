package com.labdessoft.roteiro01.controller;

import com.labdessoft.roteiro01.dto.TaskDTO;
import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> obterTarefa(@PathVariable Long id) {
        Task tarefa = taskService.obterTarefaPorId(id);
        if (tarefa != null) {
            return ResponseEntity.ok(new TaskDTO(tarefa));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TaskDTO> criarTarefa(@Valid @RequestBody TaskDTO taskDTO) {
        Task tarefa = taskService.criarTarefa(taskDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TaskDTO(tarefa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        Task tarefa = taskDTO.toEntity();
        tarefa.setId(id);
        Task tarefaAtualizada = taskService.atualizarTarefa(tarefa);
        if (tarefaAtualizada != null) {
            return ResponseEntity.ok(new TaskDTO(tarefaAtualizada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        taskService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> obterTodasTarefas() {
        List<Task> tarefas = taskService.obterTodasTarefas();
        List<TaskDTO> taskDTOs = tarefas.stream().map(TaskDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }
}
