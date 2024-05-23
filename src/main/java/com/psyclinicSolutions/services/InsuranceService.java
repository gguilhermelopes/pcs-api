package com.psyclinicSolutions.services;

import com.psyclinicSolutions.domain.Insurance;
import com.psyclinicSolutions.dto.InsuranceDTO;
import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import com.psyclinicSolutions.repositories.InsuranceRepository;
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
public class InsuranceService {

    @Autowired
    private InsuranceRepository repository;

    @Transactional(readOnly = true)
    public List<InsuranceDTO> findAll() {
        List<Insurance> list = repository.findAll();
        return list.stream().map(InsuranceDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Page<InsuranceDTO> findAllPaged(Pageable pageable) {
        Page<Insurance> pagedList = repository.findAll(pageable);
        return pagedList.map(InsuranceDTO::new);
    }

    @Transactional(readOnly = true)
    public InsuranceDTO findById(UUID id) {
        Optional<Insurance> obj = repository.findById(id);
        Insurance entity = obj.orElseThrow(() -> new DataNotFoundException("Seguro não encontrado."));

        return new InsuranceDTO(entity);
    }

    @Transactional
    public InsuranceDTO insert(InsuranceDTO data) {
        Insurance entity = new Insurance();
        dataToInsurance(data, entity);
        entity = repository.save(entity);

        return new InsuranceDTO(entity);
    }
    @Transactional
    public InsuranceDTO update(UUID id, InsuranceDTO data) {
        try{
            Insurance obj = repository.getReferenceById(id);
            dataToInsurance(data, obj);
            obj = repository.save(obj);
            return new InsuranceDTO(obj);
        } catch (EntityNotFoundException exception){
            throw new DataNotFoundException("Seguro não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new DataNotFoundException("Seguro não encontrado.");
        }
        try{
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception){
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    private void dataToInsurance(InsuranceDTO data, Insurance entity) {
        entity.setName(data.name());
        entity.setCnpj(data.cnpj());
        entity.setContact(data.contact());
        entity.setEmail(data.email());
        entity.setAddress(data.address());
        entity.setPhone(data.phone());
        entity.setCellphone(data.cellphone());
    }



}
