package com.psyclinicSolutions.dtos;

import com.psyclinicSolutions.domain.Patient;
import com.psyclinicSolutions.domain.Session;
import com.psyclinicSolutions.domain.Therapist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

import java.util.UUID;

public record SessionDTO(
        UUID id,
        @NotNull(message = "Paciente é obrigatório.")
        UUID patientId,
        String patient,
        @NotNull(message = "Terapeuta é obrigatório.")
        UUID therapistId,
        String therapist,
        @NotNull(message = "Data da sessão é obrigatória.")
        Instant sessionDate,
        @NotNull(message = "Duração da sessão é obrigatória.")
        Integer sessionDuration,
        @NotNull(message = "Indique se a sessão é remota.")
        Boolean isRemote,
        @NotNull(message = "Indique se a sessão foi autorizada.")
        Boolean isAuthorized,
        String token,
        Instant authorizationDate,
        @NotNull(message = "Indique se o paciente compareceu à sessão.")
        Boolean hasPatientAttended,
        @NotNull(message = "Valor da sessão é obrigatório.")
        Double sessionValue,
        @NotNull(message = "Indique se a sessão foi paga.")
        Boolean isPaid,
        Instant paymentDate,
        @NotNull(message = "Indique se a sessão foi contabilizada.")
        Boolean isAccounted,
        Instant accountDate
       ) {

    public SessionDTO(Session entity){
        this(
                entity.getId(),
                entity.getPatient().getId(),
                entity.getPatient().getName(),
                entity.getTherapist().getId(),
                entity.getTherapist().getName(),
                entity.getSessionDate(),
                entity.getSessionDuration(),
                entity.getIsRemote(),
                entity.getIsAuthorized(),
                entity.getToken(),
                entity.getAuthorizationDate(),
                entity.getHasPatientAttended(),
                entity.getSessionValue(),
                entity.getIsPaid(),
                entity.getPaymentDate(),
                entity.getIsAccounted(),
                entity.getAccountDate()
        );
    }
}
