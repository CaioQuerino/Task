package com.r2m.praticar.taskapplication.controllers;

import com.r2m.praticar.taskapplication.dto.RegisterRequestClientDTO;

import com.r2m.praticar.taskapplication.services.ApiResponse;
import com.r2m.praticar.taskapplication.services.ClientService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    
    private final ClientService clientService;
    
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequestClientDTO request) {
        return clientService.registerUser(request);
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse> activateClient(@PathVariable UUID id) {
        return clientService.activateClient(id);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getClient(@PathVariable UUID id) {
        return clientService.getClientById(id);
    }
    
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> healthCheck() {
        ApiResponse response = new ApiResponse(
            200,
            "Service is healthy",
            null
        );
        return ResponseEntity.ok(response);
    }
}