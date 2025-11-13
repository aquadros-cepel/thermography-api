package com.tech.thermography.web.rest;

import com.tech.thermography.domain.ThermographicInspectionRecord;
import com.tech.thermography.repository.ThermographicInspectionRecordRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.ThermographicInspectionRecord}.
 */
@RestController
@RequestMapping("/api/thermographic-inspection-records")
@Transactional
public class ThermographicInspectionRecordResource {

    private static final Logger LOG = LoggerFactory.getLogger(ThermographicInspectionRecordResource.class);

    private static final String ENTITY_NAME = "thermographicInspectionRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThermographicInspectionRecordRepository thermographicInspectionRecordRepository;

    public ThermographicInspectionRecordResource(ThermographicInspectionRecordRepository thermographicInspectionRecordRepository) {
        this.thermographicInspectionRecordRepository = thermographicInspectionRecordRepository;
    }

    /**
     * {@code POST  /thermographic-inspection-records} : Create a new thermographicInspectionRecord.
     *
     * @param thermographicInspectionRecord the thermographicInspectionRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thermographicInspectionRecord, or with status {@code 400 (Bad Request)} if the thermographicInspectionRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ThermographicInspectionRecord> createThermographicInspectionRecord(
        @Valid @RequestBody ThermographicInspectionRecord thermographicInspectionRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to save ThermographicInspectionRecord : {}", thermographicInspectionRecord);
        if (thermographicInspectionRecord.getId() != null) {
            throw new BadRequestAlertException("A new thermographicInspectionRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        thermographicInspectionRecord = thermographicInspectionRecordRepository.save(thermographicInspectionRecord);
        return ResponseEntity.created(new URI("/api/thermographic-inspection-records/" + thermographicInspectionRecord.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, thermographicInspectionRecord.getId().toString())
            )
            .body(thermographicInspectionRecord);
    }

    /**
     * {@code PUT  /thermographic-inspection-records/:id} : Updates an existing thermographicInspectionRecord.
     *
     * @param id the id of the thermographicInspectionRecord to save.
     * @param thermographicInspectionRecord the thermographicInspectionRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thermographicInspectionRecord,
     * or with status {@code 400 (Bad Request)} if the thermographicInspectionRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thermographicInspectionRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ThermographicInspectionRecord> updateThermographicInspectionRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ThermographicInspectionRecord thermographicInspectionRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to update ThermographicInspectionRecord : {}, {}", id, thermographicInspectionRecord);
        if (thermographicInspectionRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thermographicInspectionRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thermographicInspectionRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        thermographicInspectionRecord = thermographicInspectionRecordRepository.save(thermographicInspectionRecord);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thermographicInspectionRecord.getId().toString())
            )
            .body(thermographicInspectionRecord);
    }

    /**
     * {@code PATCH  /thermographic-inspection-records/:id} : Partial updates given fields of an existing thermographicInspectionRecord, field will ignore if it is null
     *
     * @param id the id of the thermographicInspectionRecord to save.
     * @param thermographicInspectionRecord the thermographicInspectionRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thermographicInspectionRecord,
     * or with status {@code 400 (Bad Request)} if the thermographicInspectionRecord is not valid,
     * or with status {@code 404 (Not Found)} if the thermographicInspectionRecord is not found,
     * or with status {@code 500 (Internal Server Error)} if the thermographicInspectionRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThermographicInspectionRecord> partialUpdateThermographicInspectionRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ThermographicInspectionRecord thermographicInspectionRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ThermographicInspectionRecord partially : {}, {}", id, thermographicInspectionRecord);
        if (thermographicInspectionRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thermographicInspectionRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thermographicInspectionRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThermographicInspectionRecord> result = thermographicInspectionRecordRepository
            .findById(thermographicInspectionRecord.getId())
            .map(existingThermographicInspectionRecord -> {
                if (thermographicInspectionRecord.getName() != null) {
                    existingThermographicInspectionRecord.setName(thermographicInspectionRecord.getName());
                }
                if (thermographicInspectionRecord.getType() != null) {
                    existingThermographicInspectionRecord.setType(thermographicInspectionRecord.getType());
                }
                if (thermographicInspectionRecord.getServiceOrder() != null) {
                    existingThermographicInspectionRecord.setServiceOrder(thermographicInspectionRecord.getServiceOrder());
                }
                if (thermographicInspectionRecord.getCreatedAt() != null) {
                    existingThermographicInspectionRecord.setCreatedAt(thermographicInspectionRecord.getCreatedAt());
                }
                if (thermographicInspectionRecord.getAnalysisDescription() != null) {
                    existingThermographicInspectionRecord.setAnalysisDescription(thermographicInspectionRecord.getAnalysisDescription());
                }
                if (thermographicInspectionRecord.getCondition() != null) {
                    existingThermographicInspectionRecord.setCondition(thermographicInspectionRecord.getCondition());
                }
                if (thermographicInspectionRecord.getDeltaT() != null) {
                    existingThermographicInspectionRecord.setDeltaT(thermographicInspectionRecord.getDeltaT());
                }
                if (thermographicInspectionRecord.getPeriodicity() != null) {
                    existingThermographicInspectionRecord.setPeriodicity(thermographicInspectionRecord.getPeriodicity());
                }
                if (thermographicInspectionRecord.getDeadlineExecution() != null) {
                    existingThermographicInspectionRecord.setDeadlineExecution(thermographicInspectionRecord.getDeadlineExecution());
                }
                if (thermographicInspectionRecord.getNextMonitoring() != null) {
                    existingThermographicInspectionRecord.setNextMonitoring(thermographicInspectionRecord.getNextMonitoring());
                }
                if (thermographicInspectionRecord.getRecommendations() != null) {
                    existingThermographicInspectionRecord.setRecommendations(thermographicInspectionRecord.getRecommendations());
                }
                if (thermographicInspectionRecord.getFinished() != null) {
                    existingThermographicInspectionRecord.setFinished(thermographicInspectionRecord.getFinished());
                }
                if (thermographicInspectionRecord.getFinishedAt() != null) {
                    existingThermographicInspectionRecord.setFinishedAt(thermographicInspectionRecord.getFinishedAt());
                }

                return existingThermographicInspectionRecord;
            })
            .map(thermographicInspectionRecordRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thermographicInspectionRecord.getId().toString())
        );
    }

    /**
     * {@code GET  /thermographic-inspection-records} : get all the thermographicInspectionRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thermographicInspectionRecords in body.
     */
    @GetMapping("")
    public List<ThermographicInspectionRecord> getAllThermographicInspectionRecords() {
        LOG.debug("REST request to get all ThermographicInspectionRecords");
        return thermographicInspectionRecordRepository.findAll();
    }

    /**
     * {@code GET  /thermographic-inspection-records/:id} : get the "id" thermographicInspectionRecord.
     *
     * @param id the id of the thermographicInspectionRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thermographicInspectionRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ThermographicInspectionRecord> getThermographicInspectionRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get ThermographicInspectionRecord : {}", id);
        Optional<ThermographicInspectionRecord> thermographicInspectionRecord = thermographicInspectionRecordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thermographicInspectionRecord);
    }

    /**
     * {@code DELETE  /thermographic-inspection-records/:id} : delete the "id" thermographicInspectionRecord.
     *
     * @param id the id of the thermographicInspectionRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThermographicInspectionRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete ThermographicInspectionRecord : {}", id);
        thermographicInspectionRecordRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
