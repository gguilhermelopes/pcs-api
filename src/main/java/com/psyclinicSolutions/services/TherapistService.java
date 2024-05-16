package com.psyclinicSolutions.services;

import com.psyclinicSolutions.domain.Therapist;
import com.psyclinicSolutions.dtos.TherapistDTO;
import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import com.psyclinicSolutions.repositories.TherapistRepository;
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
public class TherapistService {

    @Autowired
    private TherapistRepository repository;

    public List<TherapistDTO> findAll() {
        List<Therapist> list = repository.findAll();
        return list.stream().map(TherapistDTO::new).toList();
    }

    public Page<TherapistDTO> findAllPaged(Pageable pageable) {
        Page<Therapist> pagedList = repository.findAll(pageable);
        return pagedList.map(TherapistDTO::new);
    }

    public TherapistDTO findById(UUID id) {
        Optional<Therapist> obj = repository.findById(id);
        Therapist entity = obj.orElseThrow(() -> new DataNotFoundException("Terapeuta não encontrado."));

        return new TherapistDTO(entity);
    }

    @Transactional
    public TherapistDTO insert(TherapistDTO data) {
        Therapist entity = new Therapist();
        dataToTherapist(data, entity);
        entity = repository.save(entity);

        return new TherapistDTO(entity);
    }
    @Transactional
    public TherapistDTO update(UUID id, TherapistDTO data) {
        try{
            Therapist obj = repository.getReferenceById(id);
            dataToTherapist(data, obj);
            obj = repository.save(obj);
            return new TherapistDTO(obj);
        } catch (EntityNotFoundException exception){
            throw new DataNotFoundException("Terapeuta não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new DataNotFoundException("Terapeuta não encontrado.");
        }
        try{
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception){
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    private void dataToTherapist(TherapistDTO data, Therapist entity) {
        entity.setName(data.name());
        entity.setCrp(data.crp());
        entity.setEmail(data.email());
        entity.setAddress(data.address());
        entity.setPhone(data.phone());
        entity.setCellphone(data.cellphone());
        entity.setExpertise(data.expertise());
    }



}
