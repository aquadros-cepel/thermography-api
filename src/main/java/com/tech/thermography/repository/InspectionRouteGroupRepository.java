package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRouteGroup;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InspectionRouteGroup entity.
 *
 * When extending this class, extend InspectionRouteGroupRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface InspectionRouteGroupRepository
    extends InspectionRouteGroupRepositoryWithBagRelationships, JpaRepository<InspectionRouteGroup, UUID> {
    default Optional<InspectionRouteGroup> findOneWithEagerRelationships(UUID id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<InspectionRouteGroup> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<InspectionRouteGroup> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
