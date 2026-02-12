package com.tech.thermography.web.rest;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentComponent;
import com.tech.thermography.domain.InspectionRecord;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.ROI;
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

    public ThermographicInspectionRecordResource(
        ThermographicInspectionRecordRepository thermographicInspectionRecordRepository,
        ThermogramRepository thermogramRepository,
        ROIRepository roiRepository,
        EquipmentRepository equipmentRepository,
        EquipmentComponentRepository equipmentComponentRepository,
        InspectionRecordRepository inspectionRecordRepository,
        PlantRepository plantRepository,
        UserInfoRepository userInfoRepository
    ) {
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
        return thermographicInspectionRecordRepository.findAll();
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

        Thermogram thermogram = processThermogram(payload.getThermogram(), equipment, createdBy);
        Thermogram thermogramRef = processThermogramRef(payload.getThermogramRef(), thermogram, equipment, createdBy);

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

        // Popular ROIs nos thermograms antes de retornar
        populateThermogramRois(record.getThermogram());
        populateThermogramRois(record.getThermogramRef());

        return ResponseEntity.created(new URI("/api/thermographic-inspection-records/" + record.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, record.getId().toString()))
            .body(record);
    }

    private Thermogram processThermogram(Thermogram thermogramPayload, Equipment equipment, UserInfo createdBy) {
        if (thermogramPayload == null) {
            return null;
        }

        // Guardar os ROIs antes de processar o thermogram
        List<ROI> roisFromPayload = thermogramPayload.getRois();

        LOG.debug("Processing Thermogram with {} ROIs", roisFromPayload != null ? roisFromPayload.size() : 0);

        Thermogram thermogram = null;
        if (thermogramPayload.getId() != null) {
            thermogram = thermogramRepository.findById(thermogramPayload.getId()).orElse(null);
            LOG.debug("Found existing Thermogram with ID: {}", thermogramPayload.getId());
        }
        if (thermogram == null) {
            thermogram = new Thermogram();
            LOG.debug("Creating new Thermogram");
        }

        if (thermogramPayload.getImagePath() != null) {
            thermogram.setImagePath(thermogramPayload.getImagePath());
        }
        if (thermogramPayload.getAudioPath() != null) {
            thermogram.setAudioPath(thermogramPayload.getAudioPath());
        }
        if (thermogramPayload.getImageRefPath() != null) {
            thermogram.setImageRefPath(thermogramPayload.getImageRefPath());
        }
        if (thermogramPayload.getMinTemp() != null) {
            thermogram.setMinTemp(thermogramPayload.getMinTemp());
        }
        if (thermogramPayload.getAvgTemp() != null) {
            thermogram.setAvgTemp(thermogramPayload.getAvgTemp());
        }
        if (thermogramPayload.getMaxTemp() != null) {
            thermogram.setMaxTemp(thermogramPayload.getMaxTemp());
        }
        if (thermogramPayload.getEmissivity() != null) {
            thermogram.setEmissivity(thermogramPayload.getEmissivity());
        }
        if (thermogramPayload.getSubjectDistance() != null) {
            thermogram.setSubjectDistance(thermogramPayload.getSubjectDistance());
        }
        if (thermogramPayload.getAtmosphericTemp() != null) {
            thermogram.setAtmosphericTemp(thermogramPayload.getAtmosphericTemp());
        }
        if (thermogramPayload.getReflectedTemp() != null) {
            thermogram.setReflectedTemp(thermogramPayload.getReflectedTemp());
        }
        if (thermogramPayload.getRelativeHumidity() != null) {
            thermogram.setRelativeHumidity(thermogramPayload.getRelativeHumidity());
        }
        if (thermogramPayload.getCameraLens() != null) {
            thermogram.setCameraLens(thermogramPayload.getCameraLens());
        }
        if (thermogramPayload.getCameraModel() != null) {
            thermogram.setCameraModel(thermogramPayload.getCameraModel());
        }
        if (thermogramPayload.getImageResolution() != null) {
            thermogram.setImageResolution(thermogramPayload.getImageResolution());
        }
        if (thermogramPayload.getSelectedRoiId() != null) {
            thermogram.setSelectedRoiId(thermogramPayload.getSelectedRoiId());
        }
        if (thermogramPayload.getMaxTempRoi() != null) {
            thermogram.setMaxTempRoi(thermogramPayload.getMaxTempRoi());
        }
        if (thermogramPayload.getCreatedAt() != null) {
            thermogram.setCreatedAt(thermogramPayload.getCreatedAt());
        }
        if (thermogramPayload.getLatitude() != null) {
            thermogram.setLatitude(thermogramPayload.getLatitude());
        }
        if (thermogramPayload.getLongitude() != null) {
            thermogram.setLongitude(thermogramPayload.getLongitude());
        }

        thermogram.setEquipment(equipment);
        thermogram.setCreatedBy(createdBy);

        // CRITICAL: Salvar o Thermogram ANTES de processar os ROIs
        Thermogram saved = thermogramRepository.save(thermogram);
        thermogramRepository.flush(); // Garantir que o ID foi gerado e commitado

        LOG.debug("Thermogram saved with ID: {}", saved.getId());

        // Processar ROIs do payload SOMENTE APÓS o Thermogram ter sido salvo com
        // sucesso
        if (roisFromPayload != null && !roisFromPayload.isEmpty()) {
            LOG.debug("Processing {} ROIs for Thermogram {}", roisFromPayload.size(), saved.getId());
            processRois(roisFromPayload, saved);
        } else {
            LOG.debug("No ROIs to process for Thermogram {}", saved.getId());
        }

        return saved;
    }

    private Thermogram processThermogramRef(
        Thermogram thermogramRefPayload,
        Thermogram mainThermogram,
        Equipment equipment,
        UserInfo createdBy
    ) {
        if (thermogramRefPayload == null) {
            return null;
        }
        if (mainThermogram != null && thermogramRefPayload.getId() != null && thermogramRefPayload.getId().equals(mainThermogram.getId())) {
            return mainThermogram;
        }
        return processThermogram(thermogramRefPayload, equipment, createdBy);
    }

    private void processRois(List<ROI> roisPayload, Thermogram savedThermogram) {
        if (roisPayload == null || roisPayload.isEmpty()) {
            LOG.debug("No ROIs in payload");
            return;
        }

        if (savedThermogram == null || savedThermogram.getId() == null) {
            LOG.error("Cannot save ROIs: Thermogram not saved or ID is null");
            throw new BadRequestAlertException("Thermogram deve ser salvo antes dos ROIs", ENTITY_NAME, "thermogramnotsaved");
        }

        LOG.debug("Saving {} ROIs for Thermogram ID: {}", roisPayload.size(), savedThermogram.getId());

        for (ROI roiPayload : roisPayload) {
            if (roiPayload == null) {
                LOG.warn("Skipping null ROI");
                continue;
            }

            // Validar campos obrigatórios
            if (roiPayload.getType() == null || roiPayload.getType().trim().isEmpty()) {
                LOG.warn("Skipping ROI with null/empty type");
                continue;
            }
            if (roiPayload.getLabel() == null || roiPayload.getLabel().trim().isEmpty()) {
                LOG.warn("Skipping ROI with null/empty label");
                continue;
            }
            if (roiPayload.getMaxTemp() == null) {
                LOG.warn("Skipping ROI {} with null maxTemp", roiPayload.getLabel());
                continue;
            }

            ROI roi = new ROI();
            roi.setType(roiPayload.getType());
            roi.setLabel(roiPayload.getLabel());
            roi.setMaxTemp(roiPayload.getMaxTemp());
            roi.setThermogram(savedThermogram);

            try {
                ROI savedRoi = roiRepository.save(roi);
                LOG.debug(
                    "✓ Saved ROI: {} - {} with maxTemp: {} for Thermogram: {}",
                    savedRoi.getId(),
                    savedRoi.getLabel(),
                    savedRoi.getMaxTemp(),
                    savedThermogram.getId()
                );
            } catch (Exception e) {
                LOG.error("✗ Failed to save ROI {} for Thermogram {}: {}", roiPayload.getLabel(), savedThermogram.getId(), e.getMessage());
                throw new BadRequestAlertException("Erro ao salvar ROI: " + e.getMessage(), ENTITY_NAME, "roisaveerror");
            }
        }

        roiRepository.flush(); // Garantir commit
        LOG.debug("All ROIs saved and flushed for Thermogram {}", savedThermogram.getId());
    }

    private void populateThermogramRois(Thermogram thermogram) {
        if (thermogram != null && thermogram.getId() != null) {
            List<ROI> rois = roiRepository.findByThermogramId(thermogram.getId());
            thermogram.setRois(rois);
        }
    }
}
