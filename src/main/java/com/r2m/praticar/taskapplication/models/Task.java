/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 *
 * @author caioq
 */

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String title;
    private String description;
    private boolean completad;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Task() {}

    public Task(UUID id, String title, String description, boolean completad) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.completad = completad;
    }
    
    public Task(String title, String description, boolean completad) {
        this.title = title;
        this.description = description;
        this.completad = completad;
    }

    public UUID getId() {
        return this.id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }

    public void setId(String id) {
        try {
            this.id = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido: " + id + ". "
                                             + "Formato esperado: UUID.");
        }
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return this.description;
    }    
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean getCompletad() {
        return this.completad;
    }
    
    public void setCompletad(boolean completad) {
        this.completad = completad;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}