package com.psyclinicSolutions.services;

import com.psyclinicSolutions.domain.Insurance;
import com.psyclinicSolutions.domain.Patient;
import com.psyclinicSolutions.domain.Therapist;
import com.psyclinicSolutions.dtos.PatientDTO;
import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import com.psyclinicSolutions.infra.helpers.FetchObjects;
import com.psyclinicSolutions.repositories.InsuranceRepository;
import com.psyclinicSolutions.repositories.PatientRepository;
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
public class PatientService {

    @Autowired
    private PatientRepository repository;

    @Autowired
    private TherapistRepository therapistRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private FetchObjects fo;
    @Transactional(readOnly = true)
    public List<PatientDTO> findAll() {
        List<Patient> list = repository.findAll();
        return list.stream().map(PatientDTO::new).toList();
    }
    @Transactional(readOnly = true)
    public Page<PatientDTO> findAllPaged(Pageable pageable) {
        Page<Patient> pagedList = repository.findAll(pageable);
        return pagedList.map(PatientDTO::new);
    }
    @Transactional(readOnly = true)
    public PatientDTO findById(UUID id) {
        Optional<Patient> obj = repository.findById(id);
        Patient entity = obj.orElseThrow(() -> new DataNotFoundException("Paciente não encontrado."));

        return new PatientDTO(entity);
    }

    @Transactional
    public PatientDTO insert(PatientDTO data) {
        Patient entity = new Patient();
        dataToPatient(data, entity);
            entity = repository.save(entity);
            return new PatientDTO(entity);
    }

    @Transactional
    public PatientDTO update(UUID id, PatientDTO data) {
        try{
            Patient obj = repository.getReferenceById(id);
            dataToPatient(data, obj);
            obj = repository.save(obj);
            return new PatientDTO(obj);
        } catch (EntityNotFoundException exception){
            throw new DataNotFoundException("Paciente não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new DataNotFoundException("Paciente não encontrado.");
        }
        try{
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception){
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    private void dataToPatient(PatientDTO data, Patient entity) {
        Therapist therapist = fo.fetchObject(data.therapistId(), therapistRepository);
        Insurance insurance = fo.fetchObject(data.insuranceId(), insuranceRepository);

        entity.setName(data.name());
        entity.setTherapist(therapist);
        entity.setInsurance(insurance);
        entity.setImgUrl(data.imgUrl());
        entity.setCpf(data.cpf());
        entity.setEmail(data.email());
        entity.setAddress(data.address());
        entity.setPhone(data.phone());
        entity.setCellphone(data.cellphone());
        entity.setEmergencyContact(data.emergencyContact());
        entity.setEmergencyContactPhone(data.emergencyContactPhone());
        entity.setRecords(data.records());
    }



}
