package com.tech.thermography.web.rest;

import com.tech.thermography.domain.InspectionRouteGroupEquipment;
import com.tech.thermography.repository.InspectionRouteGroupEquipmentRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.InspectionRouteGroupEquipment}.
 */
@RestController
@RequestMapping("/api/inspection-route-group-equipments")
@Transactional
public class InspectionRouteGroupEquipmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(InspectionRouteGroupEquipmentResource.class);

    private static final String ENTITY_NAME = "inspectionRouteGroupEquipment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionRouteGroupEquipmentRepository inspectionRouteGroupEquipmentRepository;

    public InspectionRouteGroupEquipmentResource(InspectionRouteGroupEquipmentRepository inspectionRouteGroupEquipmentRepository) {
        this.inspectionRouteGroupEquipmentRepository = inspectionRouteGroupEquipmentRepository;
    }

    /**
     * {@code POST  /inspection-route-group-equipments} : Create a new inspectionRouteGroupEquipment.
     *
     * @param inspectionRouteGroupEquipment the inspectionRouteGroupEquipment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectionRouteGroupEquipment, or with status {@code 400 (Bad Request)} if the inspectionRouteGroupEquipment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InspectionRouteGroupEquipment> createInspectionRouteGroupEquipment(
        @Valid @RequestBody InspectionRouteGroupEquipment inspectionRouteGroupEquipment
    ) throws URISyntaxException {
        LOG.debug("REST request to save InspectionRouteGroupEquipment : {}", inspectionRouteGroupEquipment);
        if (inspectionRouteGroupEquipment.getId() != null) {
            throw new BadRequestAlertException("A new inspectionRouteGroupEquipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.save(inspectionRouteGroupEquipment);
        return ResponseEntity.created(new URI("/api/inspection-route-group-equipments/" + inspectionRouteGroupEquipment.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inspectionRouteGroupEquipment.getId().toString())
            )
            .body(inspectionRouteGroupEquipment);
    }

    /**
     * {@code PUT  /inspection-route-group-equipments/:id} : Updates an existing inspectionRouteGroupEquipment.
     *
     * @param id the id of the inspectionRouteGroupEquipment to save.
     * @param inspectionRouteGroupEquipment the inspectionRouteGroupEquipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRouteGroupEquipment,
     * or with status {@code 400 (Bad Request)} if the inspectionRouteGroupEquipment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRouteGroupEquipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InspectionRouteGroupEquipment> updateInspectionRouteGroupEquipment(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody InspectionRouteGroupEquipment inspectionRouteGroupEquipment
    ) throws URISyntaxException {
        LOG.debug("REST request to update InspectionRouteGroupEquipment : {}, {}", id, inspectionRouteGroupEquipment);
        if (inspectionRouteGroupEquipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRouteGroupEquipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRouteGroupEquipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.save(inspectionRouteGroupEquipment);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRouteGroupEquipment.getId().toString())
            )
            .body(inspectionRouteGroupEquipment);
    }

    /**
     * {@code PATCH  /inspection-route-group-equipments/:id} : Partial updates given fields of an existing inspectionRouteGroupEquipment, field will ignore if it is null
     *
     * @param id the id of the inspectionRouteGroupEquipment to save.
     * @param inspectionRouteGroupEquipment the inspectionRouteGroupEquipment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRouteGroupEquipment,
     * or with status {@code 400 (Bad Request)} if the inspectionRouteGroupEquipment is not valid,
     * or with status {@code 404 (Not Found)} if the inspectionRouteGroupEquipment is not found,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRouteGroupEquipment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InspectionRouteGroupEquipment> partialUpdateInspectionRouteGroupEquipment(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody InspectionRouteGroupEquipment inspectionRouteGroupEquipment
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InspectionRouteGroupEquipment partially : {}, {}", id, inspectionRouteGroupEquipment);
        if (inspectionRouteGroupEquipment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRouteGroupEquipment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRouteGroupEquipmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InspectionRouteGroupEquipment> result = inspectionRouteGroupEquipmentRepository
            .findById(inspectionRouteGroupEquipment.getId())
            .map(existingInspectionRouteGroupEquipment -> {
                if (inspectionRouteGroupEquipment.getIncluded() != null) {
                    existingInspectionRouteGroupEquipment.setIncluded(inspectionRouteGroupEquipment.getIncluded());
                }
                if (inspectionRouteGroupEquipment.getOrderIndex() != null) {
                    existingInspectionRouteGroupEquipment.setOrderIndex(inspectionRouteGroupEquipment.getOrderIndex());
                }

                return existingInspectionRouteGroupEquipment;
            })
            .map(inspectionRouteGroupEquipmentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRouteGroupEquipment.getId().toString())
        );
    }

    /**
     * {@code GET  /inspection-route-group-equipments} : get all the inspectionRouteGroupEquipments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectionRouteGroupEquipments in body.
     */
    @GetMapping("")
    public List<InspectionRouteGroupEquipment> getAllInspectionRouteGroupEquipments() {
        LOG.debug("REST request to get all InspectionRouteGroupEquipments");
        return inspectionRouteGroupEquipmentRepository.findAll();
    }

    /**
     * {@code GET  /inspection-route-group-equipments/:id} : get the "id" inspectionRouteGroupEquipment.
     *
     * @param id the id of the inspectionRouteGroupEquipment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectionRouteGroupEquipment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InspectionRouteGroupEquipment> getInspectionRouteGroupEquipment(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get InspectionRouteGroupEquipment : {}", id);
        Optional<InspectionRouteGroupEquipment> inspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspectionRouteGroupEquipment);
    }

    /**
     * {@code DELETE  /inspection-route-group-equipments/:id} : delete the "id" inspectionRouteGroupEquipment.
     *
     * @param id the id of the inspectionRouteGroupEquipment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectionRouteGroupEquipment(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete InspectionRouteGroupEquipment : {}", id);
        inspectionRouteGroupEquipmentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
