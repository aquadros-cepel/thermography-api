package com.tech.thermography.repository;

import com.tech.thermography.domain.ROI;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ROI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ROIRepository extends JpaRepository<ROI, UUID> {}
