/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.dto;

import  com.r2m.praticar.taskapplication.models.User;


/**
 *
 * @author caioq
 */
public record RequestTaskDTO (
        String title, 
        String description, 
        boolean completad,
        Long id,
        User user
) {}