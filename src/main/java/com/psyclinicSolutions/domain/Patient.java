package com.psyclinicSolutions.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;
    @ManyToOne
    @JoinColumn(name = "insurance_id", nullable = false)
    private Insurance insurance;
    private String name;
    private String imgUrl;
    @Column(unique = true)
    private String cpf;
    private String email;
    private String address;
    private String phone;
    private String cellphone;
    private String emergencyContact;
    private String emergencyContactPhone;
    @Column(columnDefinition = "TEXT")
    private String records;
    @OneToMany(mappedBy = "patient")
    private Set<Session> sessions = new HashSet<>();
}
