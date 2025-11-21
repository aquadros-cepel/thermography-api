package com.tech.thermography.web.rest;

import com.tech.thermography.domain.InspectionRouteGroup;
import com.tech.thermography.repository.InspectionRouteGroupRepository;
import com.tech.thermography.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tech.thermography.domain.InspectionRouteGroup}.
 */
@RestController
@RequestMapping("/api/inspection-route-groups")
@Transactional
public class InspectionRouteGroupResource {

    private static final Logger LOG = LoggerFactory.getLogger(InspectionRouteGroupResource.class);

    private static final String ENTITY_NAME = "inspectionRouteGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionRouteGroupRepository inspectionRouteGroupRepository;

    public InspectionRouteGroupResource(InspectionRouteGroupRepository inspectionRouteGroupRepository) {
        this.inspectionRouteGroupRepository = inspectionRouteGroupRepository;
    }

    /**
     * {@code POST  /inspection-route-groups} : Create a new inspectionRouteGroup.
     *
     * @param inspectionRouteGroup the inspectionRouteGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectionRouteGroup, or with status {@code 400 (Bad Request)} if the inspectionRouteGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InspectionRouteGroup> createInspectionRouteGroup(@Valid @RequestBody InspectionRouteGroup inspectionRouteGroup)
        throws URISyntaxException {
        LOG.debug("REST request to save InspectionRouteGroup : {}", inspectionRouteGroup);
        if (inspectionRouteGroup.getId() != null) {
            throw new BadRequestAlertException("A new inspectionRouteGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inspectionRouteGroup = inspectionRouteGroupRepository.save(inspectionRouteGroup);
        return ResponseEntity.created(new URI("/api/inspection-route-groups/" + inspectionRouteGroup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inspectionRouteGroup.getId().toString()))
            .body(inspectionRouteGroup);
    }

    /**
     * {@code PUT  /inspection-route-groups/:id} : Updates an existing inspectionRouteGroup.
     *
     * @param id the id of the inspectionRouteGroup to save.
     * @param inspectionRouteGroup the inspectionRouteGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRouteGroup,
     * or with status {@code 400 (Bad Request)} if the inspectionRouteGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRouteGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InspectionRouteGroup> updateInspectionRouteGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody InspectionRouteGroup inspectionRouteGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to update InspectionRouteGroup : {}, {}", id, inspectionRouteGroup);
        if (inspectionRouteGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRouteGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRouteGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inspectionRouteGroup = inspectionRouteGroupRepository.save(inspectionRouteGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRouteGroup.getId().toString()))
            .body(inspectionRouteGroup);
    }

    /**
     * {@code PATCH  /inspection-route-groups/:id} : Partial updates given fields of an existing inspectionRouteGroup, field will ignore if it is null
     *
     * @param id the id of the inspectionRouteGroup to save.
     * @param inspectionRouteGroup the inspectionRouteGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRouteGroup,
     * or with status {@code 400 (Bad Request)} if the inspectionRouteGroup is not valid,
     * or with status {@code 404 (Not Found)} if the inspectionRouteGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRouteGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InspectionRouteGroup> partialUpdateInspectionRouteGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody InspectionRouteGroup inspectionRouteGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InspectionRouteGroup partially : {}, {}", id, inspectionRouteGroup);
        if (inspectionRouteGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRouteGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRouteGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InspectionRouteGroup> result = inspectionRouteGroupRepository
            .findById(inspectionRouteGroup.getId())
            .map(existingInspectionRouteGroup -> {
                if (inspectionRouteGroup.getCode() != null) {
                    existingInspectionRouteGroup.setCode(inspectionRouteGroup.getCode());
                }
                if (inspectionRouteGroup.getName() != null) {
                    existingInspectionRouteGroup.setName(inspectionRouteGroup.getName());
                }
                if (inspectionRouteGroup.getDescription() != null) {
                    existingInspectionRouteGroup.setDescription(inspectionRouteGroup.getDescription());
                }
                if (inspectionRouteGroup.getIncluded() != null) {
                    existingInspectionRouteGroup.setIncluded(inspectionRouteGroup.getIncluded());
                }
                if (inspectionRouteGroup.getOrderIndex() != null) {
                    existingInspectionRouteGroup.setOrderIndex(inspectionRouteGroup.getOrderIndex());
                }

                return existingInspectionRouteGroup;
            })
            .map(inspectionRouteGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRouteGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /inspection-route-groups} : get all the inspectionRouteGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectionRouteGroups in body.
     */
    @GetMapping("")
    public List<InspectionRouteGroup> getAllInspectionRouteGroups() {
        LOG.debug("REST request to get all InspectionRouteGroups");
        return inspectionRouteGroupRepository.findAll();
    }

    /**
     * {@code GET  /inspection-route-groups/:id} : get the "id" inspectionRouteGroup.
     *
     * @param id the id of the inspectionRouteGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectionRouteGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InspectionRouteGroup> getInspectionRouteGroup(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get InspectionRouteGroup : {}", id);
        Optional<InspectionRouteGroup> inspectionRouteGroup = inspectionRouteGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspectionRouteGroup);
    }

    /**
     * {@code DELETE  /inspection-route-groups/:id} : delete the "id" inspectionRouteGroup.
     *
     * @param id the id of the inspectionRouteGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectionRouteGroup(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete InspectionRouteGroup : {}", id);
        inspectionRouteGroupRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
