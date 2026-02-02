/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r2m.praticar.taskapplication.dto.RequestAddressDTO;
import com.r2m.praticar.taskapplication.models.Address;
import com.r2m.praticar.taskapplication.repositories.AddressRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author caioq
 */

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    private static final String VIACEP_BASE_URL = "https://viacep.com.br/ws/";
    private static final String VIACEP_FORMAT = "/json/";

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    private Address fetchAddressFromViaCEP(String zipCode) {
        try {
            String url = VIACEP_BASE_URL + zipCode + VIACEP_FORMAT;
            String response = restTemplate.getForObject(url, String.class);
            
            JsonNode jsonNode = objectMapper.readTree(response);
            
            if (jsonNode.has("erro") && jsonNode.get("erro").asBoolean()) {
                throw new RuntimeException("CEP não encontrado: " + zipCode);
            }
            
            String cep = jsonNode.get("cep") != null ? jsonNode.get("cep").asText().replace("-", "") : "";
            String bairro = jsonNode.get("bairro") != null ? jsonNode.get("bairro").asText() : "";
            String logradouro = jsonNode.get("logradouro") != null ? jsonNode.get("logradouro").asText() : "";
            String localidade = jsonNode.get("localidade") != null ? jsonNode.get("localidade").asText() : "";
            String uf = jsonNode.get("uf") != null ? jsonNode.get("uf").asText() : "";
            String complemento = jsonNode.get("complemento") != null ? jsonNode.get("complemento").asText() : "";
            
            return new Address(
                cep,
                bairro,
                logradouro,
                localidade,
                uf,
                complemento,
                null
            );
        } catch (JsonProcessingException | RuntimeException e) {
            throw new RuntimeException("Erro ao consultar ViaCEP: " + e.getMessage());
        }
    }
    
    public void addAddress(RequestAddressDTO request) {
        try {
            if (request.zipCode() == null || request.zipCode().isBlank()) {
                throw new NullPointerException("Erro: CEP não pode ser nulo.");
            }
            
            Address address;
            
            if ((request.neighborhood() == null || request.neighborhood().isBlank()) &&
                (request.road() == null || request.road().isBlank()) &&
                (request.city() == null || request.city().isBlank()) &&
                (request.state() == null || request.state().isBlank())) {
                
                address = fetchAddressFromViaCEP(request.zipCode());
                address.setComplement(request.complement());
                address.setNumber(request.number());
            } else {
                address = new Address(
                    request.zipCode(),
                    request.neighborhood(),
                    request.road(),
                    request.city(),
                    request.state(),
                    request.complement(),
                    request.number()
                );
            }
            
            addressRepository.save(address);
            IO.println("Endereço salvo com sucesso!");
        } catch (NullPointerException e) {
            IO.println("Erro: " + e.getMessage());
        } catch (RuntimeException e) {
            IO.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            IO.println("Erro inesperado: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("null")
    public void alterAddress(RequestAddressDTO request) {
        try {
            if (request.id() == null) {
                throw new NullPointerException("Erro: Id do endereço não pode ser nulo.");
            }

            @SuppressWarnings("null")
            Address address = addressRepository.findById(request.id())
                    .orElseThrow(() -> new RuntimeException("Erro: Endereço não encontrado."));

            if (request.zipCode() != null && !request.zipCode().isBlank()) {
                // Se o CEP foi alterado e outros campos estão vazios, busca no ViaCEP
                if ((request.neighborhood() == null || request.neighborhood().isBlank()) &&
                    (request.road() == null || request.road().isBlank()) &&
                    (request.city() == null || request.city().isBlank()) &&
                    (request.state() == null || request.state().isBlank())) {
                    
                    Address viaCEPAddress = fetchAddressFromViaCEP(request.zipCode());
                    address.setZipCode(viaCEPAddress.getZipCode());
                    address.setNeighborhood(viaCEPAddress.getNeighborhood());
                    address.setRoad(viaCEPAddress.getRoad());
                    address.setCity(viaCEPAddress.getCity());
                    address.setState(viaCEPAddress.getState());
                } else {
                    address.setZipCode(request.zipCode());
                }
            }
            
            if (request.neighborhood() != null && !request.neighborhood().isBlank()) {
                address.setNeighborhood(request.neighborhood());
            }
            if (request.road() != null && !request.road().isBlank()) {
                address.setRoad(request.road());
            }
            if (request.city() != null && !request.city().isBlank()) {
                address.setCity(request.city());
            }
            if (request.state() != null && !request.state().isBlank()) {
                address.setState(request.state());
            }
            if (request.complement() != null) {
                address.setComplement(request.complement());
            }
            if (request.number() != null) {
                address.setNumber(request.number());
            }

            addressRepository.save(address);
            IO.println("Endereço atualizado com sucesso!");
        } catch (NullPointerException e) {
            IO.println("Erro: " + e.getMessage());
        } catch (RuntimeException e) {
            IO.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            IO.println("Erro inesperado: " + e.getMessage());
        }
    }
    
    // Método adicional para buscar apenas pelo CEP
    public Address getAddressByCEP(String zipCode) {
        return fetchAddressFromViaCEP(zipCode);
    }
}