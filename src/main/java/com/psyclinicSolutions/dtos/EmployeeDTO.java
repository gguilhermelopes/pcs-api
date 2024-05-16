package com.psyclinicSolutions.dtos;

import com.psyclinicSolutions.domain.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record EmployeeDTO(
        UUID id,
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
        String role
       ) {

    public EmployeeDTO(Employee entity){
        this(
                entity.getId(),
                entity.getName(),
                entity.getImgUrl(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getCellphone(),
                entity.getEmergencyContact(),
                entity.getEmergencyContactPhone(),
                entity.getRole()
        );
    }
}
