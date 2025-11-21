package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRouteGroup;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InspectionRouteGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRouteGroupRepository extends JpaRepository<InspectionRouteGroup, UUID> {}
