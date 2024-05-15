package com.psyclinicSolutions.repositories;

import com.psyclinicSolutions.domain.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InsuranceRepository extends JpaRepository<Insurance, UUID> {
}
