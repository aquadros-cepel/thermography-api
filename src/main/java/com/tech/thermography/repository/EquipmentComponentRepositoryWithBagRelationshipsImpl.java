package com.tech.thermography.repository;

import com.tech.thermography.domain.EquipmentComponent;
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
public class EquipmentComponentRepositoryWithBagRelationshipsImpl implements EquipmentComponentRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String EQUIPMENTCOMPONENTS_PARAMETER = "equipmentComponents";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<EquipmentComponent> fetchBagRelationships(Optional<EquipmentComponent> equipmentComponent) {
        return equipmentComponent.map(this::fetchEquipments);
    }

    @Override
    public Page<EquipmentComponent> fetchBagRelationships(Page<EquipmentComponent> equipmentComponents) {
        return new PageImpl<>(
            fetchBagRelationships(equipmentComponents.getContent()),
            equipmentComponents.getPageable(),
            equipmentComponents.getTotalElements()
        );
    }

    @Override
    public List<EquipmentComponent> fetchBagRelationships(List<EquipmentComponent> equipmentComponents) {
        return Optional.of(equipmentComponents).map(this::fetchEquipments).orElse(Collections.emptyList());
    }

    EquipmentComponent fetchEquipments(EquipmentComponent result) {
        return entityManager
            .createQuery(
                "select equipmentComponent from EquipmentComponent equipmentComponent left join fetch equipmentComponent.equipments where equipmentComponent.id = :id",
                EquipmentComponent.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<EquipmentComponent> fetchEquipments(List<EquipmentComponent> equipmentComponents) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, equipmentComponents.size()).forEach(index -> order.put(equipmentComponents.get(index).getId(), index));
        List<EquipmentComponent> result = entityManager
            .createQuery(
                "select equipmentComponent from EquipmentComponent equipmentComponent left join fetch equipmentComponent.equipments where equipmentComponent in :equipmentComponents",
                EquipmentComponent.class
            )
            .setParameter(EQUIPMENTCOMPONENTS_PARAMETER, equipmentComponents)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
