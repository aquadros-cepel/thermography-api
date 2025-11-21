package com.tech.thermography.web.rest;

import com.tech.thermography.domain.EquipmentComponent;
import com.tech.thermography.repository.EquipmentComponentRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.EquipmentComponent}.
 */
@RestController
@RequestMapping("/api/equipment-components")
@Transactional
public class EquipmentComponentResource {

    private static final Logger LOG = LoggerFactory.getLogger(EquipmentComponentResource.class);

    private static final String ENTITY_NAME = "equipmentComponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentComponentRepository equipmentComponentRepository;

    public EquipmentComponentResource(EquipmentComponentRepository equipmentComponentRepository) {
        this.equipmentComponentRepository = equipmentComponentRepository;
    }

    /**
     * {@code POST  /equipment-components} : Create a new equipmentComponent.
     *
     * @param equipmentComponent the equipmentComponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipmentComponent, or with status {@code 400 (Bad Request)} if the equipmentComponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EquipmentComponent> createEquipmentComponent(@Valid @RequestBody EquipmentComponent equipmentComponent)
        throws URISyntaxException {
        LOG.debug("REST request to save EquipmentComponent : {}", equipmentComponent);
        if (equipmentComponent.getId() != null) {
            throw new BadRequestAlertException("A new equipmentComponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        equipmentComponent = equipmentComponentRepository.save(equipmentComponent);
        return ResponseEntity.created(new URI("/api/equipment-components/" + equipmentComponent.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, equipmentComponent.getId().toString()))
            .body(equipmentComponent);
    }

    /**
     * {@code PUT  /equipment-components/:id} : Updates an existing equipmentComponent.
     *
     * @param id the id of the equipmentComponent to save.
     * @param equipmentComponent the equipmentComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentComponent,
     * or with status {@code 400 (Bad Request)} if the equipmentComponent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipmentComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentComponent> updateEquipmentComponent(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody EquipmentComponent equipmentComponent
    ) throws URISyntaxException {
        LOG.debug("REST request to update EquipmentComponent : {}, {}", id, equipmentComponent);
        if (equipmentComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        equipmentComponent = equipmentComponentRepository.save(equipmentComponent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipmentComponent.getId().toString()))
            .body(equipmentComponent);
    }

    /**
     * {@code PATCH  /equipment-components/:id} : Partial updates given fields of an existing equipmentComponent, field will ignore if it is null
     *
     * @param id the id of the equipmentComponent to save.
     * @param equipmentComponent the equipmentComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentComponent,
     * or with status {@code 400 (Bad Request)} if the equipmentComponent is not valid,
     * or with status {@code 404 (Not Found)} if the equipmentComponent is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipmentComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EquipmentComponent> partialUpdateEquipmentComponent(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody EquipmentComponent equipmentComponent
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EquipmentComponent partially : {}, {}", id, equipmentComponent);
        if (equipmentComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EquipmentComponent> result = equipmentComponentRepository
            .findById(equipmentComponent.getId())
            .map(existingEquipmentComponent -> {
                if (equipmentComponent.getCode() != null) {
                    existingEquipmentComponent.setCode(equipmentComponent.getCode());
                }
                if (equipmentComponent.getName() != null) {
                    existingEquipmentComponent.setName(equipmentComponent.getName());
                }
                if (equipmentComponent.getDescription() != null) {
                    existingEquipmentComponent.setDescription(equipmentComponent.getDescription());
                }

                return existingEquipmentComponent;
            })
            .map(equipmentComponentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipmentComponent.getId().toString())
        );
    }

    /**
     * {@code GET  /equipment-components} : get all the equipmentComponents.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipmentComponents in body.
     */
    @GetMapping("")
    public List<EquipmentComponent> getAllEquipmentComponents(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get all EquipmentComponents");
        if (eagerload) {
            return equipmentComponentRepository.findAllWithEagerRelationships();
        } else {
            return equipmentComponentRepository.findAll();
        }
    }

    /**
     * {@code GET  /equipment-components/:id} : get the "id" equipmentComponent.
     *
     * @param id the id of the equipmentComponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipmentComponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentComponent> getEquipmentComponent(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get EquipmentComponent : {}", id);
        Optional<EquipmentComponent> equipmentComponent = equipmentComponentRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(equipmentComponent);
    }

    /**
     * {@code DELETE  /equipment-components/:id} : delete the "id" equipmentComponent.
     *
     * @param id the id of the equipmentComponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipmentComponent(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete EquipmentComponent : {}", id);
        equipmentComponentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
