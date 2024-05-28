package com.psyclinicSolutions.controllers;

import com.psyclinicSolutions.dto.SessionDTO;
import com.psyclinicSolutions.services.SessionService;
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
@RequestMapping(value = "/sessions")
public class SessionController {

    @Autowired
    private SessionService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<SessionDTO>> findAll(){
        List<SessionDTO> employeeList = service.findAll();
        return ResponseEntity.ok(employeeList);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/paged")
    public ResponseEntity<Page<SessionDTO>> findAllPaged(Pageable pageable){
        Page<SessionDTO> list = service.findAllPaged(pageable);

        return ResponseEntity.ok(list);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<SessionDTO> findById(@PathVariable UUID id){
        SessionDTO obj = service.findById(id);

        return ResponseEntity.ok(obj);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseEntity<SessionDTO> insert(@Valid @RequestBody SessionDTO data){
        data = service.insert(data);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(data.id()).toUri();

        return ResponseEntity.created(uri).body(data);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<SessionDTO> update(@PathVariable UUID id, @Valid @RequestBody SessionDTO data){
        data = service.update(id, data);

        return ResponseEntity.ok(data);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
