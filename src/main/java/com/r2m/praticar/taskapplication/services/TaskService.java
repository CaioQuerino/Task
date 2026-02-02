/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.services;


import com.r2m.praticar.taskapplication.dto.RequestTaskDTO;
import com.r2m.praticar.taskapplication.models.Task;
import com.r2m.praticar.taskapplication.repositories.TaskRepository;
import java.util.List;


import org.springframework.stereotype.Service;

/**
 *
 * @author caioq
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(RequestTaskDTO request) {
        try {
            if (request.user() == null || request.user().getId() == null) {
                throw new NullPointerException("Erro: Id do usuário nulo.");
            }
            if (request.title() == null || request.title().isBlank()) {
                throw new NullPointerException("Erro: Título não pode ser nulo.");
            }
            if (request.description() == null || request.description().isBlank()) {
                throw new NullPointerException("Erro: Descrição não pode ser nula.");
            }

            Task task = new Task(
                request.title(),
                request.description(),
                request.completad()
            );

            taskRepository.save(task);
            IO.println("Tarefa salva com sucesso!");

        } catch (NullPointerException e) {
           IO.println("Erro: " + e.getMessage());
        } catch (Exception e) {
           IO.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
    
    public List<Task> listTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
           IO.println("Erro ao listar tarefas: " + e.getMessage());
            return List.of();
        }
    } 
    
    @SuppressWarnings("null")
    public void alterTask(RequestTaskDTO request) {
        try {
            if (request.id() == null) {
                throw new NullPointerException("Erro: Id da tarefa não pode ser nulo.");
            }

            if (!taskRepository.existsById(request.id())) {
                throw new RuntimeException("Erro: Id não existe.");
            }

            Task task = taskRepository.findById(request.id())
               .orElseThrow(() -> new RuntimeException("Erro: Tarefa não encontrada."));

            if (request.title() != null && !request.title().isBlank()) {
                task.setTitle(request.title());
            }
            if (request.description() != null && !request.description().isBlank()) {
                task.setDescription(request.description());
            }
            task.setCompletad(request.completad());

            taskRepository.save(task);
            IO.println("Tarefa atualizada com sucesso!");

        } catch (NullPointerException e) {
           IO.println("Erro: " + e.getMessage());
        } catch (RuntimeException e) {
           IO.println("Erro: " + e.getMessage());
        } catch (Exception e) {
           IO.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("null")
    public void deleteTask(RequestTaskDTO request) {
       try {
           if (request.id() == null) {
               throw new NullPointerException("Erro: Id da tarefa não pode ser nulo.");
           }

           if (!taskRepository.existsById(request.id())) {
               throw new RuntimeException("Erro: Id não existe.");
           }

           taskRepository.deleteById(request.id());
           IO.println("Tarefa deletada com sucesso. Id: " + request.id());

       } catch (NullPointerException e) {
           IO.println("Erro: " + e.getMessage());
       } catch (RuntimeException e) {
           IO.println("Erro: " + e.getMessage());
       } catch (Exception e) {
           IO.println("Ocorreu um erro inesperado: " + e.getMessage());
       }
   }
}