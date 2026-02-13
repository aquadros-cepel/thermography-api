package com.tech.thermography.web.rest;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentComponent;
import com.tech.thermography.domain.InspectionRecord;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.Thermogram;
import com.tech.thermography.domain.ThermographicInspectionRecord;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.repository.EquipmentComponentRepository;
import com.tech.thermography.repository.EquipmentRepository;
import com.tech.thermography.repository.InspectionRecordRepository;
import com.tech.thermography.repository.PlantRepository;
import com.tech.thermography.repository.ROIRepository;
import com.tech.thermography.repository.ThermogramRepository;
import com.tech.thermography.repository.ThermographicInspectionRecordRepository;
import com.tech.thermography.repository.UserInfoRepository;
import com.tech.thermography.web.rest.errors.BadRequestAlertException;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.tech.thermography.domain.ThermographicInspectionRecord}.
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
    private final ThermogramRepository thermogramRepository;
    private final ROIRepository roiRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentComponentRepository equipmentComponentRepository;
    private final InspectionRecordRepository inspectionRecordRepository;
    private final PlantRepository plantRepository;
    private final UserInfoRepository userInfoRepository;

    private final EntityManager entityManager;

    public ThermographicInspectionRecordResource(
        ThermographicInspectionRecordRepository thermographicInspectionRecordRepository,
        ThermogramRepository thermogramRepository,
        ROIRepository roiRepository,
        EquipmentRepository equipmentRepository,
        EquipmentComponentRepository equipmentComponentRepository,
        InspectionRecordRepository inspectionRecordRepository,
        PlantRepository plantRepository,
        UserInfoRepository userInfoRepository,
        EntityManager entityManager
    ) {
        this.entityManager = entityManager;
        this.thermographicInspectionRecordRepository = thermographicInspectionRecordRepository;
        this.thermogramRepository = thermogramRepository;
        this.roiRepository = roiRepository;
        this.equipmentRepository = equipmentRepository;
        this.equipmentComponentRepository = equipmentComponentRepository;
        this.inspectionRecordRepository = inspectionRecordRepository;
        this.plantRepository = plantRepository;
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * {@code POST  /thermographic-inspection-records} : Create a new
     * thermographicInspectionRecord.
     *
     * @param thermographicInspectionRecord the thermographicInspectionRecord to
     *                                      create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new thermographicInspectionRecord, or with status
     *         {@code 400 (Bad Request)} if the thermographicInspectionRecord has
     *         already an ID.
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
     * {@code PUT  /thermographic-inspection-records/:id} : Updates an existing
     * thermographicInspectionRecord.
     *
     * @param id                            the id of the
     *                                      thermographicInspectionRecord to save.
     * @param thermographicInspectionRecord the thermographicInspectionRecord to
     *                                      update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated thermographicInspectionRecord,
     *         or with status {@code 400 (Bad Request)} if the
     *         thermographicInspectionRecord is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         thermographicInspectionRecord couldn't be updated.
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
     * {@code PATCH  /thermographic-inspection-records/:id} : Partial updates given
     * fields of an existing thermographicInspectionRecord, field will ignore if it
     * is null
     *
     * @param id                            the id of the
     *                                      thermographicInspectionRecord to save.
     * @param thermographicInspectionRecord the thermographicInspectionRecord to
     *                                      update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated thermographicInspectionRecord,
     *         or with status {@code 400 (Bad Request)} if the
     *         thermographicInspectionRecord is not valid,
     *         or with status {@code 404 (Not Found)} if the
     *         thermographicInspectionRecord is not found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         thermographicInspectionRecord couldn't be updated.
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
     * {@code GET  /thermographic-inspection-records} : get all the
     * thermographicInspectionRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of thermographicInspectionRecords in body.
     */
    @GetMapping("")
    public List<ThermographicInspectionRecord> getAllThermographicInspectionRecords() {
        LOG.debug("REST request to get all ThermographicInspectionRecords");
        List<ThermographicInspectionRecord> records = thermographicInspectionRecordRepository.findAllWithRelationships();

        // Segunda query para carregar thermogramRef (quando existe) e seus ROIs
        if (!records.isEmpty()) {
            records = thermographicInspectionRecordRepository.findWithThermogramRef(records);
        }

        return records;
    }

    /**
     * {@code GET  /thermographic-inspection-records/:id} : get the "id"
     * thermographicInspectionRecord.
     *
     * @param id the id of the thermographicInspectionRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the thermographicInspectionRecord, or with status
     *         {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ThermographicInspectionRecord> getThermographicInspectionRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get ThermographicInspectionRecord : {}", id);
        Optional<ThermographicInspectionRecord> thermographicInspectionRecord = thermographicInspectionRecordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thermographicInspectionRecord);
    }

    /**
     * {@code DELETE  /thermographic-inspection-records/:id} : delete the "id"
     * thermographicInspectionRecord.
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

    /**
     * {@code POST  /thermographic-inspection-records/actions/create} : Create a new
     * thermographicInspectionRecord from payload.
     *
     * @param payload the thermographicInspectionRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new thermographicInspectionRecord, or with status
     *         {@code 400 (Bad Request)} if the payload is invalid.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actions/create")
    public ResponseEntity<ThermographicInspectionRecord> createThermographicInspectionRecordFromPayload(
        @RequestBody ThermographicInspectionRecord payload
    ) throws URISyntaxException {
        LOG.debug("REST request to create ThermographicInspectionRecord from payload");

        if (payload == null) {
            throw new BadRequestAlertException("Payload inválido", ENTITY_NAME, "payloadnull");
        }
        if (payload.getEquipment() == null || payload.getEquipment().getId() == null) {
            throw new BadRequestAlertException("Equipment obrigatório", ENTITY_NAME, "equipmentnull");
        }
        if (payload.getPlant() == null || payload.getPlant().getId() == null) {
            throw new BadRequestAlertException("Plant obrigatória", ENTITY_NAME, "plantnull");
        }
        if (payload.getCreatedBy() == null || payload.getCreatedBy().getId() == null) {
            throw new BadRequestAlertException("CreatedBy obrigatório", ENTITY_NAME, "createdbynull");
        }
        if (payload.getThermogram() == null) {
            throw new BadRequestAlertException("Thermogram obrigatório", ENTITY_NAME, "thermogramnull");
        }

        Equipment equipment = equipmentRepository
            .findById(payload.getEquipment().getId())
            .orElseThrow(() -> new BadRequestAlertException("Equipment não encontrado", ENTITY_NAME, "equipmentnotfound"));

        Plant plant = plantRepository
            .findById(payload.getPlant().getId())
            .orElseThrow(() -> new BadRequestAlertException("Plant não encontrada", ENTITY_NAME, "plantnotfound"));

        UserInfo createdBy = userInfoRepository
            .findById(payload.getCreatedBy().getId())
            .orElseThrow(() -> new BadRequestAlertException("UserInfo não encontrado", ENTITY_NAME, "createdbynotfound"));

        UserInfo finishedBy = createdBy;
        if (payload.getFinishedBy() != null && payload.getFinishedBy().getId() != null) {
            finishedBy = userInfoRepository
                .findById(payload.getFinishedBy().getId())
                .orElseThrow(() -> new BadRequestAlertException("UserInfo finishedBy não encontrado", ENTITY_NAME, "finishedbynotfound"));
        }

        InspectionRecord route = null;
        if (payload.getRoute() != null && payload.getRoute().getId() != null) {
            String routeId = payload.getRoute().getId().toString();
            if (!"-1".equals(routeId)) {
                route = inspectionRecordRepository
                    .findById(payload.getRoute().getId())
                    .orElseThrow(() -> new BadRequestAlertException("InspectionRecord não encontrado", ENTITY_NAME, "routenotfound"));
            }
        }

        EquipmentComponent component = null;
        if (payload.getComponent() != null && payload.getComponent().getId() != null) {
            component = equipmentComponentRepository
                .findById(payload.getComponent().getId())
                .orElseThrow(() -> new BadRequestAlertException("Component não encontrado", ENTITY_NAME, "componentnotfound"));
        }

        Thermogram thermogram = payload.getThermogram();
        Thermogram thermogramRef = payload.getThermogramRef();

        if (thermogram != null) {
            if (thermogram.getEquipment() == null) thermogram.setEquipment(equipment);

            if (thermogram.getCreatedBy() == null) thermogram.setCreatedBy(finishedBy);

            if (thermogram.getCreatedAt() == null) thermogram.setCreatedAt(Instant.now());

            entityManager.persist(thermogram);
            for (var roi : thermogram.getRois()) {
                roi.setThermogram(thermogram);
                roiRepository.save(roi);
            }
        }

        if (thermogramRef != null) {
            if (thermogramRef.getEquipment() == null) thermogramRef.setEquipment(equipment);

            if (thermogramRef.getCreatedBy() == null) thermogramRef.setCreatedBy(finishedBy);

            if (thermogramRef.getCreatedAt() == null) thermogramRef.setCreatedAt(Instant.now());

            entityManager.persist(thermogramRef);
            for (var roi : thermogramRef.getRois()) {
                roi.setThermogram(thermogramRef);
                roiRepository.save(roi);
            }
        }

        ThermographicInspectionRecord record = new ThermographicInspectionRecord();
        record.setId(null);
        record.setName(payload.getName());
        record.setType(payload.getType());
        record.setServiceOrder(payload.getServiceOrder());
        record.setCreatedAt(payload.getCreatedAt() != null ? payload.getCreatedAt() : Instant.now());
        record.setAnalysisDescription(payload.getAnalysisDescription());
        record.setCondition(payload.getCondition());
        record.setDeltaT(payload.getDeltaT());
        record.setPeriodicity(payload.getPeriodicity());
        record.setDeadlineExecution(payload.getDeadlineExecution());
        record.setNextMonitoring(payload.getNextMonitoring());
        record.setRecommendations(payload.getRecommendations());
        record.setFinished(payload.getFinished() != null ? payload.getFinished() : Boolean.FALSE);
        record.setPlant(plant);
        record.setEquipment(equipment);
        record.setComponent(component);
        record.setRoute(route);
        record.setCreatedBy(createdBy);
        record.setFinishedBy(finishedBy);
        record.setThermogram(thermogram);
        record.setThermogramRef(thermogramRef);

        record = thermographicInspectionRecordRepository.save(record);

        return ResponseEntity.created(new URI("/api/thermographic-inspection-records/" + record.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, record.getId().toString()))
            .body(record);
    }

    /**
     * {@code POST  /thermographic-inspection-records/actions/edit} : Edit an
     * existing
     * thermographicInspectionRecord from payload.
     *
     * Rules:
     * 1 - ThermographicInspectionRecord data can be directly edited
     * 2 - Check if thermogram ID and reference thermogram ID changed:
     * - If ID changed (different thermogram): delete old thermogram and ROIs,
     * create new
     * - If ID is the same: just edit thermogram data (ROIs are not changed in edit)
     * 3 - Creating new thermogram/ROIs follows the same code as the create endpoint
     *
     * @param payload the thermographicInspectionRecord to edit.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     *         body the updated thermographicInspectionRecord, or with status
     *         {@code 400 (Bad Request)} if the payload is invalid.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actions/update")
    public ResponseEntity<ThermographicInspectionRecord> updateThermographicInspectionRecordFromPayload(
        @RequestBody ThermographicInspectionRecord payload
    ) throws URISyntaxException {
        LOG.debug("REST request to edit ThermographicInspectionRecord from payload");

        if (payload == null || payload.getId() == null) {
            throw new BadRequestAlertException("Payload inválido ou ID não fornecido", ENTITY_NAME, "payloadnull");
        }

        // Get existing record
        ThermographicInspectionRecord existingRecord = thermographicInspectionRecordRepository
            .findById(payload.getId())
            .orElseThrow(() -> new BadRequestAlertException("Registro não encontrado", ENTITY_NAME, "recordnotfound"));

        // Validate required fields
        if (payload.getEquipment() == null || payload.getEquipment().getId() == null) {
            throw new BadRequestAlertException("Equipment obrigatório", ENTITY_NAME, "equipmentnull");
        }
        if (payload.getPlant() == null || payload.getPlant().getId() == null) {
            throw new BadRequestAlertException("Plant obrigatória", ENTITY_NAME, "plantnull");
        }
        if (payload.getCreatedBy() == null || payload.getCreatedBy().getId() == null) {
            throw new BadRequestAlertException("CreatedBy obrigatório", ENTITY_NAME, "createdbynull");
        }
        if (payload.getThermogram() == null) {
            throw new BadRequestAlertException("Thermogram obrigatório", ENTITY_NAME, "thermogramnull");
        }

        // Get related entities
        Equipment equipment = equipmentRepository
            .findById(payload.getEquipment().getId())
            .orElseThrow(() -> new BadRequestAlertException("Equipment não encontrado", ENTITY_NAME, "equipmentnotfound"));

        Plant plant = plantRepository
            .findById(payload.getPlant().getId())
            .orElseThrow(() -> new BadRequestAlertException("Plant não encontrada", ENTITY_NAME, "plantnotfound"));

        UserInfo createdBy = userInfoRepository
            .findById(payload.getCreatedBy().getId())
            .orElseThrow(() -> new BadRequestAlertException("UserInfo não encontrado", ENTITY_NAME, "createdbynotfound"));

        UserInfo finishedBy = createdBy;
        if (payload.getFinishedBy() != null && payload.getFinishedBy().getId() != null) {
            finishedBy = userInfoRepository
                .findById(payload.getFinishedBy().getId())
                .orElseThrow(() -> new BadRequestAlertException("UserInfo finishedBy não encontrado", ENTITY_NAME, "finishedbynotfound"));
        }

        InspectionRecord route = null;
        if (payload.getRoute() != null && payload.getRoute().getId() != null) {
            String routeId = payload.getRoute().getId().toString();
            if (!"-1".equals(routeId)) {
                route = inspectionRecordRepository
                    .findById(payload.getRoute().getId())
                    .orElseThrow(() -> new BadRequestAlertException("InspectionRecord não encontrado", ENTITY_NAME, "routenotfound"));
            }
        }

        EquipmentComponent component = null;
        if (payload.getComponent() != null && payload.getComponent().getId() != null) {
            component = equipmentComponentRepository
                .findById(payload.getComponent().getId())
                .orElseThrow(() -> new BadRequestAlertException("Component não encontrado", ENTITY_NAME, "componentnotfound"));
        }

        // Get existing thermograms for comparison
        Thermogram existingThermogram = existingRecord.getThermogram();
        Thermogram existingThermogramRef = existingRecord.getThermogramRef();

        // Handle thermogram
        Thermogram newThermogram = payload.getThermogram();
        UUID existingThermogramId = existingThermogram != null ? existingThermogram.getId() : null;
        UUID newThermogramId = newThermogram != null && newThermogram.getId() != null ? newThermogram.getId() : null;

        if (!Objects.equals(existingThermogramId, newThermogramId)) {
            // Thermogram ID changed - delete old thermogram with ROIs and create new one
            if (existingThermogram != null) {
                thermogramRepository.delete(existingThermogram);
            }
            // Create new thermogram (same as create endpoint)
            if (newThermogram != null) {
                if (newThermogram.getEquipment() == null) newThermogram.setEquipment(equipment);
                if (newThermogram.getCreatedBy() == null) newThermogram.setCreatedBy(finishedBy);
                if (newThermogram.getCreatedAt() == null) newThermogram.setCreatedAt(Instant.now());
                entityManager.persist(newThermogram);
                for (var roi : newThermogram.getRois()) {
                    roi.setThermogram(newThermogram);
                    roiRepository.save(roi);
                }
            }
        } else {
            // Same thermogram ID - just update thermogram data (ROIs not changed)
            if (existingThermogram != null && newThermogram != null) {
                existingThermogram.setImagePath(newThermogram.getImagePath());
                existingThermogram.setAudioPath(newThermogram.getAudioPath());
                existingThermogram.setImageRefPath(newThermogram.getImageRefPath());
                existingThermogram.setMinTemp(newThermogram.getMinTemp());
                existingThermogram.setAvgTemp(newThermogram.getAvgTemp());
                existingThermogram.setMaxTemp(newThermogram.getMaxTemp());
                existingThermogram.setEmissivity(newThermogram.getEmissivity());
                existingThermogram.setSubjectDistance(newThermogram.getSubjectDistance());
                existingThermogram.setAtmosphericTemp(newThermogram.getAtmosphericTemp());
                existingThermogram.setReflectedTemp(newThermogram.getReflectedTemp());
                existingThermogram.setRelativeHumidity(newThermogram.getRelativeHumidity());
                existingThermogram.setCameraLens(newThermogram.getCameraLens());
                existingThermogram.setCameraModel(newThermogram.getCameraModel());
                existingThermogram.setImageResolution(newThermogram.getImageResolution());
                existingThermogram.setSelectedRoiId(newThermogram.getSelectedRoiId());
                existingThermogram.setMaxTempRoi(newThermogram.getMaxTempRoi());
                existingThermogram.setLatitude(newThermogram.getLatitude());
                existingThermogram.setLongitude(newThermogram.getLongitude());
                thermogramRepository.save(existingThermogram);
            }
        }

        // Handle thermogramRef
        Thermogram newThermogramRef = payload.getThermogramRef();
        UUID existingThermogramRefId = existingThermogramRef != null ? existingThermogramRef.getId() : null;
        UUID newThermogramRefId = newThermogramRef != null && newThermogramRef.getId() != null ? newThermogramRef.getId() : null;

        if (!Objects.equals(existingThermogramRefId, newThermogramRefId)) {
            // ThermogramRef ID changed - delete old thermogramRef with ROIs and create new
            // one
            if (existingThermogramRef != null) {
                thermogramRepository.delete(existingThermogramRef);
            }
            // Create new thermogramRef (same as create endpoint)
            if (newThermogramRef != null) {
                if (newThermogramRef.getEquipment() == null) newThermogramRef.setEquipment(equipment);
                if (newThermogramRef.getCreatedBy() == null) newThermogramRef.setCreatedBy(finishedBy);
                if (newThermogramRef.getCreatedAt() == null) newThermogramRef.setCreatedAt(Instant.now());
                entityManager.persist(newThermogramRef);
                for (var roi : newThermogramRef.getRois()) {
                    roi.setThermogram(newThermogramRef);
                    roiRepository.save(roi);
                }
            }
        } else {
            // Same thermogramRef ID - just update thermogramRef data (ROIs not changed)
            if (existingThermogramRef != null && newThermogramRef != null) {
                existingThermogramRef.setImagePath(newThermogramRef.getImagePath());
                existingThermogramRef.setAudioPath(newThermogramRef.getAudioPath());
                existingThermogramRef.setImageRefPath(newThermogramRef.getImageRefPath());
                existingThermogramRef.setMinTemp(newThermogramRef.getMinTemp());
                existingThermogramRef.setAvgTemp(newThermogramRef.getAvgTemp());
                existingThermogramRef.setMaxTemp(newThermogramRef.getMaxTemp());
                existingThermogramRef.setEmissivity(newThermogramRef.getEmissivity());
                existingThermogramRef.setSubjectDistance(newThermogramRef.getSubjectDistance());
                existingThermogramRef.setAtmosphericTemp(newThermogramRef.getAtmosphericTemp());
                existingThermogramRef.setReflectedTemp(newThermogramRef.getReflectedTemp());
                existingThermogramRef.setRelativeHumidity(newThermogramRef.getRelativeHumidity());
                existingThermogramRef.setCameraLens(newThermogramRef.getCameraLens());
                existingThermogramRef.setCameraModel(newThermogramRef.getCameraModel());
                existingThermogramRef.setImageResolution(newThermogramRef.getImageResolution());
                existingThermogramRef.setSelectedRoiId(newThermogramRef.getSelectedRoiId());
                existingThermogramRef.setMaxTempRoi(newThermogramRef.getMaxTempRoi());
                existingThermogramRef.setLatitude(newThermogramRef.getLatitude());
                existingThermogramRef.setLongitude(newThermogramRef.getLongitude());
                thermogramRepository.save(existingThermogramRef);
            }
        }

        // Update ThermographicInspectionRecord data
        existingRecord.setName(payload.getName());
        existingRecord.setType(payload.getType());
        existingRecord.setServiceOrder(payload.getServiceOrder());
        existingRecord.setAnalysisDescription(payload.getAnalysisDescription());
        existingRecord.setCondition(payload.getCondition());
        existingRecord.setDeltaT(payload.getDeltaT());
        existingRecord.setPeriodicity(payload.getPeriodicity());
        existingRecord.setDeadlineExecution(payload.getDeadlineExecution());
        existingRecord.setNextMonitoring(payload.getNextMonitoring());
        existingRecord.setRecommendations(payload.getRecommendations());
        existingRecord.setFinished(payload.getFinished());
        existingRecord.setFinishedAt(payload.getFinishedAt());
        existingRecord.setPlant(plant);
        existingRecord.setEquipment(equipment);
        existingRecord.setComponent(component);
        existingRecord.setRoute(route);
        existingRecord.setCreatedBy(createdBy);
        existingRecord.setFinishedBy(finishedBy);

        // Set thermogram and thermogramRef (use the existing ones if IDs didn't change)
        if (!Objects.equals(existingThermogramId, newThermogramId)) {
            // New thermogram was created
            existingRecord.setThermogram(newThermogram);
        } else {
            // Use existing thermogram
            existingRecord.setThermogram(existingThermogram);
        }

        if (!Objects.equals(existingThermogramRefId, newThermogramRefId)) {
            // New thermogramRef was created (or removed if newThermogramRef is null)
            existingRecord.setThermogramRef(newThermogramRef);
        } else {
            // Use existing thermogramRef
            existingRecord.setThermogramRef(existingThermogramRef);
        }

        ThermographicInspectionRecord updatedRecord = thermographicInspectionRecordRepository.save(existingRecord);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, updatedRecord.getId().toString()))
            .body(updatedRecord);
    }
}
