package com.tech.thermography.web.rest;

import com.tech.thermography.domain.InspectionRecordGroupEquipment;
import com.tech.thermography.repository.InspectionRecordGroupEquipmentRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.InspectionRecordGroupEquipment}.
 */
@RestController
@RequestMapping("/api/inspection-record-group-equipments")
@Transactional
public class InspectionRecordGroupEquipmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(InspectionRecordGroupEquipmentResource.class);

    private static final String ENTITY_NAME = "inspectionRecordGroupEquipment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionRecordGroupEquipmentRepository inspectionRecordGroupEquipmentRepository;

    public InspectionRecordGroupEquipmentResource(InspectionRecordGroupEquipmentRepository inspectionRecordGroupEquipmentRepository) {
        this.inspectionRecordGroupEquipmentRepository = inspectionRecordGroupEquipmentRepository;
    }

    /**
     * {@code POST  /inspection-record-group-equipments} : Create a new inspectionRecordGroupEquipment.
     *
     * @param inspectionRecordGroupEquipment the inspectionRecordGroupEquipment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectionRecordGroupEquipment, or with status {@code 400 (Bad Request)} if the inspectionRecordGroupEquipment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InspectionRecordGroupEquipment> createInspectionRecordGroupEquipment(
        @Valid @RequestBody InspectionRecordGroupEquipment inspectionRecordGroupEquipment
    ) throws URISyntaxException {
        LOG.debug("REST request to save InspectionRecordGroupEquipment : {}", inspectionRecordGroupEquipment);
        if (inspectionRecordGroupEquipment.getId() != null) {
            throw new BadRequestAlertException("A new inspectionRecordGroupEquipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.save(inspectionRecordGroupEquipment);
        return ResponseEntity.created(new URI("/api/inspection-record-group-equipments/" + inspectionRecordGroupEquipment.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inspectionRecordGroupEquipment.getId().toString())
            )
            .body(inspectionRecordGroupEquipment);
    }

    /**
     * {@code PUT  /inspection-record-group-equipments/:id} : Updates an existing inspectionRecordGroupEquipment.
     *
     * @param id the id of the inspectionRecordGroupEquipment to save.
     * @param inspectionRecordGroupEquipment the inspectionRecordGroupEquipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRecordGroupEquipment,
     * or with status {@code 400 (Bad Request)} if the inspectionRecordGroupEquipment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRecordGroupEquipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InspectionRecordGroupEquipment> updateInspectionRecordGroupEquipment(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody InspectionRecordGroupEquipment inspectionRecordGroupEquipment
    ) throws URISyntaxException {
        LOG.debug("REST request to update InspectionRecordGroupEquipment : {}, {}", id, inspectionRecordGroupEquipment);
        if (inspectionRecordGroupEquipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRecordGroupEquipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRecordGroupEquipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.save(inspectionRecordGroupEquipment);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecordGroupEquipment.getId().toString())
            )
            .body(inspectionRecordGroupEquipment);
    }

    /**
     * {@code PATCH  /inspection-record-group-equipments/:id} : Partial updates given fields of an existing inspectionRecordGroupEquipment, field will ignore if it is null
     *
     * @param id the id of the inspectionRecordGroupEquipment to save.
     * @param inspectionRecordGroupEquipment the inspectionRecordGroupEquipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRecordGroupEquipment,
     * or with status {@code 400 (Bad Request)} if the inspectionRecordGroupEquipment is not valid,
     * or with status {@code 404 (Not Found)} if the inspectionRecordGroupEquipment is not found,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRecordGroupEquipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InspectionRecordGroupEquipment> partialUpdateInspectionRecordGroupEquipment(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody InspectionRecordGroupEquipment inspectionRecordGroupEquipment
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InspectionRecordGroupEquipment partially : {}, {}", id, inspectionRecordGroupEquipment);
        if (inspectionRecordGroupEquipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRecordGroupEquipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRecordGroupEquipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InspectionRecordGroupEquipment> result = inspectionRecordGroupEquipmentRepository
            .findById(inspectionRecordGroupEquipment.getId())
            .map(existingInspectionRecordGroupEquipment -> {
                if (inspectionRecordGroupEquipment.getOrderIndex() != null) {
                    existingInspectionRecordGroupEquipment.setOrderIndex(inspectionRecordGroupEquipment.getOrderIndex());
                }
                if (inspectionRecordGroupEquipment.getStatus() != null) {
                    existingInspectionRecordGroupEquipment.setStatus(inspectionRecordGroupEquipment.getStatus());
                }

                return existingInspectionRecordGroupEquipment;
            })
            .map(inspectionRecordGroupEquipmentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecordGroupEquipment.getId().toString())
        );
    }

    /**
     * {@code GET  /inspection-record-group-equipments} : get all the inspectionRecordGroupEquipments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectionRecordGroupEquipments in body.
     */
    @GetMapping("")
    public List<InspectionRecordGroupEquipment> getAllInspectionRecordGroupEquipments() {
        LOG.debug("REST request to get all InspectionRecordGroupEquipments");
        return inspectionRecordGroupEquipmentRepository.findAll();
    }

    /**
     * {@code GET  /inspection-record-group-equipments/:id} : get the "id" inspectionRecordGroupEquipment.
     *
     * @param id the id of the inspectionRecordGroupEquipment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectionRecordGroupEquipment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InspectionRecordGroupEquipment> getInspectionRecordGroupEquipment(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get InspectionRecordGroupEquipment : {}", id);
        Optional<InspectionRecordGroupEquipment> inspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspectionRecordGroupEquipment);
    }

    /**
     * {@code DELETE  /inspection-record-group-equipments/:id} : delete the "id" inspectionRecordGroupEquipment.
     *
     * @param id the id of the inspectionRecordGroupEquipment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectionRecordGroupEquipment(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete InspectionRecordGroupEquipment : {}", id);
        inspectionRecordGroupEquipmentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
