package com.psyclinicSolutions.services;

import com.psyclinicSolutions.domain.Patient;
import com.psyclinicSolutions.domain.Session;
import com.psyclinicSolutions.domain.Therapist;
import com.psyclinicSolutions.dto.SessionDTO;
import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import com.psyclinicSolutions.services.helpers.FetchObjects;
import com.psyclinicSolutions.repositories.PatientRepository;
import com.psyclinicSolutions.repositories.SessionRepository;
import com.psyclinicSolutions.repositories.TherapistRepository;
import com.psyclinicSolutions.services.helpers.SessionValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.*;
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
    @Autowired
    private SessionValidator sessionValidator;

    private final ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
    private final LocalTime startTime = LocalTime.parse("06:59:59");
    private final LocalTime endTime = LocalTime.parse("21:00:00");

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

    public SessionDTO insert(SessionDTO data) {
        Session entity = new Session();
        dataToSession(data, entity);

        validateSession(entity, null);

        entity = repository.save(entity);

        return new SessionDTO(entity);
    }



    @Transactional
    public SessionDTO update(UUID id, SessionDTO data) {
        try{
            Session entity = repository.getReferenceById(id);
            dataToSession(data, entity);

            validateSession(entity, entity);


            entity = repository.save(entity);
            return new SessionDTO(entity);
        } catch (EntityNotFoundException exception){
            throw new DataNotFoundException("Sessão não encontrada.");
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

    private void validateSession(Session entity, Session sessionToIgnore) {
        UUID therapistId = entity.getTherapist().getId();
        Instant newSessionStart = entity.getSessionDate();
        Instant newSessionEnd = newSessionStart.plusSeconds(entity.getSessionDuration() * 60);
        List<Session> therapistSessions = repository.findAll().stream()
                .filter(s -> s.getTherapist().getId().equals(therapistId))
                .toList();

        if (sessionValidator.isSessionOverlapping(newSessionStart, newSessionEnd, therapistSessions, sessionToIgnore)) {
            throw new IllegalArgumentException("O terapeuta já estará em sessão no dia e horário especificado.");
        }

        if (sessionValidator.isSessionOffWorkingHours(newSessionStart, newSessionEnd, startTime, endTime, zoneId)) {
            throw new IllegalArgumentException("A sessão não pode ser criada fora das horas de trabalho.");
        }

        if (entity.getIsPaid() && !sessionValidator.isPaidCheckedAndPaymentDateNotNull(entity)) {
            throw new IllegalArgumentException("Insira a data de pagamento.");
        }

        if (entity.getIsAccounted() && !sessionValidator.isAccountedCheckedAndAccountDateNotNull(entity)) {
            throw new IllegalArgumentException("Insira a data da contabilidade do pagamento.");
        }

        if (entity.getIsAuthorized() && !sessionValidator.isAuthorizedCheckedAndAuthorizationDateOrTokenNotNull(entity)) {
            throw new IllegalArgumentException("Insira a data de autorização e/ou o token.");
        }
    }
}
