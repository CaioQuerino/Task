package com.r2m.praticar.taskapplication.dto;

import java.util.UUID;

import com.r2m.praticar.taskapplication.Enums.Gender;

public record AlterRegisterRequestClientDTO(
    UUID id,
    String name, 
    String email, 
    String password, 
    String telephone,                                     
    Gender gender,
    String zipCode,
    Integer number,
    String complement
) {}