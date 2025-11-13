package com.tech.thermography.web.rest;

import com.tech.thermography.domain.RiskPeriodicityDeadline;
import com.tech.thermography.repository.RiskPeriodicityDeadlineRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.RiskPeriodicityDeadline}.
 */
@RestController
@RequestMapping("/api/risk-periodicity-deadlines")
@Transactional
public class RiskPeriodicityDeadlineResource {

    private static final Logger LOG = LoggerFactory.getLogger(RiskPeriodicityDeadlineResource.class);

    private static final String ENTITY_NAME = "riskPeriodicityDeadline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiskPeriodicityDeadlineRepository riskPeriodicityDeadlineRepository;

    public RiskPeriodicityDeadlineResource(RiskPeriodicityDeadlineRepository riskPeriodicityDeadlineRepository) {
        this.riskPeriodicityDeadlineRepository = riskPeriodicityDeadlineRepository;
    }

    /**
     * {@code POST  /risk-periodicity-deadlines} : Create a new riskPeriodicityDeadline.
     *
     * @param riskPeriodicityDeadline the riskPeriodicityDeadline to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riskPeriodicityDeadline, or with status {@code 400 (Bad Request)} if the riskPeriodicityDeadline has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RiskPeriodicityDeadline> createRiskPeriodicityDeadline(
        @Valid @RequestBody RiskPeriodicityDeadline riskPeriodicityDeadline
    ) throws URISyntaxException {
        LOG.debug("REST request to save RiskPeriodicityDeadline : {}", riskPeriodicityDeadline);
        if (riskPeriodicityDeadline.getId() != null) {
            throw new BadRequestAlertException("A new riskPeriodicityDeadline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        riskPeriodicityDeadline = riskPeriodicityDeadlineRepository.save(riskPeriodicityDeadline);
        return ResponseEntity.created(new URI("/api/risk-periodicity-deadlines/" + riskPeriodicityDeadline.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, riskPeriodicityDeadline.getId().toString()))
            .body(riskPeriodicityDeadline);
    }

    /**
     * {@code PUT  /risk-periodicity-deadlines/:id} : Updates an existing riskPeriodicityDeadline.
     *
     * @param id the id of the riskPeriodicityDeadline to save.
     * @param riskPeriodicityDeadline the riskPeriodicityDeadline to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskPeriodicityDeadline,
     * or with status {@code 400 (Bad Request)} if the riskPeriodicityDeadline is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riskPeriodicityDeadline couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RiskPeriodicityDeadline> updateRiskPeriodicityDeadline(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody RiskPeriodicityDeadline riskPeriodicityDeadline
    ) throws URISyntaxException {
        LOG.debug("REST request to update RiskPeriodicityDeadline : {}, {}", id, riskPeriodicityDeadline);
        if (riskPeriodicityDeadline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riskPeriodicityDeadline.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riskPeriodicityDeadlineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        riskPeriodicityDeadline = riskPeriodicityDeadlineRepository.save(riskPeriodicityDeadline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riskPeriodicityDeadline.getId().toString()))
            .body(riskPeriodicityDeadline);
    }

    /**
     * {@code PATCH  /risk-periodicity-deadlines/:id} : Partial updates given fields of an existing riskPeriodicityDeadline, field will ignore if it is null
     *
     * @param id the id of the riskPeriodicityDeadline to save.
     * @param riskPeriodicityDeadline the riskPeriodicityDeadline to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskPeriodicityDeadline,
     * or with status {@code 400 (Bad Request)} if the riskPeriodicityDeadline is not valid,
     * or with status {@code 404 (Not Found)} if the riskPeriodicityDeadline is not found,
     * or with status {@code 500 (Internal Server Error)} if the riskPeriodicityDeadline couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RiskPeriodicityDeadline> partialUpdateRiskPeriodicityDeadline(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody RiskPeriodicityDeadline riskPeriodicityDeadline
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RiskPeriodicityDeadline partially : {}, {}", id, riskPeriodicityDeadline);
        if (riskPeriodicityDeadline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riskPeriodicityDeadline.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riskPeriodicityDeadlineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RiskPeriodicityDeadline> result = riskPeriodicityDeadlineRepository
            .findById(riskPeriodicityDeadline.getId())
            .map(existingRiskPeriodicityDeadline -> {
                if (riskPeriodicityDeadline.getName() != null) {
                    existingRiskPeriodicityDeadline.setName(riskPeriodicityDeadline.getName());
                }
                if (riskPeriodicityDeadline.getDeadline() != null) {
                    existingRiskPeriodicityDeadline.setDeadline(riskPeriodicityDeadline.getDeadline());
                }
                if (riskPeriodicityDeadline.getDeadlineUnit() != null) {
                    existingRiskPeriodicityDeadline.setDeadlineUnit(riskPeriodicityDeadline.getDeadlineUnit());
                }
                if (riskPeriodicityDeadline.getPeriodicity() != null) {
                    existingRiskPeriodicityDeadline.setPeriodicity(riskPeriodicityDeadline.getPeriodicity());
                }
                if (riskPeriodicityDeadline.getPeriodicityUnit() != null) {
                    existingRiskPeriodicityDeadline.setPeriodicityUnit(riskPeriodicityDeadline.getPeriodicityUnit());
                }
                if (riskPeriodicityDeadline.getRecommendations() != null) {
                    existingRiskPeriodicityDeadline.setRecommendations(riskPeriodicityDeadline.getRecommendations());
                }

                return existingRiskPeriodicityDeadline;
            })
            .map(riskPeriodicityDeadlineRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riskPeriodicityDeadline.getId().toString())
        );
    }

    /**
     * {@code GET  /risk-periodicity-deadlines} : get all the riskPeriodicityDeadlines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riskPeriodicityDeadlines in body.
     */
    @GetMapping("")
    public List<RiskPeriodicityDeadline> getAllRiskPeriodicityDeadlines() {
        LOG.debug("REST request to get all RiskPeriodicityDeadlines");
        return riskPeriodicityDeadlineRepository.findAll();
    }

    /**
     * {@code GET  /risk-periodicity-deadlines/:id} : get the "id" riskPeriodicityDeadline.
     *
     * @param id the id of the riskPeriodicityDeadline to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riskPeriodicityDeadline, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RiskPeriodicityDeadline> getRiskPeriodicityDeadline(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get RiskPeriodicityDeadline : {}", id);
        Optional<RiskPeriodicityDeadline> riskPeriodicityDeadline = riskPeriodicityDeadlineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riskPeriodicityDeadline);
    }

    /**
     * {@code DELETE  /risk-periodicity-deadlines/:id} : delete the "id" riskPeriodicityDeadline.
     *
     * @param id the id of the riskPeriodicityDeadline to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRiskPeriodicityDeadline(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete RiskPeriodicityDeadline : {}", id);
        riskPeriodicityDeadlineRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
