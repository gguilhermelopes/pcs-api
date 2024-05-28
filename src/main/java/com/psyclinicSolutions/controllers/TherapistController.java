package com.psyclinicSolutions.controllers;

import com.psyclinicSolutions.dto.TherapistDTO;
import com.psyclinicSolutions.services.TherapistService;
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
@RequestMapping(value = "/therapists")
public class TherapistController {

    @Autowired
    private TherapistService service;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<TherapistDTO>> findAll(){
        List<TherapistDTO> employeeList = service.findAll();
        return ResponseEntity.ok(employeeList);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/paged")
    public ResponseEntity<Page<TherapistDTO>> findAllPaged(Pageable pageable){
        Page<TherapistDTO> list = service.findAllPaged(pageable);

        return ResponseEntity.ok(list);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TherapistDTO> findById(@PathVariable UUID id){
        TherapistDTO obj = service.findById(id);

        return ResponseEntity.ok(obj);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<TherapistDTO> insert(@Valid @RequestBody TherapistDTO data){
        data = service.insert(data);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(data.id()).toUri();

        return ResponseEntity.created(uri).body(data);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TherapistDTO> update(@PathVariable UUID id, @Valid @RequestBody TherapistDTO data){
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
