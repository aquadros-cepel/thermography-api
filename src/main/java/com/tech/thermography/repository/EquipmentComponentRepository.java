package com.tech.thermography.repository;

import com.tech.thermography.domain.EquipmentComponent;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EquipmentComponent entity.
 *
 * When extending this class, extend EquipmentComponentRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface EquipmentComponentRepository
    extends EquipmentComponentRepositoryWithBagRelationships, JpaRepository<EquipmentComponent, UUID> {
    default Optional<EquipmentComponent> findOneWithEagerRelationships(UUID id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<EquipmentComponent> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<EquipmentComponent> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
