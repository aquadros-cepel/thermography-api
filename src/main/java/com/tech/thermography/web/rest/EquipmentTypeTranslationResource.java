package com.tech.thermography.web.rest;

import com.tech.thermography.domain.EquipmentTypeTranslation;
import com.tech.thermography.repository.EquipmentTypeTranslationRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.EquipmentTypeTranslation}.
 */
@RestController
@RequestMapping("/api/equipment-type-translations")
@Transactional
public class EquipmentTypeTranslationResource {

    private static final Logger LOG = LoggerFactory.getLogger(EquipmentTypeTranslationResource.class);

    private static final String ENTITY_NAME = "equipmentTypeTranslation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentTypeTranslationRepository equipmentTypeTranslationRepository;

    public EquipmentTypeTranslationResource(EquipmentTypeTranslationRepository equipmentTypeTranslationRepository) {
        this.equipmentTypeTranslationRepository = equipmentTypeTranslationRepository;
    }

    /**
     * {@code POST  /equipment-type-translations} : Create a new equipmentTypeTranslation.
     *
     * @param equipmentTypeTranslation the equipmentTypeTranslation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipmentTypeTranslation, or with status {@code 400 (Bad Request)} if the equipmentTypeTranslation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EquipmentTypeTranslation> createEquipmentTypeTranslation(
        @Valid @RequestBody EquipmentTypeTranslation equipmentTypeTranslation
    ) throws URISyntaxException {
        LOG.debug("REST request to save EquipmentTypeTranslation : {}", equipmentTypeTranslation);
        if (equipmentTypeTranslation.getId() != null) {
            throw new BadRequestAlertException("A new equipmentTypeTranslation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        equipmentTypeTranslation = equipmentTypeTranslationRepository.save(equipmentTypeTranslation);
        return ResponseEntity.created(new URI("/api/equipment-type-translations/" + equipmentTypeTranslation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, equipmentTypeTranslation.getId().toString()))
            .body(equipmentTypeTranslation);
    }

    /**
     * {@code PUT  /equipment-type-translations/:id} : Updates an existing equipmentTypeTranslation.
     *
     * @param id the id of the equipmentTypeTranslation to save.
     * @param equipmentTypeTranslation the equipmentTypeTranslation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentTypeTranslation,
     * or with status {@code 400 (Bad Request)} if the equipmentTypeTranslation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipmentTypeTranslation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentTypeTranslation> updateEquipmentTypeTranslation(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody EquipmentTypeTranslation equipmentTypeTranslation
    ) throws URISyntaxException {
        LOG.debug("REST request to update EquipmentTypeTranslation : {}, {}", id, equipmentTypeTranslation);
        if (equipmentTypeTranslation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentTypeTranslation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentTypeTranslationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        equipmentTypeTranslation = equipmentTypeTranslationRepository.save(equipmentTypeTranslation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipmentTypeTranslation.getId().toString()))
            .body(equipmentTypeTranslation);
    }

    /**
     * {@code PATCH  /equipment-type-translations/:id} : Partial updates given fields of an existing equipmentTypeTranslation, field will ignore if it is null
     *
     * @param id the id of the equipmentTypeTranslation to save.
     * @param equipmentTypeTranslation the equipmentTypeTranslation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentTypeTranslation,
     * or with status {@code 400 (Bad Request)} if the equipmentTypeTranslation is not valid,
     * or with status {@code 404 (Not Found)} if the equipmentTypeTranslation is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipmentTypeTranslation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EquipmentTypeTranslation> partialUpdateEquipmentTypeTranslation(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody EquipmentTypeTranslation equipmentTypeTranslation
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EquipmentTypeTranslation partially : {}, {}", id, equipmentTypeTranslation);
        if (equipmentTypeTranslation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentTypeTranslation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentTypeTranslationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EquipmentTypeTranslation> result = equipmentTypeTranslationRepository
            .findById(equipmentTypeTranslation.getId())
            .map(existingEquipmentTypeTranslation -> {
                if (equipmentTypeTranslation.getCode() != null) {
                    existingEquipmentTypeTranslation.setCode(equipmentTypeTranslation.getCode());
                }
                if (equipmentTypeTranslation.getLanguage() != null) {
                    existingEquipmentTypeTranslation.setLanguage(equipmentTypeTranslation.getLanguage());
                }
                if (equipmentTypeTranslation.getName() != null) {
                    existingEquipmentTypeTranslation.setName(equipmentTypeTranslation.getName());
                }

                return existingEquipmentTypeTranslation;
            })
            .map(equipmentTypeTranslationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipmentTypeTranslation.getId().toString())
        );
    }

    /**
     * {@code GET  /equipment-type-translations} : get all the equipmentTypeTranslations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipmentTypeTranslations in body.
     */
    @GetMapping("")
    public List<EquipmentTypeTranslation> getAllEquipmentTypeTranslations() {
        LOG.debug("REST request to get all EquipmentTypeTranslations");
        return equipmentTypeTranslationRepository.findAll();
    }

    /**
     * {@code GET  /equipment-type-translations/:id} : get the "id" equipmentTypeTranslation.
     *
     * @param id the id of the equipmentTypeTranslation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipmentTypeTranslation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentTypeTranslation> getEquipmentTypeTranslation(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get EquipmentTypeTranslation : {}", id);
        Optional<EquipmentTypeTranslation> equipmentTypeTranslation = equipmentTypeTranslationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipmentTypeTranslation);
    }

    /**
     * {@code DELETE  /equipment-type-translations/:id} : delete the "id" equipmentTypeTranslation.
     *
     * @param id the id of the equipmentTypeTranslation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipmentTypeTranslation(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete EquipmentTypeTranslation : {}", id);
        equipmentTypeTranslationRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
