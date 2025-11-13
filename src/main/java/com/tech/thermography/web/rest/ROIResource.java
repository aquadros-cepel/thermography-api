package com.tech.thermography.web.rest;

import com.tech.thermography.domain.ROI;
import com.tech.thermography.repository.ROIRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.ROI}.
 */
@RestController
@RequestMapping("/api/rois")
@Transactional
public class ROIResource {

    private static final Logger LOG = LoggerFactory.getLogger(ROIResource.class);

    private static final String ENTITY_NAME = "rOI";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ROIRepository rOIRepository;

    public ROIResource(ROIRepository rOIRepository) {
        this.rOIRepository = rOIRepository;
    }

    /**
     * {@code POST  /rois} : Create a new rOI.
     *
     * @param rOI the rOI to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rOI, or with status {@code 400 (Bad Request)} if the rOI has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ROI> createROI(@Valid @RequestBody ROI rOI) throws URISyntaxException {
        LOG.debug("REST request to save ROI : {}", rOI);
        if (rOI.getId() != null) {
            throw new BadRequestAlertException("A new rOI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rOI = rOIRepository.save(rOI);
        return ResponseEntity.created(new URI("/api/rois/" + rOI.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rOI.getId().toString()))
            .body(rOI);
    }

    /**
     * {@code PUT  /rois/:id} : Updates an existing rOI.
     *
     * @param id the id of the rOI to save.
     * @param rOI the rOI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rOI,
     * or with status {@code 400 (Bad Request)} if the rOI is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rOI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ROI> updateROI(@PathVariable(value = "id", required = false) final UUID id, @Valid @RequestBody ROI rOI)
        throws URISyntaxException {
        LOG.debug("REST request to update ROI : {}, {}", id, rOI);
        if (rOI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rOI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rOIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rOI = rOIRepository.save(rOI);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rOI.getId().toString()))
            .body(rOI);
    }

    /**
     * {@code PATCH  /rois/:id} : Partial updates given fields of an existing rOI, field will ignore if it is null
     *
     * @param id the id of the rOI to save.
     * @param rOI the rOI to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rOI,
     * or with status {@code 400 (Bad Request)} if the rOI is not valid,
     * or with status {@code 404 (Not Found)} if the rOI is not found,
     * or with status {@code 500 (Internal Server Error)} if the rOI couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ROI> partialUpdateROI(@PathVariable(value = "id", required = false) final UUID id, @NotNull @RequestBody ROI rOI)
        throws URISyntaxException {
        LOG.debug("REST request to partial update ROI partially : {}, {}", id, rOI);
        if (rOI.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rOI.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rOIRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ROI> result = rOIRepository
            .findById(rOI.getId())
            .map(existingROI -> {
                if (rOI.getType() != null) {
                    existingROI.setType(rOI.getType());
                }
                if (rOI.getLabel() != null) {
                    existingROI.setLabel(rOI.getLabel());
                }
                if (rOI.getMaxTemp() != null) {
                    existingROI.setMaxTemp(rOI.getMaxTemp());
                }

                return existingROI;
            })
            .map(rOIRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rOI.getId().toString())
        );
    }

    /**
     * {@code GET  /rois} : get all the rOIS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rOIS in body.
     */
    @GetMapping("")
    public List<ROI> getAllROIS() {
        LOG.debug("REST request to get all ROIS");
        return rOIRepository.findAll();
    }

    /**
     * {@code GET  /rois/:id} : get the "id" rOI.
     *
     * @param id the id of the rOI to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rOI, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ROI> getROI(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get ROI : {}", id);
        Optional<ROI> rOI = rOIRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rOI);
    }

    /**
     * {@code DELETE  /rois/:id} : delete the "id" rOI.
     *
     * @param id the id of the rOI to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteROI(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete ROI : {}", id);
        rOIRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
