package com.psyclinicSolutions.dto;

import com.psyclinicSolutions.domain.User;

import java.util.UUID;

public record UserInsertDTO (
        UUID id,
        String name,
        String email,
        String password,
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
