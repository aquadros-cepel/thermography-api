package com.tech.thermography.web.rest;

import com.tech.thermography.domain.EquipmentGroup;
import com.tech.thermography.repository.EquipmentGroupRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.EquipmentGroup}.
 */
@RestController
@RequestMapping("/api/equipment-groups")
@Transactional
public class EquipmentGroupResource {

    private static final Logger LOG = LoggerFactory.getLogger(EquipmentGroupResource.class);

    private static final String ENTITY_NAME = "equipmentGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentGroupRepository equipmentGroupRepository;

    public EquipmentGroupResource(EquipmentGroupRepository equipmentGroupRepository) {
        this.equipmentGroupRepository = equipmentGroupRepository;
    }

    /**
     * {@code POST  /equipment-groups} : Create a new equipmentGroup.
     *
     * @param equipmentGroup the equipmentGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipmentGroup, or with status {@code 400 (Bad Request)} if the equipmentGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EquipmentGroup> createEquipmentGroup(@Valid @RequestBody EquipmentGroup equipmentGroup)
        throws URISyntaxException {
        LOG.debug("REST request to save EquipmentGroup : {}", equipmentGroup);
        if (equipmentGroup.getId() != null) {
            throw new BadRequestAlertException("A new equipmentGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        equipmentGroup = equipmentGroupRepository.save(equipmentGroup);
        return ResponseEntity.created(new URI("/api/equipment-groups/" + equipmentGroup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, equipmentGroup.getId().toString()))
            .body(equipmentGroup);
    }

    /**
     * {@code PUT  /equipment-groups/:id} : Updates an existing equipmentGroup.
     *
     * @param id the id of the equipmentGroup to save.
     * @param equipmentGroup the equipmentGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentGroup,
     * or with status {@code 400 (Bad Request)} if the equipmentGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipmentGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentGroup> updateEquipmentGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody EquipmentGroup equipmentGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to update EquipmentGroup : {}, {}", id, equipmentGroup);
        if (equipmentGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        equipmentGroup = equipmentGroupRepository.save(equipmentGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipmentGroup.getId().toString()))
            .body(equipmentGroup);
    }

    /**
     * {@code PATCH  /equipment-groups/:id} : Partial updates given fields of an existing equipmentGroup, field will ignore if it is null
     *
     * @param id the id of the equipmentGroup to save.
     * @param equipmentGroup the equipmentGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentGroup,
     * or with status {@code 400 (Bad Request)} if the equipmentGroup is not valid,
     * or with status {@code 404 (Not Found)} if the equipmentGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipmentGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EquipmentGroup> partialUpdateEquipmentGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody EquipmentGroup equipmentGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EquipmentGroup partially : {}, {}", id, equipmentGroup);
        if (equipmentGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EquipmentGroup> result = equipmentGroupRepository
            .findById(equipmentGroup.getId())
            .map(existingEquipmentGroup -> {
                if (equipmentGroup.getName() != null) {
                    existingEquipmentGroup.setName(equipmentGroup.getName());
                }
                if (equipmentGroup.getTitle() != null) {
                    existingEquipmentGroup.setTitle(equipmentGroup.getTitle());
                }
                if (equipmentGroup.getDescription() != null) {
                    existingEquipmentGroup.setDescription(equipmentGroup.getDescription());
                }

                return existingEquipmentGroup;
            })
            .map(equipmentGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipmentGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /equipment-groups} : get all the equipmentGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipmentGroups in body.
     */
    @GetMapping("")
    public List<EquipmentGroup> getAllEquipmentGroups() {
        LOG.debug("REST request to get all EquipmentGroups");
        return equipmentGroupRepository.findAll();
    }

    /**
     * {@code GET  /equipment-groups/:id} : get the "id" equipmentGroup.
     *
     * @param id the id of the equipmentGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipmentGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentGroup> getEquipmentGroup(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get EquipmentGroup : {}", id);
        Optional<EquipmentGroup> equipmentGroup = equipmentGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipmentGroup);
    }

    /**
     * {@code DELETE  /equipment-groups/:id} : delete the "id" equipmentGroup.
     *
     * @param id the id of the equipmentGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipmentGroup(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete EquipmentGroup : {}", id);
        equipmentGroupRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
