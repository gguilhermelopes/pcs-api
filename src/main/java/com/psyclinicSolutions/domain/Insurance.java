package com.psyclinicSolutions.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "insurance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    @Column(unique = true)
    private String cnpj;
    private String contact;
    private String email;
    private String address;
    private String phone;
    private String cellphone;
    @OneToMany(mappedBy = "insurance")
    private Set<Patient> patients = new HashSet<>();
}
