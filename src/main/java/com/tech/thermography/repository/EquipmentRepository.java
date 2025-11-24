package com.tech.thermography.repository;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentGroup;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Equipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
    List<Equipment> findByGroup(EquipmentGroup group);
}
