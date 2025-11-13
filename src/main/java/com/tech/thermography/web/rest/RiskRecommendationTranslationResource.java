package com.tech.thermography.web.rest;

import com.tech.thermography.domain.RiskRecommendationTranslation;
import com.tech.thermography.repository.RiskRecommendationTranslationRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.RiskRecommendationTranslation}.
 */
@RestController
@RequestMapping("/api/risk-recommendation-translations")
@Transactional
public class RiskRecommendationTranslationResource {

    private static final Logger LOG = LoggerFactory.getLogger(RiskRecommendationTranslationResource.class);

    private static final String ENTITY_NAME = "riskRecommendationTranslation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiskRecommendationTranslationRepository riskRecommendationTranslationRepository;

    public RiskRecommendationTranslationResource(RiskRecommendationTranslationRepository riskRecommendationTranslationRepository) {
        this.riskRecommendationTranslationRepository = riskRecommendationTranslationRepository;
    }

    /**
     * {@code POST  /risk-recommendation-translations} : Create a new riskRecommendationTranslation.
     *
     * @param riskRecommendationTranslation the riskRecommendationTranslation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riskRecommendationTranslation, or with status {@code 400 (Bad Request)} if the riskRecommendationTranslation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RiskRecommendationTranslation> createRiskRecommendationTranslation(
        @Valid @RequestBody RiskRecommendationTranslation riskRecommendationTranslation
    ) throws URISyntaxException {
        LOG.debug("REST request to save RiskRecommendationTranslation : {}", riskRecommendationTranslation);
        if (riskRecommendationTranslation.getId() != null) {
            throw new BadRequestAlertException("A new riskRecommendationTranslation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        riskRecommendationTranslation = riskRecommendationTranslationRepository.save(riskRecommendationTranslation);
        return ResponseEntity.created(new URI("/api/risk-recommendation-translations/" + riskRecommendationTranslation.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, riskRecommendationTranslation.getId().toString())
            )
            .body(riskRecommendationTranslation);
    }

    /**
     * {@code PUT  /risk-recommendation-translations/:id} : Updates an existing riskRecommendationTranslation.
     *
     * @param id the id of the riskRecommendationTranslation to save.
     * @param riskRecommendationTranslation the riskRecommendationTranslation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskRecommendationTranslation,
     * or with status {@code 400 (Bad Request)} if the riskRecommendationTranslation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riskRecommendationTranslation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RiskRecommendationTranslation> updateRiskRecommendationTranslation(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody RiskRecommendationTranslation riskRecommendationTranslation
    ) throws URISyntaxException {
        LOG.debug("REST request to update RiskRecommendationTranslation : {}, {}", id, riskRecommendationTranslation);
        if (riskRecommendationTranslation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riskRecommendationTranslation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riskRecommendationTranslationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        riskRecommendationTranslation = riskRecommendationTranslationRepository.save(riskRecommendationTranslation);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riskRecommendationTranslation.getId().toString())
            )
            .body(riskRecommendationTranslation);
    }

    /**
     * {@code PATCH  /risk-recommendation-translations/:id} : Partial updates given fields of an existing riskRecommendationTranslation, field will ignore if it is null
     *
     * @param id the id of the riskRecommendationTranslation to save.
     * @param riskRecommendationTranslation the riskRecommendationTranslation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskRecommendationTranslation,
     * or with status {@code 400 (Bad Request)} if the riskRecommendationTranslation is not valid,
     * or with status {@code 404 (Not Found)} if the riskRecommendationTranslation is not found,
     * or with status {@code 500 (Internal Server Error)} if the riskRecommendationTranslation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RiskRecommendationTranslation> partialUpdateRiskRecommendationTranslation(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody RiskRecommendationTranslation riskRecommendationTranslation
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RiskRecommendationTranslation partially : {}, {}", id, riskRecommendationTranslation);
        if (riskRecommendationTranslation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riskRecommendationTranslation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riskRecommendationTranslationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RiskRecommendationTranslation> result = riskRecommendationTranslationRepository
            .findById(riskRecommendationTranslation.getId())
            .map(existingRiskRecommendationTranslation -> {
                if (riskRecommendationTranslation.getLanguage() != null) {
                    existingRiskRecommendationTranslation.setLanguage(riskRecommendationTranslation.getLanguage());
                }
                if (riskRecommendationTranslation.getName() != null) {
                    existingRiskRecommendationTranslation.setName(riskRecommendationTranslation.getName());
                }

                return existingRiskRecommendationTranslation;
            })
            .map(riskRecommendationTranslationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riskRecommendationTranslation.getId().toString())
        );
    }

    /**
     * {@code GET  /risk-recommendation-translations} : get all the riskRecommendationTranslations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riskRecommendationTranslations in body.
     */
    @GetMapping("")
    public List<RiskRecommendationTranslation> getAllRiskRecommendationTranslations() {
        LOG.debug("REST request to get all RiskRecommendationTranslations");
        return riskRecommendationTranslationRepository.findAll();
    }

    /**
     * {@code GET  /risk-recommendation-translations/:id} : get the "id" riskRecommendationTranslation.
     *
     * @param id the id of the riskRecommendationTranslation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riskRecommendationTranslation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RiskRecommendationTranslation> getRiskRecommendationTranslation(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get RiskRecommendationTranslation : {}", id);
        Optional<RiskRecommendationTranslation> riskRecommendationTranslation = riskRecommendationTranslationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riskRecommendationTranslation);
    }

    /**
     * {@code DELETE  /risk-recommendation-translations/:id} : delete the "id" riskRecommendationTranslation.
     *
     * @param id the id of the riskRecommendationTranslation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRiskRecommendationTranslation(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete RiskRecommendationTranslation : {}", id);
        riskRecommendationTranslationRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
