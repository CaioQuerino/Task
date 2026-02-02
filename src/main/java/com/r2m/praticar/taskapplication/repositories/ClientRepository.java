package com.r2m.praticar.taskapplication.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.r2m.praticar.taskapplication.models.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean findByEmail(String email);
}