package com.tech.thermography.repository;

import com.tech.thermography.domain.EquipmentGroup;
import com.tech.thermography.domain.Plant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EquipmentGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentGroupRepository extends JpaRepository<EquipmentGroup, UUID> {
    Optional<EquipmentGroup> findByNameAndPlant(String name, Plant plant);

    Optional<EquipmentGroup> findByCodeAndPlant(String code, Plant plant);

    List<EquipmentGroup> findByPlant(Plant plant);

    Optional<EquipmentGroup> findFirstByCodeAndPlantAndParentGroupIsNull(String code, Plant plant);

    Optional<EquipmentGroup> findFirstByCodeAndPlantAndParentGroup(String code, Plant plant, EquipmentGroup parentGroup);
}
