package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRouteGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface InspectionRouteGroupRepositoryWithBagRelationships {
    Optional<InspectionRouteGroup> fetchBagRelationships(Optional<InspectionRouteGroup> inspectionRouteGroup);

    List<InspectionRouteGroup> fetchBagRelationships(List<InspectionRouteGroup> inspectionRouteGroups);

    Page<InspectionRouteGroup> fetchBagRelationships(Page<InspectionRouteGroup> inspectionRouteGroups);
}
