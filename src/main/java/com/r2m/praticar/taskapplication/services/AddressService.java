/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.services;

import com.r2m.praticar.taskapplication.dto.RequestAddressDTO;
import com.r2m.praticar.taskapplication.models.Address;
import com.r2m.praticar.taskapplication.repositories.AddressRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author caioq
 */

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    
    public void addAddress(RequestAddressDTO request) {
        try {
            if (request.zipCode() == null || request.zipCode().isBlank()) {
                throw new NullPointerException("Erro: CEP não pode ser nulo.");
            }
            
            Address address = new Address(
                    request.zipCode(),
                    request.neighborhood(),
                    request.road(),
                    request.city(),
                    request.state(),
                    request.complement(),
                    request.number()
            );
            
            addressRepository.save(address);
            IO.println("Endereço salvo com sucesso!");
        } catch (NullPointerException e) {
            IO.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            IO.println("Erro inesperado: " + e.getMessage());
        }
    }
    
    public void alterAddress(RequestAddressDTO request) {
        try {
            if (request.id() == null) {
                throw new NullPointerException("Erro: Id do endereço não pode ser nulo.");
            }

            Address address = addressRepository.findById(request.id())
                    .orElseThrow(() -> new RuntimeException("Erro: Endereço não encontrado."));

            if (request.zipCode() != null && !request.zipCode().isBlank()) {
                address.setZipCode(request.zipCode());
            }
            if (request.neighborhood() != null && !request.neighborhood().isBlank()) {
                address.setNeighborhood(request.neighborhood());
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
            address.setNumber(request.number());

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
}