package com.tech.thermography.repository;

import com.tech.thermography.domain.Plant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Plant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantRepository extends JpaRepository<Plant, UUID> {
    Optional<Plant> findByName(String name);
}
