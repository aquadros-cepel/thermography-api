package com.tech.thermography.repository;

import com.tech.thermography.domain.EquipmentComponent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface EquipmentComponentRepositoryWithBagRelationships {
    Optional<EquipmentComponent> fetchBagRelationships(Optional<EquipmentComponent> equipmentComponent);

    List<EquipmentComponent> fetchBagRelationships(List<EquipmentComponent> equipmentComponents);

    Page<EquipmentComponent> fetchBagRelationships(Page<EquipmentComponent> equipmentComponents);
}
