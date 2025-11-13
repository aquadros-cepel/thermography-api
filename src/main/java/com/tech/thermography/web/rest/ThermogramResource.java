package com.tech.thermography.web.rest;

import com.tech.thermography.domain.Thermogram;
import com.tech.thermography.repository.ThermogramRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.Thermogram}.
 */
@RestController
@RequestMapping("/api/thermograms")
@Transactional
public class ThermogramResource {

    private static final Logger LOG = LoggerFactory.getLogger(ThermogramResource.class);

    private static final String ENTITY_NAME = "thermogram";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThermogramRepository thermogramRepository;

    public ThermogramResource(ThermogramRepository thermogramRepository) {
        this.thermogramRepository = thermogramRepository;
    }

    /**
     * {@code POST  /thermograms} : Create a new thermogram.
     *
     * @param thermogram the thermogram to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thermogram, or with status {@code 400 (Bad Request)} if the thermogram has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Thermogram> createThermogram(@Valid @RequestBody Thermogram thermogram) throws URISyntaxException {
        LOG.debug("REST request to save Thermogram : {}", thermogram);
        if (thermogram.getId() != null) {
            throw new BadRequestAlertException("A new thermogram cannot already have an ID", ENTITY_NAME, "idexists");
        }
        thermogram = thermogramRepository.save(thermogram);
        return ResponseEntity.created(new URI("/api/thermograms/" + thermogram.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, thermogram.getId().toString()))
            .body(thermogram);
    }

    /**
     * {@code PUT  /thermograms/:id} : Updates an existing thermogram.
     *
     * @param id the id of the thermogram to save.
     * @param thermogram the thermogram to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thermogram,
     * or with status {@code 400 (Bad Request)} if the thermogram is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thermogram couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Thermogram> updateThermogram(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody Thermogram thermogram
    ) throws URISyntaxException {
        LOG.debug("REST request to update Thermogram : {}, {}", id, thermogram);
        if (thermogram.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thermogram.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thermogramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        thermogram = thermogramRepository.save(thermogram);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thermogram.getId().toString()))
            .body(thermogram);
    }

    /**
     * {@code PATCH  /thermograms/:id} : Partial updates given fields of an existing thermogram, field will ignore if it is null
     *
     * @param id the id of the thermogram to save.
     * @param thermogram the thermogram to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thermogram,
     * or with status {@code 400 (Bad Request)} if the thermogram is not valid,
     * or with status {@code 404 (Not Found)} if the thermogram is not found,
     * or with status {@code 500 (Internal Server Error)} if the thermogram couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Thermogram> partialUpdateThermogram(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Thermogram thermogram
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Thermogram partially : {}, {}", id, thermogram);
        if (thermogram.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thermogram.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thermogramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Thermogram> result = thermogramRepository
            .findById(thermogram.getId())
            .map(existingThermogram -> {
                if (thermogram.getImagePath() != null) {
                    existingThermogram.setImagePath(thermogram.getImagePath());
                }
                if (thermogram.getAudioPath() != null) {
                    existingThermogram.setAudioPath(thermogram.getAudioPath());
                }
                if (thermogram.getImageRefPath() != null) {
                    existingThermogram.setImageRefPath(thermogram.getImageRefPath());
                }
                if (thermogram.getMinTemp() != null) {
                    existingThermogram.setMinTemp(thermogram.getMinTemp());
                }
                if (thermogram.getAvgTemp() != null) {
                    existingThermogram.setAvgTemp(thermogram.getAvgTemp());
                }
                if (thermogram.getMaxTemp() != null) {
                    existingThermogram.setMaxTemp(thermogram.getMaxTemp());
                }
                if (thermogram.getEmissivity() != null) {
                    existingThermogram.setEmissivity(thermogram.getEmissivity());
                }
                if (thermogram.getSubjectDistance() != null) {
                    existingThermogram.setSubjectDistance(thermogram.getSubjectDistance());
                }
                if (thermogram.getAtmosphericTemp() != null) {
                    existingThermogram.setAtmosphericTemp(thermogram.getAtmosphericTemp());
                }
                if (thermogram.getReflectedTemp() != null) {
                    existingThermogram.setReflectedTemp(thermogram.getReflectedTemp());
                }
                if (thermogram.getRelativeHumidity() != null) {
                    existingThermogram.setRelativeHumidity(thermogram.getRelativeHumidity());
                }
                if (thermogram.getCameraLens() != null) {
                    existingThermogram.setCameraLens(thermogram.getCameraLens());
                }
                if (thermogram.getCameraModel() != null) {
                    existingThermogram.setCameraModel(thermogram.getCameraModel());
                }
                if (thermogram.getImageResolution() != null) {
                    existingThermogram.setImageResolution(thermogram.getImageResolution());
                }
                if (thermogram.getSelectedRoiId() != null) {
                    existingThermogram.setSelectedRoiId(thermogram.getSelectedRoiId());
                }
                if (thermogram.getMaxTempRoi() != null) {
                    existingThermogram.setMaxTempRoi(thermogram.getMaxTempRoi());
                }
                if (thermogram.getCreatedAt() != null) {
                    existingThermogram.setCreatedAt(thermogram.getCreatedAt());
                }
                if (thermogram.getLatitude() != null) {
                    existingThermogram.setLatitude(thermogram.getLatitude());
                }
                if (thermogram.getLongitude() != null) {
                    existingThermogram.setLongitude(thermogram.getLongitude());
                }

                return existingThermogram;
            })
            .map(thermogramRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thermogram.getId().toString())
        );
    }

    /**
     * {@code GET  /thermograms} : get all the thermograms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thermograms in body.
     */
    @GetMapping("")
    public List<Thermogram> getAllThermograms() {
        LOG.debug("REST request to get all Thermograms");
        return thermogramRepository.findAll();
    }

    /**
     * {@code GET  /thermograms/:id} : get the "id" thermogram.
     *
     * @param id the id of the thermogram to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thermogram, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Thermogram> getThermogram(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get Thermogram : {}", id);
        Optional<Thermogram> thermogram = thermogramRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thermogram);
    }

    /**
     * {@code DELETE  /thermograms/:id} : delete the "id" thermogram.
     *
     * @param id the id of the thermogram to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThermogram(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete Thermogram : {}", id);
        thermogramRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
