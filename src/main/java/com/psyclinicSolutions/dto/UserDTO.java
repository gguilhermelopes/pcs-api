package com.psyclinicSolutions.dto;

import com.psyclinicSolutions.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserDTO(
        UUID id,
        @NotNull(message = "Nome é obrigatório.")
        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, message = "Nome tem que ter mais de 3 caracteres.")
        String name,
        @NotNull(message = "Email é obrigatório.")
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email inválido.")
        String email,
        @NotNull(message = "Função é obrigatória.")
        UUID roleId,
        String authority
) {
    public UserDTO (User entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRole().getId(),
                entity.getRole().getAuthority()
        );
    }
}
