/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.models;

import com.r2m.praticar.taskapplication.Enums.Gender;
import com.r2m.praticar.taskapplication.Enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 *
 * @author caioq
 */
@Entity
@Table(name = "clients")
public class Client extends User {
    public Client() {}

    public Client(UUID id, String name, String email, UserRole role, 
            String password, boolean action, Address address, String telephone, 
                                                      Gender gender, Task task) 
    {
        super(id, name, email, role = UserRole.CLIENT, password, action, 
                                      address, telephone, gender, task);
    }
}
