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







/*import java.util.ArrayList;
import java.util.List;

public class Tarefa {
    private String descricao;
    private boolean concluida;

    public Tarefa(String descricao) {
        this.descricao = descricao;
        this.concluida = false; // Por padrão, a tarefa é inicializada como não concluída
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void marcarConcluida() {
        this.concluida = true;
    }

    public static List<Tarefa> getListarTarefas() {
        // Aqui você pode substituir isso por uma chamada ao banco de dados ou outra fonte de dados
        List<Tarefa> tarefas = new ArrayList<>();

        // Adicionando algumas tarefas de exemplo
        tarefas.add(new Tarefa("Estudar para a prova de matemática"));
        tarefas.add(new Tarefa("Fazer compras no mercado"));
        tarefas.add(new Tarefa("Ligar para o cliente X"));

        return tarefas;
    }

    public static void main(String[] args) {
        List<Tarefa> tarefas = Tarefa.getListarTarefas();

        System.out.println("Lista de Tarefas:");
        for (Tarefa tarefa : tarefas) {
            System.out.println("- " + tarefa.getDescricao());
        }
    }
}
*/
