package com.psyclinicSolutions.services;

import com.psyclinicSolutions.domain.Employee;
import com.psyclinicSolutions.dtos.EmployeeDTO;
import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import com.psyclinicSolutions.repositories.EmployeeRepository;
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
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAll() {
        List<Employee> list = repository.findAll();
        return list.stream().map(EmployeeDTO::new).toList();
    }
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAllPaged(Pageable pageable) {
        Page<Employee> pagedList = repository.findAll(pageable);
        return pagedList.map(EmployeeDTO::new);
    }
    @Transactional(readOnly = true)
    public EmployeeDTO findById(UUID id) {
        Optional<Employee> obj = repository.findById(id);
        Employee entity = obj.orElseThrow(() -> new DataNotFoundException("Funcionário não encontrado."));

        return new EmployeeDTO(entity);
    }

    @Transactional
    public EmployeeDTO insert(EmployeeDTO data) {
        Employee entity = new Employee();
        dataToEmployee(data, entity);
        entity = repository.save(entity);

        return new EmployeeDTO(entity);
    }
    @Transactional
    public EmployeeDTO update(UUID id, EmployeeDTO data) {
        try{
            Employee obj = repository.getReferenceById(id);
            dataToEmployee(data, obj);
            obj = repository.save(obj);
            return new EmployeeDTO(obj);
        } catch (EntityNotFoundException exception){
            throw new DataNotFoundException("Funcionário não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new DataNotFoundException("Funcionário não encontrado.");
        }
        try{
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception){
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    private void dataToEmployee(EmployeeDTO data, Employee entity) {
        entity.setName(data.name());
        entity.setImgUrl(data.imgUrl());
        entity.setCpf(data.cpf());
        entity.setEmail(data.email());
        entity.setAddress(data.address());
        entity.setPhone(data.phone());
        entity.setCellphone(data.cellphone());
        entity.setEmergencyContact(data.emergencyContact());
        entity.setEmergencyContactPhone(data.emergencyContactPhone());
        entity.setRole(data.role());
    }



}
