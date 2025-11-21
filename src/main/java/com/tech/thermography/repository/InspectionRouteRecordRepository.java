package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRouteRecord;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InspectionRouteRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRouteRecordRepository extends JpaRepository<InspectionRouteRecord, UUID> {}
