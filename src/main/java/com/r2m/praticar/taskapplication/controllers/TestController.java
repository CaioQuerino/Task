package com.r2m.praticar.taskapplication.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello World! API está funcionando.";
    }
    
    @GetMapping("/check-db")
    public String checkDatabase() {
        try {
            return "Database connection OK";
        } catch (Exception e) {
            return "Database connection FAILED: " + e.getMessage();
        }
    }
}