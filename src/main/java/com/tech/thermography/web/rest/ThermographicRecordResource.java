package com.tech.thermography.web.rest;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentComponent;
import com.tech.thermography.domain.InspectionRecord;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.ROI;
import com.tech.thermography.domain.Thermogram;
import com.tech.thermography.domain.ThermographicRecord;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.repository.EquipmentComponentRepository;
import com.tech.thermography.repository.EquipmentRepository;
import com.tech.thermography.repository.InspectionRecordRepository;
import com.tech.thermography.repository.PlantRepository;
import com.tech.thermography.repository.ROIRepository;
import com.tech.thermography.repository.ThermogramRepository;
import com.tech.thermography.repository.ThermographicRecordRepository;
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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
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
 * {@link com.tech.thermography.domain.ThermographicRecord}.
 */
@RestController
@RequestMapping("/api/thermographic-records")
@Transactional
public class ThermographicRecordResource {

    private static final Logger LOG = LoggerFactory.getLogger(ThermographicRecordResource.class);

    private static final String ENTITY_NAME = "thermographicRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThermographicRecordRepository thermographicRecordRepository;
    private final ThermogramRepository thermogramRepository;
    private final ROIRepository roiRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentComponentRepository equipmentComponentRepository;
    private final InspectionRecordRepository inspectionRecordRepository;
    private final PlantRepository plantRepository;
    private final UserInfoRepository userInfoRepository;

    private final EntityManager entityManager;

    public ThermographicRecordResource(
        ThermographicRecordRepository thermographicRecordRepository,
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
        this.thermographicRecordRepository = thermographicRecordRepository;
        this.thermogramRepository = thermogramRepository;
        this.roiRepository = roiRepository;
        this.equipmentRepository = equipmentRepository;
        this.equipmentComponentRepository = equipmentComponentRepository;
        this.inspectionRecordRepository = inspectionRecordRepository;
        this.plantRepository = plantRepository;
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * {@code POST  /thermographic-records} : Create a new thermographicRecord.
     *
     * @param thermographicRecord the thermographicRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new thermographicRecord, or with status
     *         {@code 400 (Bad Request)} if the thermographicRecord has already an
     *         ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ThermographicRecord> createThermographicRecord(@Valid @RequestBody ThermographicRecord thermographicRecord)
        throws URISyntaxException {
        LOG.debug("REST request to save ThermographicRecord : {}", thermographicRecord);
        if (thermographicRecord.getId() != null) {
            throw new BadRequestAlertException("A new thermographicRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        thermographicRecord = thermographicRecordRepository.save(thermographicRecord);
        return ResponseEntity.created(new URI("/api/thermographic-records/" + thermographicRecord.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, thermographicRecord.getId().toString()))
            .body(thermographicRecord);
    }

    /**
     * {@code PUT  /thermographic-records/:id} : Updates an existing
     * thermographicRecord.
     *
     * @param id                  the id of the thermographicRecord to save.
     * @param thermographicRecord the thermographicRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated thermographicRecord,
     *         or with status {@code 400 (Bad Request)} if the thermographicRecord
     *         is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         thermographicRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ThermographicRecord> updateThermographicRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ThermographicRecord thermographicRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to update ThermographicRecord : {}, {}", id, thermographicRecord);
        if (thermographicRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thermographicRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thermographicRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        thermographicRecord = thermographicRecordRepository.save(thermographicRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thermographicRecord.getId().toString()))
            .body(thermographicRecord);
    }

    /**
     * {@code PATCH  /thermographic-records/:id} : Partial updates given fields of
     * an existing thermographicRecord, field will ignore if it is null
     *
     * @param id                  the id of the thermographicRecord to save.
     * @param thermographicRecord the thermographicRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated thermographicRecord,
     *         or with status {@code 400 (Bad Request)} if the thermographicRecord
     *         is not valid,
     *         or with status {@code 404 (Not Found)} if the thermographicRecord is
     *         not found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         thermographicRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThermographicRecord> partialUpdateThermographicRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ThermographicRecord thermographicRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ThermographicRecord partially : {}, {}", id, thermographicRecord);
        if (thermographicRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thermographicRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thermographicRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThermographicRecord> result = thermographicRecordRepository
            .findById(thermographicRecord.getId())
            .map(existingThermographicRecord -> {
                if (thermographicRecord.getName() != null) {
                    existingThermographicRecord.setName(thermographicRecord.getName());
                }
                if (thermographicRecord.getType() != null) {
                    existingThermographicRecord.setType(thermographicRecord.getType());
                }
                if (thermographicRecord.getServiceOrder() != null) {
                    existingThermographicRecord.setServiceOrder(thermographicRecord.getServiceOrder());
                }
                if (thermographicRecord.getCreatedAt() != null) {
                    existingThermographicRecord.setCreatedAt(thermographicRecord.getCreatedAt());
                }
                if (thermographicRecord.getAnalysisDescription() != null) {
                    existingThermographicRecord.setAnalysisDescription(thermographicRecord.getAnalysisDescription());
                }
                if (thermographicRecord.getCondition() != null) {
                    existingThermographicRecord.setCondition(thermographicRecord.getCondition());
                }
                if (thermographicRecord.getDeltaT() != null) {
                    existingThermographicRecord.setDeltaT(thermographicRecord.getDeltaT());
                }
                if (thermographicRecord.getPeriodicity() != null) {
                    existingThermographicRecord.setPeriodicity(thermographicRecord.getPeriodicity());
                }
                if (thermographicRecord.getDeadlineExecution() != null) {
                    existingThermographicRecord.setDeadlineExecution(thermographicRecord.getDeadlineExecution());
                }
                if (thermographicRecord.getNextMonitoring() != null) {
                    existingThermographicRecord.setNextMonitoring(thermographicRecord.getNextMonitoring());
                }
                if (thermographicRecord.getRecommendations() != null) {
                    existingThermographicRecord.setRecommendations(thermographicRecord.getRecommendations());
                }
                if (thermographicRecord.getFinished() != null) {
                    existingThermographicRecord.setFinished(thermographicRecord.getFinished());
                }
                if (thermographicRecord.getFinishedAt() != null) {
                    existingThermographicRecord.setFinishedAt(thermographicRecord.getFinishedAt());
                }
                if (thermographicRecord.getCurrent() != null) {
                    existingThermographicRecord.setCurrent(thermographicRecord.getCurrent());
                }
                if (thermographicRecord.getVoltage() != null) {
                    existingThermographicRecord.setVoltage(thermographicRecord.getVoltage());
                }
                if (thermographicRecord.getWindSpeed() != null) {
                    existingThermographicRecord.setWindSpeed(thermographicRecord.getWindSpeed());
                }
                if (thermographicRecord.getParentRecord() != null && thermographicRecord.getParentRecord().getId() != null) {
                    existingThermographicRecord.setParentRecord(
                        thermographicRecordRepository
                            .findById(thermographicRecord.getParentRecord().getId())
                            .orElseThrow(() ->
                                new BadRequestAlertException(
                                    "Parent record not found Registro relacionado não encontrado",
                                    ENTITY_NAME,
                                    "parentnotfound"
                                )
                            )
                    );
                }

                return existingThermographicRecord;
            })
            .map(thermographicRecordRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thermographicRecord.getId().toString())
        );
    }

    /**
     * {@code GET  /thermographic-records} : get all the thermographicRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of thermographicRecords in body.
     */
    @GetMapping("")
    public List<ThermographicRecord> getAllThermographicRecords() {
        LOG.debug("REST request to get all ThermographicRecords");
        List<ThermographicRecord> records = thermographicRecordRepository.findAllWithRelationships();

        // Segunda query para carregar thermogramRef (quando existe) e seus ROIs
        if (!records.isEmpty()) {
            records = thermographicRecordRepository.findWithThermogramRef(records);
        }

        return records;
    }

    /**
     * {@code GET  /thermographic-records/:id} : get the "id" thermographicRecord.
     *
     * @param id the id of the thermographicRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the thermographicRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ThermographicRecord> getThermographicRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get ThermographicRecord : {}", id);
        Optional<ThermographicRecord> thermographicRecord = thermographicRecordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thermographicRecord);
    }

    /**
     * {@code DELETE  /thermographic-records/:id} : delete the "id"
     * thermographicRecord.
     *
     * @param id the id of the thermographicRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThermographicRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete ThermographicRecord : {}", id);
        thermographicRecordRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /thermographic-inspection-records/actions/create} : Create a new
     * thermographicRecord from payload.
     *
     * @param payload the thermographicRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new thermographicRecord, or with status
     *         {@code 400 (Bad Request)} if the payload is invalid.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actions/create")
    public ResponseEntity<ThermographicRecord> createThermographicRecordFromPayload(@RequestBody ThermographicRecord payload)
        throws URISyntaxException {
        LOG.debug("REST request to create ThermographicRecord from payload");

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

        ThermographicRecord record = new ThermographicRecord();
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
        record.setCurrent(payload.getCurrent());
        record.setVoltage(payload.getVoltage());
        record.setWindSpeed(payload.getWindSpeed());
        if (payload.getParentRecord() != null && payload.getParentRecord().getId() != null) {
            record.setParentRecord(
                thermographicRecordRepository
                    .findById(payload.getParentRecord().getId())
                    .orElseThrow(() -> new BadRequestAlertException("Registro relacionado não encontrado", ENTITY_NAME, "parentnotfound"))
            );
        }
        record.setPlant(plant);
        record.setEquipment(equipment);
        record.setComponent(component);
        record.setRoute(route);
        record.setCreatedBy(createdBy);
        record.setFinishedBy(finishedBy);
        record.setThermogram(thermogram);
        record.setThermogramRef(thermogramRef);

        record = thermographicRecordRepository.save(record);

        return ResponseEntity.created(new URI("/api/thermographic-inspection-records/" + record.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, record.getId().toString()))
            .body(record);
    }

    /**
     * {@code POST  /thermographic-inspection-records/actions/edit} : Edit an
     * existing
     * thermographicRecord from payload.
     *
     * Rules:
     * 1 - ThermographicRecord data can be directly edited
     * 2 - Check if thermogram ID and reference thermogram ID changed:
     * - If ID changed (different thermogram): delete old thermogram and ROIs,
     * create new
     * - If ID is the same: just edit thermogram data (ROIs are not changed in edit)
     * 3 - Creating new thermogram/ROIs follows the same code as the create endpoint
     *
     * @param payload the thermographicRecord to edit.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     *         body the updated thermographicRecord, or with status
     *         {@code 400 (Bad Request)} if the payload is invalid.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actions/update")
    public ResponseEntity<ThermographicRecord> updateThermographicRecordFromPayload(@RequestBody ThermographicRecord payload)
        throws URISyntaxException {
        LOG.debug("REST request to edit ThermographicRecord from payload");

        if (payload == null || payload.getId() == null) {
            throw new BadRequestAlertException("Payload inválido ou ID não fornecido", ENTITY_NAME, "payloadnull");
        }

        // Get existing record
        ThermographicRecord existingRecord = thermographicRecordRepository
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
                existingThermogram.setExtOpticsTemp(newThermogram.getExtOpticsTemp());
                existingThermogram.setExtOpticsTrans(newThermogram.getExtOpticsTrans());
                existingThermogram.setCameraLens(newThermogram.getCameraLens());
                existingThermogram.setCameraModel(newThermogram.getCameraModel());
                existingThermogram.setImageResolution(newThermogram.getImageResolution());
                existingThermogram.setSelectedRoiId(newThermogram.getSelectedRoiId());
                existingThermogram.setMaxTempRoi(newThermogram.getMaxTempRoi());
                existingThermogram.setLatitude(newThermogram.getLatitude());
                existingThermogram.setLongitude(newThermogram.getLongitude());
                existingThermogram.setPhase(newThermogram.getPhase());
                existingThermogram.setEquipment(newThermogram.getEquipment());
                existingThermogram.setCreatedBy(newThermogram.getCreatedBy());
                existingThermogram.setCreatedAt(newThermogram.getCreatedAt());
                syncRois(existingThermogram, newThermogram.getRois());
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
                existingThermogramRef.setExtOpticsTemp(newThermogramRef.getExtOpticsTemp());
                existingThermogramRef.setExtOpticsTrans(newThermogramRef.getExtOpticsTrans());
                existingThermogramRef.setCameraLens(newThermogramRef.getCameraLens());
                existingThermogramRef.setCameraModel(newThermogramRef.getCameraModel());
                existingThermogramRef.setImageResolution(newThermogramRef.getImageResolution());
                existingThermogramRef.setSelectedRoiId(newThermogramRef.getSelectedRoiId());
                existingThermogramRef.setMaxTempRoi(newThermogramRef.getMaxTempRoi());
                existingThermogramRef.setLatitude(newThermogramRef.getLatitude());
                existingThermogramRef.setLongitude(newThermogramRef.getLongitude());
                existingThermogramRef.setPhase(newThermogramRef.getPhase());
                existingThermogramRef.setEquipment(newThermogramRef.getEquipment());
                existingThermogramRef.setCreatedBy(newThermogramRef.getCreatedBy());
                existingThermogramRef.setCreatedAt(newThermogramRef.getCreatedAt());
                syncRois(existingThermogramRef, newThermogramRef.getRois());
                thermogramRepository.save(existingThermogramRef);
            }
        }

        // Update ThermographicRecord data
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
        existingRecord.setCurrent(payload.getCurrent());
        existingRecord.setVoltage(payload.getVoltage());
        existingRecord.setWindSpeed(payload.getWindSpeed());

        if (payload.getParentRecord() != null && payload.getParentRecord().getId() != null) {
            if (payload.getId().equals(payload.getParentRecord().getId())) {
                throw new BadRequestAlertException("Um registro não pode ser pai de si mesmo", ENTITY_NAME, "parentself");
            }
            existingRecord.setParentRecord(
                thermographicRecordRepository
                    .findById(payload.getParentRecord().getId())
                    .orElseThrow(() -> new BadRequestAlertException("Registro relacionado não encontrado", ENTITY_NAME, "parentnotfound"))
            );
        } else {
            existingRecord.setParentRecord(null);
        }
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

        ThermographicRecord updatedRecord = thermographicRecordRepository.save(existingRecord);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, updatedRecord.getId().toString()))
            .body(updatedRecord);
    }

    // Atualiza a coleção gerenciada in-place; substituir a instância viola
    // orphanRemoval.
    private void syncRois(Thermogram target, List<ROI> incoming) {
        List<ROI> managed = target.getRois();
        if (incoming == null) {
            return;
        }
        Set<UUID> incomingIds = incoming.stream().map(ROI::getId).filter(Objects::nonNull).collect(Collectors.toSet());
        managed.removeIf(roi -> !incomingIds.contains(roi.getId()));

        for (ROI roi : incoming) {
            ROI existing = managed.stream().filter(m -> m.getId() != null && m.getId().equals(roi.getId())).findFirst().orElse(null);
            if (existing != null) {
                existing.setType(roi.getType());
                existing.setLabel(roi.getLabel());
                existing.setMaxTemp(roi.getMaxTemp());
            } else {
                roi.setThermogram(target);
                managed.add(roiRepository.save(roi));
            }
        }
    }
}
