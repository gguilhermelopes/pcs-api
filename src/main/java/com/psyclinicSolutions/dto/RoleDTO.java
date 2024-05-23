package com.psyclinicSolutions.dto;

import com.psyclinicSolutions.domain.Role;
import com.psyclinicSolutions.domain.User;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record RoleDTO(UUID id, String authority, Set<UserDTO> users) {
    public RoleDTO(Role entity) {
        this(entity.getId(),
                entity.getAuthority(),
                entity.getUsers().stream().map(UserDTO::new).collect(Collectors.toSet())
        );
    }
}
