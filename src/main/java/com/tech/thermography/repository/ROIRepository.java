package com.tech.thermography.repository;

import com.tech.thermography.domain.ROI;
import com.tech.thermography.domain.Thermogram;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ROI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ROIRepository extends JpaRepository<ROI, UUID> {
    List<ROI> findByThermogram(Thermogram thermogram);

    List<ROI> findByThermogramId(UUID thermogramId);
}
