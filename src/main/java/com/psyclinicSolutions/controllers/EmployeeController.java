package com.psyclinicSolutions.controllers;

import com.psyclinicSolutions.dto.EmployeeDTO;
import com.psyclinicSolutions.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findAll(){
        List<EmployeeDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/paged")
    public ResponseEntity<Page<EmployeeDTO>> findAllPaged(Pageable pageable){
        Page<EmployeeDTO> list = service.findAllPaged(pageable);

        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable UUID id){
        EmployeeDTO obj = service.findById(id);

        return ResponseEntity.ok(obj);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeDTO> insert(@Valid @RequestBody EmployeeDTO data){
        data = service.insert(data);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(data.id()).toUri();

        return ResponseEntity.created(uri).body(data);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable UUID id, @Valid @RequestBody EmployeeDTO data){
        data = service.update(id, data);

        return ResponseEntity.ok(data);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
