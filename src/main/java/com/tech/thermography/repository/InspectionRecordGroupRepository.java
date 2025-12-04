package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRecordGroup;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InspectionRecordGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRecordGroupRepository extends JpaRepository<InspectionRecordGroup, UUID> {}
