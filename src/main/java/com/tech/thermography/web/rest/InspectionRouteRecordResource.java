package com.tech.thermography.web.rest;

import com.tech.thermography.domain.InspectionRouteRecord;
import com.tech.thermography.repository.InspectionRouteRecordRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.InspectionRouteRecord}.
 */
@RestController
@RequestMapping("/api/inspection-route-records")
@Transactional
public class InspectionRouteRecordResource {

    private static final Logger LOG = LoggerFactory.getLogger(InspectionRouteRecordResource.class);

    private static final String ENTITY_NAME = "inspectionRouteRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionRouteRecordRepository inspectionRouteRecordRepository;

    public InspectionRouteRecordResource(InspectionRouteRecordRepository inspectionRouteRecordRepository) {
        this.inspectionRouteRecordRepository = inspectionRouteRecordRepository;
    }

    /**
     * {@code POST  /inspection-route-records} : Create a new inspectionRouteRecord.
     *
     * @param inspectionRouteRecord the inspectionRouteRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectionRouteRecord, or with status {@code 400 (Bad Request)} if the inspectionRouteRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InspectionRouteRecord> createInspectionRouteRecord(
        @Valid @RequestBody InspectionRouteRecord inspectionRouteRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to save InspectionRouteRecord : {}", inspectionRouteRecord);
        if (inspectionRouteRecord.getId() != null) {
            throw new BadRequestAlertException("A new inspectionRouteRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inspectionRouteRecord = inspectionRouteRecordRepository.save(inspectionRouteRecord);
        return ResponseEntity.created(new URI("/api/inspection-route-records/" + inspectionRouteRecord.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inspectionRouteRecord.getId().toString()))
            .body(inspectionRouteRecord);
    }

    /**
     * {@code PUT  /inspection-route-records/:id} : Updates an existing inspectionRouteRecord.
     *
     * @param id the id of the inspectionRouteRecord to save.
     * @param inspectionRouteRecord the inspectionRouteRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRouteRecord,
     * or with status {@code 400 (Bad Request)} if the inspectionRouteRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRouteRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InspectionRouteRecord> updateInspectionRouteRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody InspectionRouteRecord inspectionRouteRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to update InspectionRouteRecord : {}, {}", id, inspectionRouteRecord);
        if (inspectionRouteRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRouteRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRouteRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inspectionRouteRecord = inspectionRouteRecordRepository.save(inspectionRouteRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRouteRecord.getId().toString()))
            .body(inspectionRouteRecord);
    }

    /**
     * {@code PATCH  /inspection-route-records/:id} : Partial updates given fields of an existing inspectionRouteRecord, field will ignore if it is null
     *
     * @param id the id of the inspectionRouteRecord to save.
     * @param inspectionRouteRecord the inspectionRouteRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRouteRecord,
     * or with status {@code 400 (Bad Request)} if the inspectionRouteRecord is not valid,
     * or with status {@code 404 (Not Found)} if the inspectionRouteRecord is not found,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRouteRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InspectionRouteRecord> partialUpdateInspectionRouteRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody InspectionRouteRecord inspectionRouteRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InspectionRouteRecord partially : {}, {}", id, inspectionRouteRecord);
        if (inspectionRouteRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRouteRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRouteRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InspectionRouteRecord> result = inspectionRouteRecordRepository
            .findById(inspectionRouteRecord.getId())
            .map(existingInspectionRouteRecord -> {
                if (inspectionRouteRecord.getCode() != null) {
                    existingInspectionRouteRecord.setCode(inspectionRouteRecord.getCode());
                }
                if (inspectionRouteRecord.getName() != null) {
                    existingInspectionRouteRecord.setName(inspectionRouteRecord.getName());
                }
                if (inspectionRouteRecord.getDescription() != null) {
                    existingInspectionRouteRecord.setDescription(inspectionRouteRecord.getDescription());
                }
                if (inspectionRouteRecord.getMaintenanceDocument() != null) {
                    existingInspectionRouteRecord.setMaintenanceDocument(inspectionRouteRecord.getMaintenanceDocument());
                }
                if (inspectionRouteRecord.getCreatedAt() != null) {
                    existingInspectionRouteRecord.setCreatedAt(inspectionRouteRecord.getCreatedAt());
                }
                if (inspectionRouteRecord.getExpectedStartDate() != null) {
                    existingInspectionRouteRecord.setExpectedStartDate(inspectionRouteRecord.getExpectedStartDate());
                }
                if (inspectionRouteRecord.getExpectedEndDate() != null) {
                    existingInspectionRouteRecord.setExpectedEndDate(inspectionRouteRecord.getExpectedEndDate());
                }
                if (inspectionRouteRecord.getStarted() != null) {
                    existingInspectionRouteRecord.setStarted(inspectionRouteRecord.getStarted());
                }
                if (inspectionRouteRecord.getStartedAt() != null) {
                    existingInspectionRouteRecord.setStartedAt(inspectionRouteRecord.getStartedAt());
                }
                if (inspectionRouteRecord.getFinished() != null) {
                    existingInspectionRouteRecord.setFinished(inspectionRouteRecord.getFinished());
                }
                if (inspectionRouteRecord.getFinishedAt() != null) {
                    existingInspectionRouteRecord.setFinishedAt(inspectionRouteRecord.getFinishedAt());
                }

                return existingInspectionRouteRecord;
            })
            .map(inspectionRouteRecordRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRouteRecord.getId().toString())
        );
    }

    /**
     * {@code GET  /inspection-route-records} : get all the inspectionRouteRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectionRouteRecords in body.
     */
    @GetMapping("")
    public List<InspectionRouteRecord> getAllInspectionRouteRecords() {
        LOG.debug("REST request to get all InspectionRouteRecords");
        return inspectionRouteRecordRepository.findAll();
    }

    /**
     * {@code GET  /inspection-route-records/:id} : get the "id" inspectionRouteRecord.
     *
     * @param id the id of the inspectionRouteRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectionRouteRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InspectionRouteRecord> getInspectionRouteRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get InspectionRouteRecord : {}", id);
        Optional<InspectionRouteRecord> inspectionRouteRecord = inspectionRouteRecordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspectionRouteRecord);
    }

    /**
     * {@code DELETE  /inspection-route-records/:id} : delete the "id" inspectionRouteRecord.
     *
     * @param id the id of the inspectionRouteRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectionRouteRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete InspectionRouteRecord : {}", id);
        inspectionRouteRecordRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
