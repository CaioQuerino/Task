package com.r2m.praticar.taskapplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.r2m.praticar.taskapplication.dto.RequestTaskDTO;
import com.r2m.praticar.taskapplication.models.Task;
import com.r2m.praticar.taskapplication.repositories.TaskRepository;
import com.r2m.praticar.taskapplication.services.ApiResponse;
import com.r2m.praticar.taskapplication.services.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTask(@RequestBody RequestTaskDTO request) {
        try {
            Task task = taskService.addTask(request);
            if (task == null) {
                ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Falha ao criar tarefa",
                    null
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            ApiResponse response = new ApiResponse(
                HttpStatus.CREATED.value(),
                "Tarefa criada com sucesso",
                task
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro ao criar tarefa: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> listTasks() {
        try {
            List<Task> tasks = taskService.listTasks();
            ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                "Tarefas listadas com sucesso",
                tasks
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro ao listar tarefas: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> alterTask(
            @PathVariable UUID id,
            @RequestBody RequestTaskDTO request) {
        try {
            // Cria um novo DTO com o ID do path parameter
            RequestTaskDTO updatedRequest = new RequestTaskDTO(
                request.title(),
                request.description(),
                request.completad(),
                id,
                request.user()
            );
            
            taskService.alterTask(updatedRequest);
            
            ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                "Tarefa atualizada com sucesso",
                id
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno ao atualizar tarefa",
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            RequestTaskDTO request = new RequestTaskDTO(null, null, false, uuid, null);
            taskService.deleteTask(request);
            
            ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                "Tarefa deletada com sucesso",
                null
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                "ID inválido: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.value(),
                "Erro ao deletar tarefa: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro ao deletar tarefa: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @SuppressWarnings("null")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTaskById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Task task = taskRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
                
            ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                "Tarefa encontrada",
                task
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                "ID inválido: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro ao buscar tarefa: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}