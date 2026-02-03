package com.r2m.praticar.taskapplication.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.r2m.praticar.taskapplication.models.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Client c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") String email);
    
    boolean findByEmail(String email);
}