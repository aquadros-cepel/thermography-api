package com.tech.thermography.web.rest;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.repository.EquipmentRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.Equipment}.
 */
@RestController
@RequestMapping("/api/equipment")
@Transactional
public class EquipmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(EquipmentResource.class);

    private static final String ENTITY_NAME = "equipment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentRepository equipmentRepository;

    public EquipmentResource(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    /**
     * {@code POST  /equipment} : Create a new equipment.
     *
     * @param equipment the equipment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipment, or with status {@code 400 (Bad Request)} if the equipment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Equipment> createEquipment(@Valid @RequestBody Equipment equipment) throws URISyntaxException {
        LOG.debug("REST request to save Equipment : {}", equipment);
        if (equipment.getId() != null) {
            throw new BadRequestAlertException("A new equipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        equipment = equipmentRepository.save(equipment);
        return ResponseEntity.created(new URI("/api/equipment/" + equipment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, equipment.getId().toString()))
            .body(equipment);
    }

    /**
     * {@code PUT  /equipment/:id} : Updates an existing equipment.
     *
     * @param id the id of the equipment to save.
     * @param equipment the equipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipment,
     * or with status {@code 400 (Bad Request)} if the equipment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody Equipment equipment
    ) throws URISyntaxException {
        LOG.debug("REST request to update Equipment : {}, {}", id, equipment);
        if (equipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        equipment = equipmentRepository.save(equipment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipment.getId().toString()))
            .body(equipment);
    }

    /**
     * {@code PATCH  /equipment/:id} : Partial updates given fields of an existing equipment, field will ignore if it is null
     *
     * @param id the id of the equipment to save.
     * @param equipment the equipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipment,
     * or with status {@code 400 (Bad Request)} if the equipment is not valid,
     * or with status {@code 404 (Not Found)} if the equipment is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Equipment> partialUpdateEquipment(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Equipment equipment
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Equipment partially : {}, {}", id, equipment);
        if (equipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Equipment> result = equipmentRepository
            .findById(equipment.getId())
            .map(existingEquipment -> {
                if (equipment.getName() != null) {
                    existingEquipment.setName(equipment.getName());
                }
                if (equipment.getTitle() != null) {
                    existingEquipment.setTitle(equipment.getTitle());
                }
                if (equipment.getDescription() != null) {
                    existingEquipment.setDescription(equipment.getDescription());
                }
                if (equipment.getType() != null) {
                    existingEquipment.setType(equipment.getType());
                }
                if (equipment.getManufacturer() != null) {
                    existingEquipment.setManufacturer(equipment.getManufacturer());
                }
                if (equipment.getModel() != null) {
                    existingEquipment.setModel(equipment.getModel());
                }
                if (equipment.getSerialNumber() != null) {
                    existingEquipment.setSerialNumber(equipment.getSerialNumber());
                }
                if (equipment.getVoltageClass() != null) {
                    existingEquipment.setVoltageClass(equipment.getVoltageClass());
                }
                if (equipment.getPhaseType() != null) {
                    existingEquipment.setPhaseType(equipment.getPhaseType());
                }
                if (equipment.getStartDate() != null) {
                    existingEquipment.setStartDate(equipment.getStartDate());
                }
                if (equipment.getLatitude() != null) {
                    existingEquipment.setLatitude(equipment.getLatitude());
                }
                if (equipment.getLongitude() != null) {
                    existingEquipment.setLongitude(equipment.getLongitude());
                }

                return existingEquipment;
            })
            .map(equipmentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipment.getId().toString())
        );
    }

    /**
     * {@code GET  /equipment} : get all the equipment.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipment in body.
     */
    @GetMapping("")
    public List<Equipment> getAllEquipment() {
        LOG.debug("REST request to get all Equipment");
        return equipmentRepository.findAll();
    }

    /**
     * {@code GET  /equipment/:id} : get the "id" equipment.
     *
     * @param id the id of the equipment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipment(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get Equipment : {}", id);
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipment);
    }

    /**
     * {@code DELETE  /equipment/:id} : delete the "id" equipment.
     *
     * @param id the id of the equipment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete Equipment : {}", id);
        equipmentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
