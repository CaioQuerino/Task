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
@Table(name = "admins")
public class Admin extends User {
    public Admin() {}

    public Admin(UUID id, String name, String email, UserRole role, 
            String password, boolean action, Address address, String telephone, 
                                                      Gender gender, Task task) 
    {
        super(id, name, email, role = UserRole.ADMIN, password, action, address, 
                                                  telephone, gender, task);
    }
}
