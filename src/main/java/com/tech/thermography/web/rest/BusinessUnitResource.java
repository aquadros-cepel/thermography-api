package com.tech.thermography.web.rest;

import com.tech.thermography.domain.BusinessUnit;
import com.tech.thermography.repository.BusinessUnitRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.BusinessUnit}.
 */
@RestController
@RequestMapping("/api/business-units")
@Transactional
public class BusinessUnitResource {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessUnitResource.class);

    private static final String ENTITY_NAME = "businessUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessUnitRepository businessUnitRepository;

    public BusinessUnitResource(BusinessUnitRepository businessUnitRepository) {
        this.businessUnitRepository = businessUnitRepository;
    }

    /**
     * {@code POST  /business-units} : Create a new businessUnit.
     *
     * @param businessUnit the businessUnit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessUnit, or with status {@code 400 (Bad Request)} if the businessUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BusinessUnit> createBusinessUnit(@Valid @RequestBody BusinessUnit businessUnit) throws URISyntaxException {
        LOG.debug("REST request to save BusinessUnit : {}", businessUnit);
        if (businessUnit.getId() != null) {
            throw new BadRequestAlertException("A new businessUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        businessUnit = businessUnitRepository.save(businessUnit);
        return ResponseEntity.created(new URI("/api/business-units/" + businessUnit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, businessUnit.getId().toString()))
            .body(businessUnit);
    }

    /**
     * {@code PUT  /business-units/:id} : Updates an existing businessUnit.
     *
     * @param id the id of the businessUnit to save.
     * @param businessUnit the businessUnit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessUnit,
     * or with status {@code 400 (Bad Request)} if the businessUnit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessUnit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BusinessUnit> updateBusinessUnit(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody BusinessUnit businessUnit
    ) throws URISyntaxException {
        LOG.debug("REST request to update BusinessUnit : {}, {}", id, businessUnit);
        if (businessUnit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessUnit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        businessUnit = businessUnitRepository.save(businessUnit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessUnit.getId().toString()))
            .body(businessUnit);
    }

    /**
     * {@code PATCH  /business-units/:id} : Partial updates given fields of an existing businessUnit, field will ignore if it is null
     *
     * @param id the id of the businessUnit to save.
     * @param businessUnit the businessUnit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessUnit,
     * or with status {@code 400 (Bad Request)} if the businessUnit is not valid,
     * or with status {@code 404 (Not Found)} if the businessUnit is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessUnit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BusinessUnit> partialUpdateBusinessUnit(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody BusinessUnit businessUnit
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BusinessUnit partially : {}, {}", id, businessUnit);
        if (businessUnit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessUnit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusinessUnit> result = businessUnitRepository
            .findById(businessUnit.getId())
            .map(existingBusinessUnit -> {
                if (businessUnit.getCode() != null) {
                    existingBusinessUnit.setCode(businessUnit.getCode());
                }
                if (businessUnit.getName() != null) {
                    existingBusinessUnit.setName(businessUnit.getName());
                }
                if (businessUnit.getDescription() != null) {
                    existingBusinessUnit.setDescription(businessUnit.getDescription());
                }

                return existingBusinessUnit;
            })
            .map(businessUnitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessUnit.getId().toString())
        );
    }

    /**
     * {@code GET  /business-units} : get all the businessUnits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessUnits in body.
     */
    @GetMapping("")
    public List<BusinessUnit> getAllBusinessUnits() {
        LOG.debug("REST request to get all BusinessUnits");
        return businessUnitRepository.findAll();
    }

    /**
     * {@code GET  /business-units/:id} : get the "id" businessUnit.
     *
     * @param id the id of the businessUnit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessUnit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BusinessUnit> getBusinessUnit(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get BusinessUnit : {}", id);
        Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(businessUnit);
    }

    /**
     * {@code DELETE  /business-units/:id} : delete the "id" businessUnit.
     *
     * @param id the id of the businessUnit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessUnit(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete BusinessUnit : {}", id);
        businessUnitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
