package com.r2m.praticar.taskapplication.dto;

import com.r2m.praticar.taskapplication.Enums.Gender;

public record RegisterRequestClientDTO(
    String name, 
    String email, 
    String password, 
    String telephone,                                     
    Gender gender,
    String zipCode 
) {}