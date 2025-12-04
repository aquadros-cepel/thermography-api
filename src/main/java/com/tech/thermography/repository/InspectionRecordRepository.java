package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRecord;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InspectionRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRecordRepository extends JpaRepository<InspectionRecord, UUID> {
    @Query(
        "SELECT DISTINCT ir FROM InspectionRecord ir " +
        "LEFT JOIN FETCH ir.groups g " +
        "LEFT JOIN FETCH g.subGroups sg " +
        "LEFT JOIN FETCH sg.equipments sge " +
        "LEFT JOIN FETCH sge.equipment eq " +
        "LEFT JOIN FETCH g.equipments ge " +
        "LEFT JOIN FETCH ge.equipment geq " +
        "WHERE ir.id = :id"
    )
    Optional<InspectionRecord> findByIdWithFullHierarchy(@Param("id") UUID id);

    @Query("SELECT COUNT(ir) FROM InspectionRecord ir WHERE ir.inspectionRoute.id = :routeId")
    long countByInspectionRouteId(@Param("routeId") UUID routeId);
}
