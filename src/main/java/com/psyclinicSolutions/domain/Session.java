package com.psyclinicSolutions.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "session")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;
    private Instant sessionDate;
    private Integer sessionDuration;
    private Boolean isRemote;
    private Boolean isAuthorized;
    private List<String> token = new ArrayList<>();
    private Instant authorizationDate;
    private Boolean hasPatientAttended;
    private Double sessionValue;
    private Boolean isPaid;
    private Instant paymentDate;
    private Boolean isAccounted;
    private Instant accountDate;
}
