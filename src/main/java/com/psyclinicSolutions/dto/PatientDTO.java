package com.psyclinicSolutions.dto;

import com.psyclinicSolutions.domain.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record PatientDTO(
        UUID id,
        @NotNull(message = "Terapeuta é obrigatório")
        UUID therapistId,
        String therapist,
        @NotNull(message = "Plano de saúde é obrigatório")
        UUID insuranceId,
        String insurance,
        @NotNull(message = "Nome é obrigatório.")
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, message = "Nome tem que ter mais de 3 caracteres.")
        String name,
        String imgUrl,
        @NotNull(message = "CPF é obrigatório.")
        @NotBlank(message = "CPF é obrigatório.")
        @CPF(message = "CPF inválido.")
        String cpf,
        @NotNull(message = "Email é obrigatório.")
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email inválido.")
        String email,
        @NotNull(message = "Endereço é obrigatório.")
        @NotBlank(message = "Endereço é obrigatório.")
        String address,
        @NotNull(message = "Telefone é obrigatório.")
        @NotBlank(message = "Telefone é obrigatório.")
        String phone,
        String cellphone,
        @NotNull(message = "Contato emergencial é obrigatório.")
        @NotBlank(message = "Contato emergencial é obrigatório.")
        @Size(min = 3, message = "Contato emergencial tem que ter mais de 3 caracteres.")
        String emergencyContact,
        @NotNull(message = "Telefone do contato emergencial é obrigatório.")
        @NotBlank(message = "Telefone do contato emergencial é obrigatório.")
        String emergencyContactPhone,
        @NotNull(message = "Função é obrigatória.")
        @NotBlank(message = "Função é obrigatória.")
        String records,
        Set<SessionDTO> sessions
       ) {

    public PatientDTO(Patient entity){
        this(
                entity.getId(),
                entity.getTherapist().getId(),
                entity.getTherapist().getName(),
                entity.getInsurance().getId(),
                entity.getInsurance().getName(),
                entity.getName(),
                entity.getImgUrl(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getCellphone(),
                entity.getEmergencyContact(),
                entity.getEmergencyContactPhone(),
                entity.getRecords(),
                new HashSet<>(entity.getSessions().stream().map(SessionDTO::new).collect(Collectors.toSet()))
        );
    }
}
