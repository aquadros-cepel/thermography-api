package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRoute;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InspectionRoute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectionRouteRepository extends JpaRepository<InspectionRoute, UUID> {
    @Query(
        "select inspectionRoute from InspectionRoute inspectionRoute left join fetch inspectionRoute.plant left join fetch inspectionRoute.createdBy where inspectionRoute.id =:id"
    )
    Optional<InspectionRoute> findByIdWithAssociations(@Param("id") UUID id);

    @Query(
        "SELECT DISTINCT ir FROM InspectionRoute ir " +
        "LEFT JOIN FETCH ir.groups g " +
        "LEFT JOIN FETCH g.subGroups sg " +
        "LEFT JOIN FETCH sg.equipments sge " +
        "LEFT JOIN FETCH sge.equipment eq " +
        "LEFT JOIN FETCH g.equipments ge " +
        "LEFT JOIN FETCH ge.equipment geq " +
        "WHERE ir.id = :id"
    )
    Optional<InspectionRoute> findByIdWithFullHierarchy(@Param("id") UUID id);
}
