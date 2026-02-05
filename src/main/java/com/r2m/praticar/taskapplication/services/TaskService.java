/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.services;


import com.r2m.praticar.taskapplication.dto.RequestTaskDTO;
import com.r2m.praticar.taskapplication.models.Task;
import com.r2m.praticar.taskapplication.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public Task addTask(RequestTaskDTO request) {
        try {
            if (request.title() == null || request.title().isBlank()) {
                throw new IllegalArgumentException("Erro: Título não pode ser nulo.");
            }
            if (request.description() == null || request.description().isBlank()) {
                throw new IllegalArgumentException("Erro: Descrição não pode ser nula.");
            }

            Task task = new Task();
            task.setTitle(request.title());
            task.setDescription(request.description());
            task.setCompletad(request.completad());
            
            if (request.user() != null) {
                task.setUser(request.user());
            }

            Task savedTask = taskRepository.save(task);
            IO.println("Tarefa salva com sucesso! ID: " + savedTask.getId());
            return savedTask;

        } catch (IllegalArgumentException e) {
            IO.println("Erro de validação: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            IO.println("Ocorreu um erro inesperado: " + e.getMessage());
            throw new RuntimeException("Erro ao criar tarefa: " + e.getMessage());
        }
    }
    
    public List<Task> listTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            IO.println("Erro ao listar tarefas: " + e.getMessage());
            throw new RuntimeException("Erro ao listar tarefas: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("null")
    public void alterTask(RequestTaskDTO request) {
        try {
            if (request.id() == null) {
                throw new IllegalArgumentException("Erro: Id da tarefa não pode ser nulo.");
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
            IO.println("Tarefa atualizada com sucesso! ID: " + task.getId());

        } catch (IllegalArgumentException e) {
            IO.println("Erro de validação: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            IO.println("Erro: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            IO.println("Ocorreu um erro inesperado: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("null")
    public void deleteTask(RequestTaskDTO request) {
        try {
            if (request.id() == null) {
                throw new IllegalArgumentException("Erro: Id da tarefa não pode ser nulo.");
            }

            if (!taskRepository.existsById(request.id())) {
                throw new RuntimeException("Erro: Tarefa não existe.");
            }

            taskRepository.deleteById(request.id());
            IO.println("Tarefa deletada com sucesso. Id: " + request.id());

        } catch (IllegalArgumentException e) {
            IO.println("Erro de validação: " + e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            IO.println("Erro: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            IO.println("Ocorreu um erro inesperado: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar tarefa: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("null")
    public Task getTaskById(UUID id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }
}