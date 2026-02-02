package com.r2m.praticar.taskapplication.services;

import java.util.UUID;

import com.r2m.praticar.taskapplication.Enums.UserRole;
import com.r2m.praticar.taskapplication.configs.SecurityConfig;
import com.r2m.praticar.taskapplication.dto.RegisterRequestClientDTO;
import com.r2m.praticar.taskapplication.exceptions.BusinessException;
import com.r2m.praticar.taskapplication.exceptions.ExternalServiceException;
import com.r2m.praticar.taskapplication.exceptions.ValidationException;
import com.r2m.praticar.taskapplication.models.Address;
import com.r2m.praticar.taskapplication.models.Client;
import com.r2m.praticar.taskapplication.repositories.ClientRepository;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final SecurityConfig securityConfig;
    private final AddressService addressService;
    
    private static final String CLIENT_ALREADY_EXISTS = "Já existe um cliente cadastrado com o email: ";

    @SuppressWarnings("unused")
    private static final String INVALID_REQUEST = "Dados de requisição inválidos";
    
    private static final String ADDRESS_SERVICE_ERROR = "Erro ao consultar serviço de endereço";
    private static final String DATABASE_ERROR = "Erro ao persistir dados do cliente";
    private static final String VALIDATION_ERROR = "Erro de validação: ";
    
    private static final String CLIENT_REGISTERED_SUCCESS = "Cliente cadastrado com sucesso";
    private static final String CLIENT_ACTIVATED_SUCCESS = "Cliente ativado com sucesso";

    public ClientService(ClientRepository clientRepository, 
                        AddressService addressService,
                        SecurityConfig securityConfig) {
        this.clientRepository = clientRepository;
        this.addressService = addressService;
        this.securityConfig = securityConfig;
    }

    @Transactional
    public ResponseEntity<ApiResponse> registerUser(RegisterRequestClientDTO request) {
        try {
            validateRequest(request);
            
            checkIfClientExists(request.email());
            
            Client client = createClientFromRequest(request);
            
            Address address = fetchAddress(request.zipCode());
            client.setAddress(address);
            
            saveClient(client);
            
            ApiResponse response = new ApiResponse(
                HttpStatus.CREATED.value(),
                CLIENT_REGISTERED_SUCCESS,
                client.getId()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (ValidationException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                VALIDATION_ERROR + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (BusinessException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            
        } catch (ExternalServiceException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.BAD_GATEWAY.value(),
                ADDRESS_SERVICE_ERROR + ": " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
            
        } catch (DataAccessException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                DATABASE_ERROR + ": " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
            
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @SuppressWarnings("null")
    @Transactional
    public ResponseEntity<ApiResponse> activateClient(UUID clientId) {
        try {
            Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado", null));
            
            if (client.isAction()) {
                ApiResponse response = new ApiResponse(
                    HttpStatus.OK.value(),
                    "Cliente já está ativo",
                    clientId
                );
                return ResponseEntity.ok(response);
            }
            
            client.setAction(true);
            clientRepository.save(client);
            
            ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                CLIENT_ACTIVATED_SUCCESS,
                clientId
            );
            return ResponseEntity.ok(response);
            
        } catch (BusinessException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro ao ativar cliente: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @SuppressWarnings("null")
    public ResponseEntity<ApiResponse> getClientById(UUID clientId) {
        try {
            Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado", null));
            
            ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                "Cliente encontrado",
                client
            );
            return ResponseEntity.ok(response);
            
        } catch (BusinessException e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro ao buscar cliente: " + e.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private void validateRequest(RegisterRequestClientDTO request) {
        if (request == null) {
            throw new ValidationException("Request não pode ser nulo");
        }

        validateField(request.name(), "nome");
        validateField(request.email(), "email");
        validateField(request.telephone(), "telefone");
        validateField(request.password(), "senha");
        validateField(request.gender(), "gênero");
        validateField(request.zipCode(), "CEP");
        
        if (!isValidEmail(request.email())) {
            throw new ValidationException("Email inválido: " + request.email());
        }
        
        if (request.password().length() < 6) {
            throw new ValidationException("Senha deve ter pelo menos 6 caracteres");
        }
    }

    private void validateField(Object field, String fieldName) {
        if (field == null) {
            throw new ValidationException(String.format("O campo %s não pode ser nulo", fieldName));
        }
        
        if (field instanceof String && ((String) field).trim().isEmpty()) {
            throw new ValidationException(String.format("O campo %s não pode ser vazio", fieldName));
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }

    private void checkIfClientExists(String email) {
        boolean exists = clientRepository.findByEmail(email);
        if (exists) {
            throw new BusinessException(CLIENT_ALREADY_EXISTS + email, null);
        }
    }

    private Client createClientFromRequest(RegisterRequestClientDTO request) {
        Client client = new Client();
        client.setId(UUID.randomUUID());
        client.setName(request.name().trim());
        client.setEmail(request.email().trim().toLowerCase());
        client.setTelephone(request.telephone().trim());
        client.setPassword(securityConfig.BCryptPasswordEncoder().encode(request.password()));
        client.setGender(request.gender());
        client.setRole(UserRole.CLIENT);
        client.setAction(false);
        
        return client;
    }

    

    private Address fetchAddress(String zipCode) {
        try {
            return addressService.getAddressByCEP(zipCode);
        } catch (Exception e) {
            throw new ExternalServiceException(
                String.format("Erro ao buscar endereço para o CEP: %s", zipCode), 
                e
            );
        }
    }

    @SuppressWarnings("null")
    private void saveClient(Client client) {
        try {
            clientRepository.save(client);
        } catch (DataAccessException e) {
            throw new BusinessException(
                String.format("%s: %s", DATABASE_ERROR, e.getMessage()), null);
        }
    }
}