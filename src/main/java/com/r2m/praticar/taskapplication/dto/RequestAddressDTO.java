/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.dto;

/**
 *
 * @author caioq
 */
public record RequestAddressDTO (
    Long id,
    String zipCode, 
    String neighborhood, 
    String road,
    String state, 
    String city, 
    String complement, 
    Integer number        
) {}