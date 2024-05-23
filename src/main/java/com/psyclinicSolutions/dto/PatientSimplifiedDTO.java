package com.psyclinicSolutions.dto;

import com.psyclinicSolutions.domain.Patient;

import java.util.UUID;

public record PatientSimplifiedDTO (
        UUID id,
        UUID therapistId,
        String therapist,
        UUID insuranceId,
        String insurance,
        String name,
        String email,
        String phone
)
{
    public PatientSimplifiedDTO(Patient entity){
        this(
                entity.getId(),
                entity.getTherapist().getId(),
                entity.getTherapist().getName(),
                entity.getInsurance().getId(),
                entity.getInsurance().getName(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone()
                );
    }

}
