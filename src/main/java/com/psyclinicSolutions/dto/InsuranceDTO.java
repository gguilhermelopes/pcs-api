package com.psyclinicSolutions.dto;

import com.psyclinicSolutions.domain.Insurance;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record InsuranceDTO(
        UUID id,
        @NotNull(message = "Nome é obrigatório.")
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, message = "Nome tem que ter mais de 3 caracteres.")
        String name,
        @NotNull(message = "CNPJ é obrigatório.")
        @NotBlank(message = "CNPJ é obrigatório.")
        @CNPJ(message = "CNPJ inválido.")
        String cnpj,
        @NotNull(message = "Contato é obrigatório.")
        @NotBlank(message = "Contato é obrigatório.")
        String contact,
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
        Set<PatientSimplifiedDTO> patients
       ) {

    public InsuranceDTO(Insurance entity){
        this(
                entity.getId(),
                entity.getName(),
                entity.getCnpj(),
                entity.getContact(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getCellphone(),
                new HashSet<>(entity.getPatients().stream().map(PatientSimplifiedDTO::new).collect(Collectors.toList()))
        );
    }


}
