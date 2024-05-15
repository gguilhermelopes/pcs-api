package com.psyclinicSolutions.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @GetMapping
    public ResponseEntity<Void> findAll(){
        System.out.println("Accessing /employees endpoint");
        return ResponseEntity.ok().build();
    }
}
