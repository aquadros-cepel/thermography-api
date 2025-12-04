package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRecordGroupEquipment;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InspectionRecordGroupEquipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRecordGroupEquipmentRepository extends JpaRepository<InspectionRecordGroupEquipment, UUID> {}
