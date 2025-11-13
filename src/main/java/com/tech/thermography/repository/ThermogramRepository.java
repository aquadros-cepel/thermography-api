package com.tech.thermography.repository;

import com.tech.thermography.domain.Thermogram;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Thermogram entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThermogramRepository extends JpaRepository<Thermogram, UUID> {}
