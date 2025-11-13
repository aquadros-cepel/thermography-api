package com.tech.thermography.repository;

import com.tech.thermography.domain.ThermographicInspectionRecord;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ThermographicInspectionRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThermographicInspectionRecordRepository extends JpaRepository<ThermographicInspectionRecord, UUID> {}
