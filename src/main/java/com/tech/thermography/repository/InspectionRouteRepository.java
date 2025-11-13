package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRoute;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InspectionRoute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRouteRepository extends JpaRepository<InspectionRoute, UUID> {}
