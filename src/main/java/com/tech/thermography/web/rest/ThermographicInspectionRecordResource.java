package com.tech.thermography.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
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

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentComponent;
import com.tech.thermography.domain.InspectionRecord;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.ROI;
import com.tech.thermography.domain.Thermogram;
import com.tech.thermography.domain.ThermographicInspectionRecord;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.domain.enumeration.ConditionType;
import com.tech.thermography.domain.enumeration.ThermographicInspectionRecordType;
import com.tech.thermography.repository.EquipmentComponentRepository;
import com.tech.thermography.repository.EquipmentRepository;
import com.tech.thermography.repository.InspectionRecordRepository;
import com.tech.thermography.repository.PlantRepository;
import com.tech.thermography.repository.ROIRepository;
import com.tech.thermography.repository.ThermogramRepository;
import com.tech.thermography.repository.ThermographicInspectionRecordRepository;
import com.tech.thermography.repository.UserInfoRepository;
import com.tech.thermography.web.rest.dto.ThermographicInspectionRecordCreateDTO;
import com.tech.thermography.web.rest.errors.BadRequestAlertException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
            UserInfoRepository userInfoRepository) {
        this.thermographicInspectionRecordRepository = thermographicInspectionRecordRepository;
        this.thermogramRepository = thermogramRepository;
        this.roiRepository = roiRepository;
        this.equipmentRepository = equipmentRepository;
        this.equipmentComponentRepository = equipmentComponentRepository;
        this.inspectionRecordRepository = inspectionRecordRepository;
        this.plantRepository = plantRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @PostMapping("/actions/create")
    public ResponseEntity<ThermographicInspectionRecord> createThermographicInspectionRecordFromPayload(
            @RequestBody ThermographicInspectionRecordCreateDTO payload) throws URISyntaxException {
        LOG.debug("REST request to create ThermographicInspectionRecord from payload");

        if (payload == null) {
            throw new BadRequestAlertException("Payload inválido", ENTITY_NAME, "payloadnull");
        }
        if (payload.equipment == null || payload.equipment.id == null) {
            throw new BadRequestAlertException("Equipment obrigatório", ENTITY_NAME, "equipmentnull");
        }
        if (payload.plant == null || payload.plant.id == null) {
            throw new BadRequestAlertException("Plant obrigatória", ENTITY_NAME, "plantnull");
        }
        if (payload.createdBy == null || payload.createdBy.id == null) {
            throw new BadRequestAlertException("CreatedBy obrigatório", ENTITY_NAME, "createdbynull");
        }
        if (payload.thermogram == null) {
            throw new BadRequestAlertException("Thermogram obrigatório", ENTITY_NAME, "thermogramnull");
        }

        Equipment equipment = equipmentRepository
                .findById(payload.equipment.id)
                .orElseThrow(() -> new BadRequestAlertException("Equipment não encontrado", ENTITY_NAME,
                        "equipmentnotfound"));

        Plant plant = plantRepository
                .findById(payload.plant.id)
                .orElseThrow(() -> new BadRequestAlertException("Plant não encontrada", ENTITY_NAME, "plantnotfound"));

        UserInfo createdBy = userInfoRepository
                .findById(payload.createdBy.id)
                .orElseThrow(() -> new BadRequestAlertException("UserInfo não encontrado", ENTITY_NAME,
                        "createdbynotfound"));

        UserInfo finishedBy = createdBy;
        if (payload.finishedBy != null && payload.finishedBy.id != null) {
            finishedBy = userInfoRepository
                    .findById(payload.finishedBy.id)
                    .orElseThrow(() -> new BadRequestAlertException("UserInfo finishedBy não encontrado", ENTITY_NAME,
                            "finishedbynotfound"));
        }

        InspectionRecord route = null;
        if (payload.route != null && payload.route.id != null) {
            route = inspectionRecordRepository
                    .findById(payload.route.id)
                    .orElseThrow(() -> new BadRequestAlertException("InspectionRecord não encontrado", ENTITY_NAME,
                            "routenotfound"));
        }

        EquipmentComponent component = null;
        if (payload.component != null && payload.component.id != null) {
            component = equipmentComponentRepository
                    .findById(payload.component.id)
                    .orElseThrow(() -> new BadRequestAlertException("Component não encontrado", ENTITY_NAME,
                            "componentnotfound"));
        }

        Thermogram thermogram = buildThermogram(payload.thermogram, equipment, createdBy);
        Thermogram thermogramRef = buildThermogramRef(payload.thermogramRef, thermogram, equipment, createdBy);

        ThermographicInspectionRecord record = new ThermographicInspectionRecord();
        record.setId(null);
        record.setName(payload.name);
        ThermographicInspectionRecordType recordType = parseRecordType(payload.type);
        if (recordType == null) {
            throw new BadRequestAlertException("Type inválido", ENTITY_NAME, "typeinvalid");
        }
        record.setType(recordType);
        record.setServiceOrder(payload.serviceOrder);
        record.setCreatedAt(parseInstant(payload.createdAt));
        record.setAnalysisDescription(payload.analysisDescription);
        ConditionType condition = parseCondition(payload.condition);
        if (condition == null) {
            throw new BadRequestAlertException("Condition inválido", ENTITY_NAME, "conditioninvalid");
        }
        record.setCondition(condition);
        record.setDeltaT(payload.deltaT);
        record.setPeriodicity(payload.periodicity);
        record.setDeadlineExecution(parseLocalDate(payload.deadlineExecution));
        record.setNextMonitoring(parseLocalDate(payload.nextMonitoring));
        record.setRecommendations(payload.recommendations);
        record.setFinished(payload.finished != null ? payload.finished : Boolean.FALSE);
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
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
                        record.getId().toString()))
                .body(record);
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
            @Valid @RequestBody ThermographicInspectionRecord thermographicInspectionRecord) throws URISyntaxException {
        LOG.debug("REST request to save ThermographicInspectionRecord : {}", thermographicInspectionRecord);
        if (thermographicInspectionRecord.getId() != null) {
            throw new BadRequestAlertException("A new thermographicInspectionRecord cannot already have an ID",
                    ENTITY_NAME, "idexists");
        }
        thermographicInspectionRecord = thermographicInspectionRecordRepository.save(thermographicInspectionRecord);
        return ResponseEntity
                .created(new URI("/api/thermographic-inspection-records/" + thermographicInspectionRecord.getId()))
                .headers(
                        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
                                thermographicInspectionRecord.getId().toString()))
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
            @Valid @RequestBody ThermographicInspectionRecord thermographicInspectionRecord) throws URISyntaxException {
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
                        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                                thermographicInspectionRecord.getId().toString()))
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
            @NotNull @RequestBody ThermographicInspectionRecord thermographicInspectionRecord)
            throws URISyntaxException {
        LOG.debug("REST request to partial update ThermographicInspectionRecord partially : {}, {}", id,
                thermographicInspectionRecord);
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
                        existingThermographicInspectionRecord
                                .setServiceOrder(thermographicInspectionRecord.getServiceOrder());
                    }
                    if (thermographicInspectionRecord.getCreatedAt() != null) {
                        existingThermographicInspectionRecord
                                .setCreatedAt(thermographicInspectionRecord.getCreatedAt());
                    }
                    if (thermographicInspectionRecord.getAnalysisDescription() != null) {
                        existingThermographicInspectionRecord
                                .setAnalysisDescription(thermographicInspectionRecord.getAnalysisDescription());
                    }
                    if (thermographicInspectionRecord.getCondition() != null) {
                        existingThermographicInspectionRecord
                                .setCondition(thermographicInspectionRecord.getCondition());
                    }
                    if (thermographicInspectionRecord.getDeltaT() != null) {
                        existingThermographicInspectionRecord.setDeltaT(thermographicInspectionRecord.getDeltaT());
                    }
                    if (thermographicInspectionRecord.getPeriodicity() != null) {
                        existingThermographicInspectionRecord
                                .setPeriodicity(thermographicInspectionRecord.getPeriodicity());
                    }
                    if (thermographicInspectionRecord.getDeadlineExecution() != null) {
                        existingThermographicInspectionRecord
                                .setDeadlineExecution(thermographicInspectionRecord.getDeadlineExecution());
                    }
                    if (thermographicInspectionRecord.getNextMonitoring() != null) {
                        existingThermographicInspectionRecord
                                .setNextMonitoring(thermographicInspectionRecord.getNextMonitoring());
                    }
                    if (thermographicInspectionRecord.getRecommendations() != null) {
                        existingThermographicInspectionRecord
                                .setRecommendations(thermographicInspectionRecord.getRecommendations());
                    }
                    if (thermographicInspectionRecord.getFinished() != null) {
                        existingThermographicInspectionRecord.setFinished(thermographicInspectionRecord.getFinished());
                    }
                    if (thermographicInspectionRecord.getFinishedAt() != null) {
                        existingThermographicInspectionRecord
                                .setFinishedAt(thermographicInspectionRecord.getFinishedAt());
                    }

                    return existingThermographicInspectionRecord;
                })
                .map(thermographicInspectionRecordRepository::save);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                        thermographicInspectionRecord.getId().toString()));
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
        Optional<ThermographicInspectionRecord> thermographicInspectionRecord = thermographicInspectionRecordRepository
                .findById(id);
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

    private Thermogram buildThermogram(
            ThermographicInspectionRecordCreateDTO.ThermogramDTO dto,
            Equipment equipment,
            UserInfo createdBy) {
        if (dto == null) {
            return null;
        }

        Thermogram thermogram = null;
        if (dto.id != null) {
            thermogram = thermogramRepository.findById(dto.id).orElse(null);
        }
        if (thermogram == null) {
            thermogram = new Thermogram();
        }

        if (dto.imagePath != null) {
            thermogram.setImagePath(dto.imagePath);
        }
        if (dto.audioPath != null) {
            thermogram.setAudioPath(dto.audioPath);
        }
        if (dto.imageRefPath != null) {
            thermogram.setImageRefPath(dto.imageRefPath);
        }
        if (dto.minTemp != null) {
            thermogram.setMinTemp(dto.minTemp);
        }
        if (dto.avgTemp != null) {
            thermogram.setAvgTemp(dto.avgTemp);
        }
        if (dto.maxTemp != null) {
            thermogram.setMaxTemp(dto.maxTemp);
        }
        if (dto.emissivity != null) {
            thermogram.setEmissivity(dto.emissivity);
        }
        if (dto.subjectDistance != null) {
            thermogram.setSubjectDistance(dto.subjectDistance);
        }
        if (dto.atmosphericTemp != null) {
            thermogram.setAtmosphericTemp(dto.atmosphericTemp);
        }
        if (dto.reflectedTemp != null) {
            thermogram.setReflectedTemp(dto.reflectedTemp);
        }
        if (dto.relativeHumidity != null) {
            thermogram.setRelativeHumidity(dto.relativeHumidity);
        }
        if (dto.cameraLens != null) {
            thermogram.setCameraLens(dto.cameraLens);
        }
        if (dto.cameraModel != null) {
            thermogram.setCameraModel(dto.cameraModel);
        }
        if (dto.imageResolution != null) {
            thermogram.setImageResolution(dto.imageResolution);
        }
        if (dto.selectedRoiId != null) {
            thermogram.setSelectedRoiId(dto.selectedRoiId);
        }
        if (dto.maxTempRoi != null) {
            thermogram.setMaxTempRoi(dto.maxTempRoi);
        }
        if (dto.createdAt != null) {
            thermogram.setCreatedAt(parseInstant(dto.createdAt));
        }
        if (dto.latitude != null) {
            thermogram.setLatitude(dto.latitude);
        }
        if (dto.longitude != null) {
            thermogram.setLongitude(dto.longitude);
        }

        thermogram.setEquipment(equipment);
        thermogram.setCreatedBy(createdBy);

        Thermogram saved = thermogramRepository.save(thermogram);
        saveRois(dto, saved);
        return saved;
    }

    private Thermogram buildThermogramRef(
            ThermographicInspectionRecordCreateDTO.ThermogramDTO dto,
            Thermogram mainThermogram,
            Equipment equipment,
            UserInfo createdBy) {
        if (dto == null) {
            return null;
        }
        if (mainThermogram != null && dto.id != null && dto.id.equals(mainThermogram.getId())) {
            return mainThermogram;
        }
        return buildThermogram(dto, equipment, createdBy);
    }

    private void saveRois(ThermographicInspectionRecordCreateDTO.ThermogramDTO dto, Thermogram thermogram) {
        if (dto == null || dto.rois == null || dto.rois.isEmpty()) {
            return;
        }
        for (ThermographicInspectionRecordCreateDTO.RoiDTO roiDto : dto.rois) {
            if (roiDto == null) {
                continue;
            }
            ROI roi = null;
            if (roiDto.id != null) {
                roi = roiRepository.findById(roiDto.id).orElse(null);
            }
            if (roi == null) {
                roi = new ROI();
            }
            if (roiDto.type != null) {
                roi.setType(roiDto.type);
            }
            if (roiDto.label != null) {
                roi.setLabel(roiDto.label);
            }
            if (roiDto.maxTemp != null) {
                roi.setMaxTemp(roiDto.maxTemp);
            }
            roi.setThermogram(thermogram);
            roiRepository.save(roi);
        }
    }

    private ThermographicInspectionRecordType parseRecordType(String value) {
        if (value == null) {
            return null;
        }
        try {
            return ThermographicInspectionRecordType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private ConditionType parseCondition(String value) {
        if (value == null) {
            return null;
        }
        try {
            return ConditionType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Instant parseInstant(String value) {
        if (value == null || value.isBlank()) {
            return Instant.now();
        }
        try {
            return Instant.parse(value);
        } catch (Exception e) {
            throw new BadRequestAlertException("createdAt inválido", ENTITY_NAME, "createdatinvalid");
        }
    }

    private LocalDate parseLocalDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            if (value.contains("T")) {
                return Instant.parse(value).atZone(ZoneOffset.UTC).toLocalDate();
            }
            return LocalDate.parse(value);
        } catch (Exception e) {
            throw new BadRequestAlertException("Data inválida", ENTITY_NAME, "dateinvalid");
        }
    }
}
