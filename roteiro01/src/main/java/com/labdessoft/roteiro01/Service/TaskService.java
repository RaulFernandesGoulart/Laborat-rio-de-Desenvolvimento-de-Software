package com.labdessoft.roteiro01.Service;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.entity.TaskType;
import com.labdessoft.roteiro01.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public EntyTask createTask(EntyTask task) {
        // Verifica se o tipo de tarefa está definido
        if (task.getTaskType() == null) {
            // Define um valor padrão para o tipo de tarefa, se necessário
            task.setTaskType(TaskType.DATA.ordinal()); // Por exemplo, definindo como tipo "Data"
        }
        // Salva a tarefa no banco de dados
        return taskRepository.save(task);
    }

    public EntyTask getTaskById(Long taskId) {
        // Recupera a tarefa do banco de dados
        return taskRepository.findById(taskId).orElse(null);
    }

    public List<EntyTask> getAllTasks() {
        return taskRepository.findAll();
    }

    // Métodos abaixo precisam ser implementados
    public List<EntyTask> gerenciarTarefas() {
        // Implementar a lógica para gerenciar tarefas
        // Exemplo: listar todas as tarefas do banco de dados
        return taskRepository.findAll();
    }

    public List<EntyTask> concluirTarefas() {
        // Implementar a lógica para concluir tarefas
        // Exemplo: listar todas as tarefas concluídas do banco de dados
        return taskRepository.findByCompleted(true);
    }

    public List<EntyTask> priorizarTarefas() {
        // Implementar a lógica para priorizar tarefas
        // Exemplo: listar todas as tarefas priorizadas do banco de dados
        // Considere adicionar um campo na entidade Task para indicar a prioridade
        // Exemplo: return taskRepository.findByPriority("high");
        return taskRepository.findAll(); // Exemplo com lista completa (sem priorização)
    }

    public List<EntyTask> categorizarTarefas() {
        // Implementar a lógica para categorizar tarefas
        // Exemplo: listar todas as tarefas categorizadas do banco de dados
        // Considere adicionar um campo na entidade Task para indicar a categoria
        // Exemplo: return taskRepository.findByCategory("work");
        return taskRepository.findAll(); // Exemplo com lista completa (sem categorização)
    }
}