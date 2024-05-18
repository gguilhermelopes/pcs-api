package com.psyclinicSolutions.services;

import com.psyclinicSolutions.domain.Patient;
import com.psyclinicSolutions.domain.Session;
import com.psyclinicSolutions.domain.Therapist;
import com.psyclinicSolutions.dtos.SessionDTO;
import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import com.psyclinicSolutions.infra.helpers.FetchObjects;
import com.psyclinicSolutions.repositories.PatientRepository;
import com.psyclinicSolutions.repositories.SessionRepository;
import com.psyclinicSolutions.repositories.TherapistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {


    @Autowired
    private SessionRepository repository;
    @Autowired
    private TherapistRepository therapistRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private FetchObjects fo;

    @Transactional(readOnly = true)
    public List<SessionDTO> findAll() {
        List<Session> list = repository.findAll();
        return list.stream().map(SessionDTO::new).toList();
    }
    @Transactional(readOnly = true)
    public Page<SessionDTO> findAllPaged(Pageable pageable) {
        Page<Session> pagedList = repository.findAll(pageable);
        return pagedList.map(SessionDTO::new);
    }
    @Transactional(readOnly = true)
    public SessionDTO findById(UUID id) {
        Optional<Session> obj = repository.findById(id);
        Session entity = obj.orElseThrow(() -> new DataNotFoundException("Sessão não encontrada."));

        return new SessionDTO(entity);
    }

    @Transactional
    public SessionDTO insert(SessionDTO data) {
        Session entity = new Session();
        dataToSession(data, entity);
        entity = repository.save(entity);

        return new SessionDTO(entity);
    }
    @Transactional
    public SessionDTO update(UUID id, SessionDTO data) {
        try{
            Session obj = repository.getReferenceById(id);
            dataToSession(data, obj);
            obj = repository.save(obj);
            return new SessionDTO(obj);
        } catch (EntityNotFoundException exception){
            throw new DataNotFoundException("TSessão não encontrada.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new DataNotFoundException("Sessão não encontrada.");
        }
        try{
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception){
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    private void dataToSession(SessionDTO data, Session entity) {
       Therapist therapist = fo.fetchObject(data.therapistId(), therapistRepository);
       Patient patient = fo.fetchObject(data.patientId(), patientRepository);

       entity.setTherapist(therapist);
       entity.setPatient(patient);
       entity.setSessionDate(data.sessionDate());
       entity.setSessionDuration(data.sessionDuration());
       entity.setIsRemote(data.isRemote());
       entity.setIsAuthorized(data.isAuthorized());
       entity.setToken(data.token());
       entity.setAuthorizationDate(data.authorizationDate());
       entity.setHasPatientAttended(data.hasPatientAttended());
       entity.setSessionValue(data.sessionValue());
       entity.setIsPaid(data.isPaid());
       entity.setPaymentDate(data.paymentDate());
       entity.setIsAccounted(data.isAccounted());
       entity.setAccountDate(data.accountDate());
    }





}
