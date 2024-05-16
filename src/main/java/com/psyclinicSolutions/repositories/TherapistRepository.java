package com.psyclinicSolutions.repositories;

import com.psyclinicSolutions.domain.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TherapistRepository extends JpaRepository<Therapist, UUID> {
}
