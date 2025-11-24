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
}
