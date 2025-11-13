package com.tech.thermography.repository;

import com.tech.thermography.domain.RiskPeriodicityDeadline;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RiskPeriodicityDeadline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskPeriodicityDeadlineRepository extends JpaRepository<RiskPeriodicityDeadline, UUID> {}
