package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRouteGroupEquipment;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InspectionRouteGroupEquipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRouteGroupEquipmentRepository extends JpaRepository<InspectionRouteGroupEquipment, UUID> {}
