package com.labdessoft.roteiro01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Optional<Task> obterTarefaPorId(Long id) {
        return taskRepository.findById(id).map(this::atualizarStatusDaTarefa);
    }

    public Task criarTarefa(Task tarefa) {
        validarDataDeVencimento(tarefa);
        Task tarefaSalva = taskRepository.save(tarefa);
        return atualizarStatusDaTarefa(tarefaSalva);
    }

    public Optional<Task> atualizarTarefa(Long id, Task tarefaAtualizada) {
        return taskRepository.findById(id)
                             .map(tarefaExistente -> atualizarPropriedades(tarefaExistente, tarefaAtualizada));
    }

    public void deletarTarefa(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> obterTodasTarefas() {
        List<Task> tarefas = taskRepository.findAll();
        return tarefas.stream().map(this::atualizarStatusDaTarefa).toList();
    }

    private Task atualizarStatusDaTarefa(Task task) {
        switch (task.getType()) {
            case DATA -> atualizarStatusData(task);
            case PRAZO -> {}
            default -> task.setStatus(task.getCompleted() ? TaskStatus.CONCLUIDA : TaskStatus.PREVISTA);
        }
        return task;
    }

    private void atualizarStatusData(Task task) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = task.getDueDate();
        if (dueDate.isBefore(currentDate)) {
            task.setStatus(TaskStatus.ATRASADA);
        } else if (dueDate.isEqual(currentDate)) {
            task.setStatus(TaskStatus.PREVISTA);
        } else {
            task.setStatus(TaskStatus.CONCLUIDA);
        }
    }

    private void validarDataDeVencimento(Task tarefa) {
        if (tarefa.getType() == TaskType.DATA && tarefa.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("o prazo para tarefas do tipo 'Data' deve ser igual ou após o prazo atual.");
        }
    }

    private Task atualizarPropriedades(Task tarefaExistente, Task tarefaAtualizada) {
        tarefaExistente.setDescription(tarefaAtualizada.getDescription());
        tarefaExistente.setCompleted(tarefaAtualizada.getCompleted());
        tarefaExistente.setType(tarefaAtualizada.getType());
        tarefaExistente.setDeadlineInDays(tarefaAtualizada.getDeadlineInDays());
        tarefaExistente.setDueDate(tarefaAtualizada.getDueDate());
        tarefaExistente.setPriority(tarefaAtualizada.getPriority());
        return atualizarStatusDaTarefa(tarefaExistente);
    }
}
