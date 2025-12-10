package com.tech.thermography.web.rest;

import com.tech.thermography.domain.InspectionRecord;
import com.tech.thermography.domain.InspectionRecordGroup;
import com.tech.thermography.domain.InspectionRecordGroupEquipment;
import com.tech.thermography.domain.InspectionRoute;
import com.tech.thermography.domain.InspectionRouteGroup;
import com.tech.thermography.domain.InspectionRouteGroupEquipment;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.domain.enumeration.Periodicity;
import com.tech.thermography.repository.InspectionRecordGroupEquipmentRepository;
import com.tech.thermography.repository.InspectionRecordGroupRepository;
import com.tech.thermography.repository.InspectionRecordRepository;
import com.tech.thermography.repository.InspectionRouteRepository;
import com.tech.thermography.service.AuthenticatedUserService;
import com.tech.thermography.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
 * {@link com.tech.thermography.domain.InspectionRecord}.
 */
@RestController
@RequestMapping("/api/inspection-records")
@Transactional
public class InspectionRecordResource {

    private static final Logger LOG = LoggerFactory.getLogger(InspectionRecordResource.class);

    private static final String ENTITY_NAME = "inspectionRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionRecordRepository inspectionRecordRepository;
    private final InspectionRecordGroupRepository inspectionRecordGroupRepository;
    private final InspectionRecordGroupEquipmentRepository inspectionRecordGroupEquipmentRepository;

    private final InspectionRouteRepository inspectionRouteRepository;

    private final AuthenticatedUserService authenticatedUserService;

    public InspectionRecordResource(
        InspectionRecordRepository inspectionRecordRepository,
        InspectionRecordGroupRepository inspectionRecordGroupRepository,
        InspectionRecordGroupEquipmentRepository inspectionRecordGroupEquipmentRepository,
        InspectionRouteRepository inspectionRouteRepository,
        AuthenticatedUserService authenticatedUserService
    ) {
        this.inspectionRecordRepository = inspectionRecordRepository;
        this.inspectionRecordGroupRepository = inspectionRecordGroupRepository;
        this.inspectionRecordGroupEquipmentRepository = inspectionRecordGroupEquipmentRepository;
        this.inspectionRouteRepository = inspectionRouteRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    /**
     * {@code POST  /inspection-records} : Create a new inspectionRecord.
     *
     * @param inspectionRecord the inspectionRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new inspectionRecord, or with status
     *         {@code 400 (Bad Request)} if the inspectionRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InspectionRecord> createInspectionRecord(@Valid @RequestBody InspectionRecord inspectionRecord)
        throws URISyntaxException {
        LOG.debug("REST request to save InspectionRecord : {}", inspectionRecord);
        if (inspectionRecord.getId() != null) {
            throw new BadRequestAlertException("A new inspectionRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inspectionRecord = inspectionRecordRepository.save(inspectionRecord);
        return ResponseEntity.created(new URI("/api/inspection-records/" + inspectionRecord.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inspectionRecord.getId().toString()))
            .body(inspectionRecord);
    }

    /**
     * {@code PUT  /inspection-records/:id} : Updates an existing inspectionRecord.
     *
     * @param id               the id of the inspectionRecord to save.
     * @param inspectionRecord the inspectionRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated inspectionRecord,
     *         or with status {@code 400 (Bad Request)} if the inspectionRecord is
     *         not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         inspectionRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InspectionRecord> updateInspectionRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody InspectionRecord inspectionRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to update InspectionRecord : {}, {}", id, inspectionRecord);
        if (inspectionRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserInfo userInfo = authenticatedUserService.requireCurrentUserInfo();
        if (userInfo == null) {
            throw new BadRequestAlertException("UserInfo not found", ENTITY_NAME, "idnotfound");
        }

        InspectionRecord existingRecord = inspectionRecordRepository
            .findById(inspectionRecord.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));

        if (existingRecord.getStarted() == null && inspectionRecord.getStarted() != null) inspectionRecord.setStartedBy(userInfo);

        if (existingRecord.getFinished() == null && inspectionRecord.getFinished() != null) inspectionRecord.setFinishedBy(userInfo);

        inspectionRecord = inspectionRecordRepository.save(inspectionRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecord.getId().toString()))
            .body(inspectionRecord);
    }

    /**
     * {@code PATCH  /inspection-records/:id} : Partial updates given fields of an
     * existing inspectionRecord, field will ignore if it is null
     *
     * @param id               the id of the inspectionRecord to save.
     * @param inspectionRecord the inspectionRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated inspectionRecord,
     *         or with status {@code 400 (Bad Request)} if the inspectionRecord is
     *         not valid,
     *         or with status {@code 404 (Not Found)} if the inspectionRecord is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         inspectionRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InspectionRecord> partialUpdateInspectionRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody InspectionRecord inspectionRecord
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InspectionRecord partially : {}, {}", id, inspectionRecord);
        if (inspectionRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRecord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRecordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserInfo userInfo = authenticatedUserService.requireCurrentUserInfo();
        if (userInfo == null) {
            throw new BadRequestAlertException("UserInfo not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InspectionRecord> result = inspectionRecordRepository
            .findById(inspectionRecord.getId())
            .map(existingInspectionRecord -> {
                if (inspectionRecord.getCode() != null) {
                    existingInspectionRecord.setCode(inspectionRecord.getCode());
                }
                if (inspectionRecord.getName() != null) {
                    existingInspectionRecord.setName(inspectionRecord.getName());
                }
                if (inspectionRecord.getDescription() != null) {
                    existingInspectionRecord.setDescription(inspectionRecord.getDescription());
                }
                if (inspectionRecord.getMaintenanceDocument() != null) {
                    existingInspectionRecord.setMaintenanceDocument(inspectionRecord.getMaintenanceDocument());
                }
                if (inspectionRecord.getCreatedAt() != null) {
                    existingInspectionRecord.setCreatedAt(inspectionRecord.getCreatedAt());
                }
                if (inspectionRecord.getExpectedStartDate() != null) {
                    existingInspectionRecord.setExpectedStartDate(inspectionRecord.getExpectedStartDate());
                }
                if (inspectionRecord.getExpectedEndDate() != null) {
                    existingInspectionRecord.setExpectedEndDate(inspectionRecord.getExpectedEndDate());
                }
                if (inspectionRecord.getStarted() != null) {
                    existingInspectionRecord.setStarted(inspectionRecord.getStarted());
                }
                if (inspectionRecord.getStartedAt() != null) {
                    existingInspectionRecord.setStartedAt(inspectionRecord.getStartedAt());
                }
                if (inspectionRecord.getFinished() != null) {
                    existingInspectionRecord.setFinished(inspectionRecord.getFinished());
                }
                if (inspectionRecord.getFinishedAt() != null) {
                    existingInspectionRecord.setFinishedAt(inspectionRecord.getFinishedAt());
                }
                if (existingInspectionRecord.getStarted() == null && inspectionRecord.getStarted() != null) inspectionRecord.setStartedBy(
                    userInfo
                );

                if (
                    existingInspectionRecord.getFinished() == null && inspectionRecord.getFinished() != null
                ) inspectionRecord.setFinishedBy(userInfo);

                return existingInspectionRecord;
            })
            .map(inspectionRecordRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecord.getId().toString())
        );
    }

    /**
     * {@code GET  /inspection-records} : get all the inspectionRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of inspectionRecords in body.
     */
    @GetMapping("")
    public List<InspectionRecord> getAllInspectionRecords() {
        LOG.debug("REST request to get all InspectionRecords");
        return inspectionRecordRepository.findAll();
    }

    /**
     * {@code GET  /inspection-records/:id} : get the "id" inspectionRecord.
     *
     * @param id the id of the inspectionRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the inspectionRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InspectionRecord> getInspectionRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get InspectionRecord : {}", id);
        Optional<InspectionRecord> inspectionRecord = inspectionRecordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspectionRecord);
    }

    /**
     * {@code DELETE  /inspection-records/:id} : delete the "id" inspectionRecord.
     *
     * @param id the id of the inspectionRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectionRecord(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete InspectionRecord : {}", id);
        inspectionRecordRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    // **********************************************************************************************
    // Testes sem DTO
    // **********************************************************************************************

    @GetMapping("/actions/{id}")
    public ResponseEntity<InspectionRecord> getInspectionRecordWithFullHierarchy(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get InspectionRecord : {}", id);
        Optional<InspectionRecord> inspectionRecord = inspectionRecordRepository.findByIdWithFullHierarchy(id);
        return ResponseUtil.wrapOrNotFound(inspectionRecord);
    }

    /**
     * {@code POST  /create-record} : Receive an InspectionRecord and return its
     * JSON
     * representation.
     *
     * @param inspectionRecord the inspectionRecord to process.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the JSON representation of the inspectionRecord including all nested
     *         objects.
     */
    @PostMapping("/actions/create-record/{inspectionRouteId}")
    public ResponseEntity<InspectionRecord> createRecord(@PathVariable("inspectionRouteId") UUID inspectionRouteId) {
        // 1. Pegar a Rota associada ao registro de inspeção
        InspectionRoute inspectionRoute = inspectionRouteRepository
            .findById(inspectionRouteId)
            .orElseThrow(() -> new RuntimeException("Rota de inspeção não encontrada"));

        InspectionRecord inspectionRecord = new InspectionRecord();

        // 2. Calcular o nome: nome da rota + _xxx onde xxx é o count de
        // inspectionRecords associados à essa rota
        long count = inspectionRecordRepository.countByInspectionRouteId(inspectionRouteId);
        inspectionRecord.setName(inspectionRoute.getName() + "_" + String.format("%03d", count + 1));

        inspectionRecord.setDescription(inspectionRoute.getDescription());

        // 3. Calcular ExpectedStartDate baseado na dataInicial da rota e periodicidade
        LocalDate expectedStartDate = calculateExpectedStartDate(inspectionRoute);
        inspectionRecord.setExpectedStartDate(expectedStartDate);

        // 4. ExpectedEndDate = ExpectedStartDate + periodicidade (em dias)
        int periodicityDays = getPeriodicityDays(inspectionRoute.getPeriodicity());
        inspectionRecord.setExpectedEndDate(expectedStartDate.plusDays(periodicityDays));

        inspectionRecord.setPlant(inspectionRoute.getPlant());
        inspectionRecord.setInspectionRoute(inspectionRoute);

        // 5. Copiar groups onde included = true
        Set<InspectionRecordGroup> recordGroups = new HashSet<>();

        for (InspectionRouteGroup routeGroup : inspectionRoute.getGroups()) {
            if (Boolean.TRUE.equals(routeGroup.getIncluded())) {
                InspectionRecordGroup recordGroup = copyRecordGroupToRecordGroup(routeGroup, inspectionRecord);
                recordGroups.add(recordGroup);
            }
        }
        inspectionRecord.setGroups(recordGroups);

        InspectionRecord inspectionRecordSaved = saveRecord(inspectionRecord, true);

        try {
            return ResponseEntity.created(new URI("/api/inspection-records/" + inspectionRecordSaved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inspectionRecordSaved.getId().toString()))
                .body(inspectionRecordSaved);
        } catch (Exception e) {
            LOG.error("Erro ao criar Rota", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actions/update-record/{id}")
    public ResponseEntity<InspectionRecord> updateRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody InspectionRecord inspectionRecord
    ) {
        InspectionRecord inspectionRecordSaved = saveRecord(inspectionRecord, false);

        try {
            return ResponseEntity.created(new URI("/api/inspection-record/" + inspectionRecordSaved.getId()))
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecord.getId().toString()))
                .body(inspectionRecord);
        } catch (Exception e) {
            LOG.error("Erro ao editar registro de inspeção", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public InspectionRecord saveRecord(InspectionRecord inspectionRecord, boolean isNew) {
        try {
            UserInfo userInfo = authenticatedUserService.requireCurrentUserInfo();
            if (userInfo == null) {
                throw new BadRequestAlertException("UserInfo not found", ENTITY_NAME, "idnotfound");
            }

            if (isNew) {
                inspectionRecord.setId(null);
                inspectionRecord.setCreatedAt(Instant.now());
                inspectionRecord.setCreatedBy(userInfo);
            } else {
                inspectionRecord.setFinishedAt(Instant.now());
                inspectionRecord.setFinishedBy(userInfo);
            }

            InspectionRecord inspectionRecordSaved = inspectionRecordRepository.save(inspectionRecord);

            for (InspectionRecordGroup group : inspectionRecord.getGroups()) {
                if (isNew) group.setId(null);
                else group.setFinishedAt(Instant.now());

                group.setInspectionRecord(inspectionRecordSaved);

                InspectionRecordGroup groupSaved = inspectionRecordGroupRepository.save(group);

                for (InspectionRecordGroup subGroup : group.getSubGroups()) {
                    if (isNew) subGroup.setId(null);
                    else group.setFinishedAt(Instant.now());

                    subGroup.setParentGroup(groupSaved);
                    InspectionRecordGroup subGroupSaved = inspectionRecordGroupRepository.save(subGroup);

                    for (InspectionRecordGroupEquipment groupEquipment : subGroup.getEquipments()) {
                        if (isNew) groupEquipment.setId(null);
                        groupEquipment.setInspectionRecordGroup(subGroupSaved);
                        inspectionRecordGroupEquipmentRepository.save(groupEquipment);
                    }
                }
            }
            return inspectionRecordSaved;
        } catch (Exception e) {
            LOG.error("Erro ao criar Registro de Inspeção", e);
            return null;
        }
    }

    /**
     * Calcula a data de início esperada baseada na rota e sua periodicidade
     */
    private LocalDate calculateExpectedStartDate(InspectionRoute inspectionRoute) {
        LocalDate routeStartDate = inspectionRoute.getExpectedStartDate();
        if (routeStartDate == null) {
            return LocalDate.now();
        }

        Periodicity periodicity = inspectionRoute.getPeriodicity();
        if (periodicity == null) {
            return routeStartDate;
        }

        LocalDate now = LocalDate.now();
        LocalDate nextDate = routeStartDate;

        // Encontrar a próxima data baseada na periodicidade
        while (nextDate.isBefore(now) || nextDate.isEqual(now)) {
            switch (periodicity) {
                case MONTHLY:
                    nextDate = nextDate.plusMonths(1);
                    break;
                case QUARTERLY:
                    nextDate = nextDate.plusMonths(3);
                    break;
                case SEMI_ANNUAL:
                    nextDate = nextDate.plusMonths(6);
                    break;
                case ANNUAL:
                    nextDate = nextDate.plusYears(1);
                    break;
                default:
                    return routeStartDate;
            }
        }

        return nextDate;
    }

    /**
     * Retorna o número de dias da periodicidade
     */
    private int getPeriodicityDays(Periodicity periodicity) {
        if (periodicity == null) {
            return 7;
        }

        switch (periodicity) {
            case MONTHLY:
                return 30;
            case QUARTERLY:
                return 90;
            case SEMI_ANNUAL:
                return 180;
            case ANNUAL:
                return 365;
            default:
                return 7;
        }
    }

    /**
     * Copia um InspectionRouteGroup para InspectionRecordGroup incluindo subgroups
     * e equipments
     */
    private InspectionRecordGroup copyRecordGroupToRecordGroup(InspectionRouteGroup routeGroup, InspectionRecord inspectionRecord) {
        InspectionRecordGroup recordGroup = new InspectionRecordGroup();
        recordGroup.setCode(routeGroup.getCode());
        recordGroup.setName(routeGroup.getName());
        recordGroup.setDescription(routeGroup.getDescription());
        recordGroup.setOrderIndex(routeGroup.getOrderIndex());
        recordGroup.setInspectionRecord(inspectionRecord);

        // Copiar subgroups onde included = true
        Set<InspectionRecordGroup> recordSubGroups = new HashSet<>();
        for (InspectionRouteGroup routeSubGroup : routeGroup.getSubGroups()) {
            if (Boolean.TRUE.equals(routeSubGroup.getIncluded())) {
                InspectionRecordGroup recordSubGroup = copyRecordGroupToRecordGroup(routeSubGroup, null);
                recordSubGroup.setParentGroup(recordGroup);
                recordSubGroups.add(recordSubGroup);
            }
        }
        recordGroup.setSubGroups(recordSubGroups);

        // Copiar equipments onde included = true
        Set<InspectionRecordGroupEquipment> recordEquipments = new HashSet<>();
        for (InspectionRouteGroupEquipment routeEquipment : routeGroup.getEquipments()) {
            if (Boolean.TRUE.equals(routeEquipment.getIncluded())) {
                InspectionRecordGroupEquipment recordEquipment = new InspectionRecordGroupEquipment();
                recordEquipment.setEquipment(routeEquipment.getEquipment());
                recordEquipment.setOrderIndex(routeEquipment.getOrderIndex());
                recordEquipment.setInspectionRecordGroup(recordGroup);
                recordEquipments.add(recordEquipment);
            }
        }
        recordGroup.setEquipments(recordEquipments);

        return recordGroup;
    }
}
