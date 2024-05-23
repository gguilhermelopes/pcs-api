package com.psyclinicSolutions.services;

import com.psyclinicSolutions.domain.Role;

import com.psyclinicSolutions.dto.RoleDTO;
import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import com.psyclinicSolutions.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional(readOnly = true)
    public List<RoleDTO> findAll() {
        List<Role> list = repository.findAll();
        return list.stream().map(RoleDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Page<RoleDTO> findAllPaged(Pageable pageable) {
        Page<Role> pagedList = repository.findAll(pageable);
        return pagedList.map(RoleDTO::new);
    }

    @Transactional(readOnly = true)
    public RoleDTO findById(UUID id) {
        Optional<Role> obj = repository.findById(id);
        Role entity = obj.orElseThrow(() -> new DataNotFoundException("Usuário não encontrado."));

        return new RoleDTO(entity);
    }

    @Transactional
    public RoleDTO insert(RoleDTO data) {
        Role entity = new Role();
        dataToRole(data, entity);
        entity = repository.save(entity);

        return new RoleDTO(entity);
    }
    @Transactional
    public RoleDTO update(UUID id, RoleDTO data) {
        try{
            Role obj = repository.getReferenceById(id);
            dataToRole(data, obj);
            obj = repository.save(obj);
            return new RoleDTO(obj);
        } catch (EntityNotFoundException exception){
            throw new DataNotFoundException("Usuário não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new DataNotFoundException("Usuário não encontrado.");
        }
        try{
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception){
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    private void dataToRole(RoleDTO data, Role entity) {

       entity.setAuthority(data.authority());
    }



}
