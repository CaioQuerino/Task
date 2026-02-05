/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.dto;

import java.util.UUID;

/**
 *
 * @author caioq
 */
public record ResponseTaskDTO(
        String title, 
        String description, 
        boolean completad,
        UUID id
) {}