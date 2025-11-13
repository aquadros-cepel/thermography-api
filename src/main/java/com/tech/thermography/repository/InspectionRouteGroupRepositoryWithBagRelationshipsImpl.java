package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRouteGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class InspectionRouteGroupRepositoryWithBagRelationshipsImpl implements InspectionRouteGroupRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String INSPECTIONROUTEGROUPS_PARAMETER = "inspectionRouteGroups";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<InspectionRouteGroup> fetchBagRelationships(Optional<InspectionRouteGroup> inspectionRouteGroup) {
        return inspectionRouteGroup.map(this::fetchEquipments);
    }

    @Override
    public Page<InspectionRouteGroup> fetchBagRelationships(Page<InspectionRouteGroup> inspectionRouteGroups) {
        return new PageImpl<>(
            fetchBagRelationships(inspectionRouteGroups.getContent()),
            inspectionRouteGroups.getPageable(),
            inspectionRouteGroups.getTotalElements()
        );
    }

    @Override
    public List<InspectionRouteGroup> fetchBagRelationships(List<InspectionRouteGroup> inspectionRouteGroups) {
        return Optional.of(inspectionRouteGroups).map(this::fetchEquipments).orElse(Collections.emptyList());
    }

    InspectionRouteGroup fetchEquipments(InspectionRouteGroup result) {
        return entityManager
            .createQuery(
                "select inspectionRouteGroup from InspectionRouteGroup inspectionRouteGroup left join fetch inspectionRouteGroup.equipments where inspectionRouteGroup.id = :id",
                InspectionRouteGroup.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<InspectionRouteGroup> fetchEquipments(List<InspectionRouteGroup> inspectionRouteGroups) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, inspectionRouteGroups.size()).forEach(index -> order.put(inspectionRouteGroups.get(index).getId(), index));
        List<InspectionRouteGroup> result = entityManager
            .createQuery(
                "select inspectionRouteGroup from InspectionRouteGroup inspectionRouteGroup left join fetch inspectionRouteGroup.equipments where inspectionRouteGroup in :inspectionRouteGroups",
                InspectionRouteGroup.class
            )
            .setParameter(INSPECTIONROUTEGROUPS_PARAMETER, inspectionRouteGroups)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
