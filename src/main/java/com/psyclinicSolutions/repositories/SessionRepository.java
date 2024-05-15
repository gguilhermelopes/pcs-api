package com.psyclinicSolutions.repositories;

import com.psyclinicSolutions.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
}
