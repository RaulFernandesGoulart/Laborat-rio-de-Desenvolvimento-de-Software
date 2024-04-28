package com.labdessoft.roteiro01.service;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.entity.TaskStatus;
import com.labdessoft.roteiro01.entity.TaskType;
import com.labdessoft.roteiro01.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task obterTarefaPorId(Long id) {
        return taskRepository.findById(id)
                .map(this::setTaskStatus)
                .orElse(null);
    }

    public Task criarTarefa(Task tarefa) {
        validarDataDeVencimento(tarefa);
        return taskRepository.save(setTaskStatus(tarefa));
    }

    public Task atualizarTarefa(Long id, Task tarefaAtualizada) {
        return taskRepository.findById(id)
                .map(tarefaExistente -> {
                    tarefaAtualizada.setId(tarefaExistente.getId()); // Mantém o ID original
                    return taskRepository.save(setTaskStatus(tarefaAtualizada));
                })
                .orElse(null);
    }

    public void deletarTarefa(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> obterTodasTarefas() {
        return taskRepository.findAll().stream()
                .map(this::setTaskStatus)
                .collect(Collectors.toList());
    }

    private Task setTaskStatus(Task task) {
        LocalDate currentDate = LocalDate.now();
        TaskStatus status;

        if (task.getType() == TaskType.DATA) {
            status = task.getDueDate().isBefore(currentDate) ? TaskStatus.ATRASADA :
                    task.getDueDate().isEqual(currentDate) ? TaskStatus.PREVISTA :
                            TaskStatus.CONCLUIDA;
        } else {
            status = task.getCompleted() ? TaskStatus.CONCLUIDA : TaskStatus.PREVISTA;
        }

        task.setStatus(status);
        return task;
    }

    private void validarDataDeVencimento(Task tarefa) {
        if (tarefa.getType() == TaskType.DATA && tarefa.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("O vencimento das tarefas do tipo 'Data' deve ser igual ou após a data atual.");
        }
    }
}
