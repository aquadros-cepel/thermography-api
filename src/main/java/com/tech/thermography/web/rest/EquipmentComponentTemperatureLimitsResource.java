package com.tech.thermography.web.rest;

import com.tech.thermography.domain.EquipmentComponentTemperatureLimits;
import com.tech.thermography.repository.EquipmentComponentTemperatureLimitsRepository;
import com.tech.thermography.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tech.thermography.domain.EquipmentComponentTemperatureLimits}.
 */
@RestController
@RequestMapping("/api/equipment-component-temperature-limits")
@Transactional
public class EquipmentComponentTemperatureLimitsResource {

    private static final Logger LOG = LoggerFactory.getLogger(EquipmentComponentTemperatureLimitsResource.class);

    private static final String ENTITY_NAME = "equipmentComponentTemperatureLimits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipmentComponentTemperatureLimitsRepository equipmentComponentTemperatureLimitsRepository;

    public EquipmentComponentTemperatureLimitsResource(
        EquipmentComponentTemperatureLimitsRepository equipmentComponentTemperatureLimitsRepository
    ) {
        this.equipmentComponentTemperatureLimitsRepository = equipmentComponentTemperatureLimitsRepository;
    }

    /**
     * {@code POST  /equipment-component-temperature-limits} : Create a new equipmentComponentTemperatureLimits.
     *
     * @param equipmentComponentTemperatureLimits the equipmentComponentTemperatureLimits to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipmentComponentTemperatureLimits, or with status {@code 400 (Bad Request)} if the equipmentComponentTemperatureLimits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EquipmentComponentTemperatureLimits> createEquipmentComponentTemperatureLimits(
        @Valid @RequestBody EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits
    ) throws URISyntaxException {
        LOG.debug("REST request to save EquipmentComponentTemperatureLimits : {}", equipmentComponentTemperatureLimits);
        if (equipmentComponentTemperatureLimits.getId() != null) {
            throw new BadRequestAlertException(
                "A new equipmentComponentTemperatureLimits cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        equipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository.save(equipmentComponentTemperatureLimits);
        return ResponseEntity.created(new URI("/api/equipment-component-temperature-limits/" + equipmentComponentTemperatureLimits.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    equipmentComponentTemperatureLimits.getId().toString()
                )
            )
            .body(equipmentComponentTemperatureLimits);
    }

    /**
     * {@code PUT  /equipment-component-temperature-limits/:id} : Updates an existing equipmentComponentTemperatureLimits.
     *
     * @param id the id of the equipmentComponentTemperatureLimits to save.
     * @param equipmentComponentTemperatureLimits the equipmentComponentTemperatureLimits to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentComponentTemperatureLimits,
     * or with status {@code 400 (Bad Request)} if the equipmentComponentTemperatureLimits is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipmentComponentTemperatureLimits couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipmentComponentTemperatureLimits> updateEquipmentComponentTemperatureLimits(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits
    ) throws URISyntaxException {
        LOG.debug("REST request to update EquipmentComponentTemperatureLimits : {}, {}", id, equipmentComponentTemperatureLimits);
        if (equipmentComponentTemperatureLimits.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentComponentTemperatureLimits.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentComponentTemperatureLimitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        equipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository.save(equipmentComponentTemperatureLimits);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    equipmentComponentTemperatureLimits.getId().toString()
                )
            )
            .body(equipmentComponentTemperatureLimits);
    }

    /**
     * {@code PATCH  /equipment-component-temperature-limits/:id} : Partial updates given fields of an existing equipmentComponentTemperatureLimits, field will ignore if it is null
     *
     * @param id the id of the equipmentComponentTemperatureLimits to save.
     * @param equipmentComponentTemperatureLimits the equipmentComponentTemperatureLimits to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipmentComponentTemperatureLimits,
     * or with status {@code 400 (Bad Request)} if the equipmentComponentTemperatureLimits is not valid,
     * or with status {@code 404 (Not Found)} if the equipmentComponentTemperatureLimits is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipmentComponentTemperatureLimits couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EquipmentComponentTemperatureLimits> partialUpdateEquipmentComponentTemperatureLimits(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update EquipmentComponentTemperatureLimits partially : {}, {}",
            id,
            equipmentComponentTemperatureLimits
        );
        if (equipmentComponentTemperatureLimits.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipmentComponentTemperatureLimits.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipmentComponentTemperatureLimitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EquipmentComponentTemperatureLimits> result = equipmentComponentTemperatureLimitsRepository
            .findById(equipmentComponentTemperatureLimits.getId())
            .map(existingEquipmentComponentTemperatureLimits -> {
                if (equipmentComponentTemperatureLimits.getName() != null) {
                    existingEquipmentComponentTemperatureLimits.setName(equipmentComponentTemperatureLimits.getName());
                }
                if (equipmentComponentTemperatureLimits.getNormal() != null) {
                    existingEquipmentComponentTemperatureLimits.setNormal(equipmentComponentTemperatureLimits.getNormal());
                }
                if (equipmentComponentTemperatureLimits.getLowRisk() != null) {
                    existingEquipmentComponentTemperatureLimits.setLowRisk(equipmentComponentTemperatureLimits.getLowRisk());
                }
                if (equipmentComponentTemperatureLimits.getMediumRisk() != null) {
                    existingEquipmentComponentTemperatureLimits.setMediumRisk(equipmentComponentTemperatureLimits.getMediumRisk());
                }
                if (equipmentComponentTemperatureLimits.getHighRisk() != null) {
                    existingEquipmentComponentTemperatureLimits.setHighRisk(equipmentComponentTemperatureLimits.getHighRisk());
                }
                if (equipmentComponentTemperatureLimits.getImminentHighRisk() != null) {
                    existingEquipmentComponentTemperatureLimits.setImminentHighRisk(
                        equipmentComponentTemperatureLimits.getImminentHighRisk()
                    );
                }

                return existingEquipmentComponentTemperatureLimits;
            })
            .map(equipmentComponentTemperatureLimitsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipmentComponentTemperatureLimits.getId().toString())
        );
    }

    /**
     * {@code GET  /equipment-component-temperature-limits} : get all the equipmentComponentTemperatureLimits.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipmentComponentTemperatureLimits in body.
     */
    @GetMapping("")
    public List<EquipmentComponentTemperatureLimits> getAllEquipmentComponentTemperatureLimits(
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("equipmentcomponent-is-null".equals(filter)) {
            LOG.debug("REST request to get all EquipmentComponentTemperatureLimitss where equipmentComponent is null");
            return StreamSupport.stream(equipmentComponentTemperatureLimitsRepository.findAll().spliterator(), false)
                .filter(equipmentComponentTemperatureLimits -> equipmentComponentTemperatureLimits.getEquipmentComponent() == null)
                .toList();
        }
        LOG.debug("REST request to get all EquipmentComponentTemperatureLimits");
        return equipmentComponentTemperatureLimitsRepository.findAll();
    }

    /**
     * {@code GET  /equipment-component-temperature-limits/:id} : get the "id" equipmentComponentTemperatureLimits.
     *
     * @param id the id of the equipmentComponentTemperatureLimits to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipmentComponentTemperatureLimits, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentComponentTemperatureLimits> getEquipmentComponentTemperatureLimits(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get EquipmentComponentTemperatureLimits : {}", id);
        Optional<EquipmentComponentTemperatureLimits> equipmentComponentTemperatureLimits =
            equipmentComponentTemperatureLimitsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipmentComponentTemperatureLimits);
    }

    /**
     * {@code DELETE  /equipment-component-temperature-limits/:id} : delete the "id" equipmentComponentTemperatureLimits.
     *
     * @param id the id of the equipmentComponentTemperatureLimits to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipmentComponentTemperatureLimits(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete EquipmentComponentTemperatureLimits : {}", id);
        equipmentComponentTemperatureLimitsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
