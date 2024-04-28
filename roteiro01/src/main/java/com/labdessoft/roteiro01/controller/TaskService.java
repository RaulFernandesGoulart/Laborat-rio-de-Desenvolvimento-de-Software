package com.labdessoft.roteiro01.controller;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task obterTarefaPorId(Long id) {
        return taskRepository.findById(id)
                            .map(this::atualizarStatusDaTarefa)
                            .orElse(null);
    }

    public Task criarTarefa(Task tarefa) {
        validarDataDeVencimento(tarefa);
        Task tarefaSalva = taskRepository.save(tarefa);
        return atualizarStatusDaTarefa(tarefaSalva);
    }

    public Task atualizarTarefa(Long id, Task tarefaAtualizada) {
        return taskRepository.findById(id)
                             .map(tarefaExistente -> {
                                 atualizarPropriedades(tarefaExistente, tarefaAtualizada);
                                 return taskRepository.save(tarefaExistente);
                             })
                             .orElse(null);
    }

    public void deletarTarefa(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> obterTodasTarefas() {
        List<Task> tarefas = taskRepository.findAll();
        return tarefas.stream()
                      .map(this::atualizarStatusDaTarefa)
                      .toList();
    }

    private Task atualizarStatusDaTarefa(Task task) {
        switch (task.getType()) {
            case DATA:
                atualizarStatusData(task);
                break;
            case PRAZO:
                // Lógica similar para tarefas do tipo Prazo
                break;
            default:
                task.setStatus(task.getCompleted() ? TaskStatus.CONCLUIDA : TaskStatus.PREVISTA);
        }
        return task;
    }

    private void atualizarStatusData(Task task) {
        LocalDate currentDate = LocalDate.now();
        if (task.getDueDate().isBefore(currentDate)) {
            task.setStatus(TaskStatus.ATRASADA);
        } else if (task.getDueDate().isEqual(currentDate)) {
            task.setStatus(TaskStatus.PREVISTA);
        } else {
            task.setStatus(TaskStatus.CONCLUIDA);
        }
    }

    private void validarDataDeVencimento(Task tarefa) {
        if (tarefa.getType() == TaskType.DATA && tarefa.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data de vencimento para tarefas do tipo 'Data' deve ser igual ou após a data atual.");
        }
    }

    private void atualizarPropriedades(Task tarefaExistente, Task tarefaAtualizada) {
        tarefaExistente.setDescription(tarefaAtualizada.getDescription());
        tarefaExistente.setCompleted(tarefaAtualizada.getCompleted());
        tarefaExistente.setType(tarefaAtualizada.getType());
        tarefaExistente.setDeadlineInDays(tarefaAtualizada.getDeadlineInDays());
        tarefaExistente.setDueDate(tarefaAtualizada.getDueDate());
        tarefaExistente.setPriority(tarefaAtualizada.getPriority());
        atualizarStatusDaTarefa(tarefaExistente);
    }
}
