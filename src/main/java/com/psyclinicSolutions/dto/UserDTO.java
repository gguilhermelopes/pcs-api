package com.psyclinicSolutions.dto;

import com.psyclinicSolutions.domain.User;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String name,
        String email,
        UUID roleId,
        String role
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
