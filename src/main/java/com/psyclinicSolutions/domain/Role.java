package com.psyclinicSolutions.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    private String authority;
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    @Override
    public String getAuthority() {
        return authority;
    }
}
