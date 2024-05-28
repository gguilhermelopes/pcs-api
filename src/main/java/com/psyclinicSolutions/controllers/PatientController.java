package com.psyclinicSolutions.controllers;

import com.psyclinicSolutions.dto.PatientDTO;
import com.psyclinicSolutions.services.PatientService;
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
@RequestMapping(value = "/patients")
public class PatientController {

    @Autowired
    private PatientService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll(){
        List<PatientDTO> employeeList = service.findAll();
        return ResponseEntity.ok(employeeList);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/paged")
    public ResponseEntity<Page<PatientDTO>> findAllPaged(Pageable pageable){
        Page<PatientDTO> list = service.findAllPaged(pageable);

        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable UUID id){
        PatientDTO obj = service.findById(id);

        return ResponseEntity.ok(obj);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseEntity<PatientDTO> insert(@Valid @RequestBody PatientDTO data){
        data = service.insert(data);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(data.id()).toUri();

        return ResponseEntity.created(uri).body(data);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PatientDTO> update(@PathVariable UUID id, @Valid @RequestBody PatientDTO data){
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
