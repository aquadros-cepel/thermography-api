package com.tech.thermography.web.rest;

import com.tech.thermography.domain.InspectionRecordGroup;
import com.tech.thermography.domain.InspectionRecordGroupEquipment;
import com.tech.thermography.repository.InspectionRecordGroupEquipmentRepository;
import com.tech.thermography.repository.InspectionRecordGroupRepository;
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
 * {@link com.tech.thermography.domain.InspectionRecordGroup}.
 */
@RestController
@RequestMapping("/api/inspection-record-groups")
@Transactional
public class InspectionRecordGroupResource {

    private static final Logger LOG = LoggerFactory.getLogger(InspectionRecordGroupResource.class);

    private static final String ENTITY_NAME = "inspectionRecordGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionRecordGroupRepository inspectionRecordGroupRepository;
    private final InspectionRecordGroupEquipmentRepository inspectionRecordGroupEquipmentRepository;

    public InspectionRecordGroupResource(
        InspectionRecordGroupRepository inspectionRecordGroupRepository,
        InspectionRecordGroupEquipmentRepository inspectionRecordGroupEquipmentRepository
    ) {
        this.inspectionRecordGroupRepository = inspectionRecordGroupRepository;
        this.inspectionRecordGroupEquipmentRepository = inspectionRecordGroupEquipmentRepository;
    }

    /**
     * {@code POST  /inspection-record-groups} : Create a new inspectionRecordGroup.
     *
     * @param inspectionRecordGroup the inspectionRecordGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new inspectionRecordGroup, or with status
     *         {@code 400 (Bad Request)} if the inspectionRecordGroup has already an
     *         ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InspectionRecordGroup> createInspectionRecordGroup(
        @Valid @RequestBody InspectionRecordGroup inspectionRecordGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to save InspectionRecordGroup : {}", inspectionRecordGroup);
        if (inspectionRecordGroup.getId() != null) {
            throw new BadRequestAlertException("A new inspectionRecordGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inspectionRecordGroup = inspectionRecordGroupRepository.save(inspectionRecordGroup);
        return ResponseEntity.created(new URI("/api/inspection-record-groups/" + inspectionRecordGroup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inspectionRecordGroup.getId().toString()))
            .body(inspectionRecordGroup);
    }

    /**
     * {@code PUT  /inspection-record-groups/:id} : Updates an existing
     * inspectionRecordGroup.
     *
     * @param id                    the id of the inspectionRecordGroup to save.
     * @param inspectionRecordGroup the inspectionRecordGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated inspectionRecordGroup,
     *         or with status {@code 400 (Bad Request)} if the inspectionRecordGroup
     *         is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         inspectionRecordGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InspectionRecordGroup> updateInspectionRecordGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody InspectionRecordGroup inspectionRecordGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to update InspectionRecordGroup : {}, {}", id, inspectionRecordGroup);
        if (inspectionRecordGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRecordGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRecordGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inspectionRecordGroup = inspectionRecordGroupRepository.save(inspectionRecordGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecordGroup.getId().toString()))
            .body(inspectionRecordGroup);
    }

    /**
     * {@code PATCH  /inspection-record-groups/:id} : Partial updates given fields
     * of an existing inspectionRecordGroup, field will ignore if it is null
     *
     * @param id                    the id of the inspectionRecordGroup to save.
     * @param inspectionRecordGroup the inspectionRecordGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated inspectionRecordGroup,
     *         or with status {@code 400 (Bad Request)} if the inspectionRecordGroup
     *         is not valid,
     *         or with status {@code 404 (Not Found)} if the inspectionRecordGroup
     *         is not found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         inspectionRecordGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InspectionRecordGroup> partialUpdateInspectionRecordGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody InspectionRecordGroup inspectionRecordGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InspectionRecordGroup partially : {}, {}", id, inspectionRecordGroup);
        if (inspectionRecordGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRecordGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRecordGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InspectionRecordGroup> result = inspectionRecordGroupRepository
            .findById(inspectionRecordGroup.getId())
            .map(existingInspectionRecordGroup -> {
                if (inspectionRecordGroup.getCode() != null) {
                    existingInspectionRecordGroup.setCode(inspectionRecordGroup.getCode());
                }
                if (inspectionRecordGroup.getName() != null) {
                    existingInspectionRecordGroup.setName(inspectionRecordGroup.getName());
                }
                if (inspectionRecordGroup.getDescription() != null) {
                    existingInspectionRecordGroup.setDescription(inspectionRecordGroup.getDescription());
                }
                if (inspectionRecordGroup.getOrderIndex() != null) {
                    existingInspectionRecordGroup.setOrderIndex(inspectionRecordGroup.getOrderIndex());
                }
                if (inspectionRecordGroup.getFinished() != null) {
                    existingInspectionRecordGroup.setFinished(inspectionRecordGroup.getFinished());
                }
                if (inspectionRecordGroup.getFinishedAt() != null) {
                    existingInspectionRecordGroup.setFinishedAt(inspectionRecordGroup.getFinishedAt());
                }

                return existingInspectionRecordGroup;
            })
            .map(inspectionRecordGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecordGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /inspection-record-groups} : get all the inspectionRecordGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of inspectionRecordGroups in body.
     */
    @GetMapping("")
    public List<InspectionRecordGroup> getAllInspectionRecordGroups() {
        LOG.debug("REST request to get all InspectionRecordGroups");
        return inspectionRecordGroupRepository.findAll();
    }

    /**
     * {@code GET  /inspection-record-groups/:id} : get the "id"
     * inspectionRecordGroup.
     *
     * @param id the id of the inspectionRecordGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the inspectionRecordGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InspectionRecordGroup> getInspectionRecordGroup(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get InspectionRecordGroup : {}", id);
        Optional<InspectionRecordGroup> inspectionRecordGroup = inspectionRecordGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspectionRecordGroup);
    }

    /**
     * {@code DELETE  /inspection-record-groups/:id} : delete the "id"
     * inspectionRecordGroup.
     *
     * @param id the id of the inspectionRecordGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectionRecordGroup(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete InspectionRecordGroup : {}", id);
        inspectionRecordGroupRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PutMapping("/actions/update-record-group/{id}")
    public ResponseEntity<InspectionRecordGroup> updateRecord(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody InspectionRecordGroup inspectionRecordGroup
    ) {
        try {
            InspectionRecordGroup inspectionRecordGroupSaved = saveRecordGroup(inspectionRecordGroup);

            return ResponseEntity.created(new URI("/api/inspection-record-groups/" + inspectionRecordGroupSaved.getId()))
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRecordGroup.getId().toString()))
                .body(inspectionRecordGroup);
        } catch (Exception e) {
            LOG.error("Erro ao editar grupo do registro de inspeção", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public InspectionRecordGroup saveRecordGroup(InspectionRecordGroup inspectionRecordGroup) {
        try {
            inspectionRecordGroup.setFinishedAt(Instant.now());
            InspectionRecordGroup inspectionRecordGroupSaved = inspectionRecordGroupRepository.save(inspectionRecordGroup);

            for (InspectionRecordGroup subGroup : inspectionRecordGroup.getSubGroups()) {
                for (InspectionRecordGroupEquipment groupEquipment : subGroup.getEquipments()) {
                    inspectionRecordGroupEquipmentRepository.save(groupEquipment);
                }
            }
            return inspectionRecordGroupSaved;
        } catch (Exception e) {
            LOG.error("Erro ao criar Registro de Inspeção", e);
            return null;
        }
    }
}
