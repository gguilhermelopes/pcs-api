package com.psyclinicSolutions.dtos;

import com.psyclinicSolutions.domain.Patient;
import com.psyclinicSolutions.domain.Session;
import com.psyclinicSolutions.domain.Therapist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record TherapistDTO(
        UUID id,
        @NotNull(message = "Nome é obrigatório.")
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, message = "Nome tem que ter mais de 3 caracteres.")
        String name,
        @NotNull(message = "Email é obrigatório.")
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email inválido.")
        String email,
        @NotNull(message = "CRP é obrigatório.")
        @NotBlank(message = "CRP é obrigatório.")
        String crp,
        @NotNull(message = "Endereço é obrigatório.")
        @NotBlank(message = "Endereço é obrigatório.")
        String address,
        @NotNull(message = "Telefone é obrigatório.")
        @NotBlank(message = "Telefone é obrigatório.")
        String phone,
        String cellphone,
        @NotNull(message = "Especialização é obrigatória.")
        @NotBlank(message = "Especialização é obrigatória")
        String expertise,
        Set<Patient> patients,
        Set<Session> sessions
       ) {

    public TherapistDTO(Therapist entity){
        this(
                entity.getId(),
                entity.getName(),
                entity.getCrp(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getCellphone(),
                entity.getExpertise(),
                Set.of(),
                Set.of()
        );
    }
}
