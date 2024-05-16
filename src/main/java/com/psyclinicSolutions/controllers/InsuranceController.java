package com.psyclinicSolutions.controllers;

import com.psyclinicSolutions.dtos.InsuranceDTO;
import com.psyclinicSolutions.services.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/insurances")
public class InsuranceController {

    @Autowired
    private InsuranceService service;

    @GetMapping
    public ResponseEntity<List<InsuranceDTO>> findAll(){
        List<InsuranceDTO> employeeList = service.findAll();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<InsuranceDTO>> findAllPaged(Pageable pageable){
        Page<InsuranceDTO> list = service.findAllPaged(pageable);

        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InsuranceDTO> findById(@PathVariable UUID id){
        InsuranceDTO obj = service.findById(id);

        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<InsuranceDTO> insert(@Valid @RequestBody InsuranceDTO data){
        data = service.insert(data);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(data.id()).toUri();

        return ResponseEntity.created(uri).body(data);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<InsuranceDTO> update(@PathVariable UUID id, @Valid @RequestBody InsuranceDTO data){
        data = service.update(id, data);

        return ResponseEntity.ok(data);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
