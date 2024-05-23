package com.psyclinicSolutions.dto;

import com.psyclinicSolutions.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserInsertDTO (
        UUID id,
        @NotNull(message = "Nome é obrigatório.")
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, message = "Nome tem que ter mais de 3 caracteres.")
        String name,
        @NotNull(message = "Email é obrigatório.")
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email inválido.")
        String email,
        @NotNull(message = "Senha é obrigatória.")
        @NotBlank(message = "Senha é obrigatória.")
        @Size(min = 7, message = "Senha tem que ter mais de 7 caracteres.")
        String password,
        @NotNull(message = "Função é obrigatória.")
        UUID roleId
){
    public UserInsertDTO (User entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole().getId()
        );
    }
}
