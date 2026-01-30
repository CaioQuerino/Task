/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.repositories;

import com.r2m.praticar.taskapplication.models.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 *
 * @author caioq
 */

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {}