package com.tech.thermography.repository;

import com.tech.thermography.domain.EquipmentTypeTranslation;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EquipmentTypeTranslation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentTypeTranslationRepository extends JpaRepository<EquipmentTypeTranslation, UUID> {}
