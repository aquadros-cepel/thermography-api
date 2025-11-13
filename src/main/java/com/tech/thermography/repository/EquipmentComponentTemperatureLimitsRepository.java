package com.tech.thermography.repository;

import com.tech.thermography.domain.EquipmentComponentTemperatureLimits;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EquipmentComponentTemperatureLimits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentComponentTemperatureLimitsRepository extends JpaRepository<EquipmentComponentTemperatureLimits, UUID> {}
