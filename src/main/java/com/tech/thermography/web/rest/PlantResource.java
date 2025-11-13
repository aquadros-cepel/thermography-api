package com.tech.thermography.web.rest;

import com.tech.thermography.domain.Plant;
import com.tech.thermography.repository.PlantRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.Plant}.
 */
@RestController
@RequestMapping("/api/plants")
@Transactional
public class PlantResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlantResource.class);

    private static final String ENTITY_NAME = "plant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantRepository plantRepository;

    public PlantResource(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    /**
     * {@code POST  /plants} : Create a new plant.
     *
     * @param plant the plant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plant, or with status {@code 400 (Bad Request)} if the plant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Plant> createPlant(@Valid @RequestBody Plant plant) throws URISyntaxException {
        LOG.debug("REST request to save Plant : {}", plant);
        if (plant.getId() != null) {
            throw new BadRequestAlertException("A new plant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        plant = plantRepository.save(plant);
        return ResponseEntity.created(new URI("/api/plants/" + plant.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, plant.getId().toString()))
            .body(plant);
    }

    /**
     * {@code PUT  /plants/:id} : Updates an existing plant.
     *
     * @param id the id of the plant to save.
     * @param plant the plant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plant,
     * or with status {@code 400 (Bad Request)} if the plant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable(value = "id", required = false) final UUID id, @Valid @RequestBody Plant plant)
        throws URISyntaxException {
        LOG.debug("REST request to update Plant : {}, {}", id, plant);
        if (plant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        plant = plantRepository.save(plant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plant.getId().toString()))
            .body(plant);
    }

    /**
     * {@code PATCH  /plants/:id} : Partial updates given fields of an existing plant, field will ignore if it is null
     *
     * @param id the id of the plant to save.
     * @param plant the plant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plant,
     * or with status {@code 400 (Bad Request)} if the plant is not valid,
     * or with status {@code 404 (Not Found)} if the plant is not found,
     * or with status {@code 500 (Internal Server Error)} if the plant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Plant> partialUpdatePlant(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Plant plant
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Plant partially : {}, {}", id, plant);
        if (plant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Plant> result = plantRepository
            .findById(plant.getId())
            .map(existingPlant -> {
                if (plant.getName() != null) {
                    existingPlant.setName(plant.getName());
                }
                if (plant.getTitle() != null) {
                    existingPlant.setTitle(plant.getTitle());
                }
                if (plant.getDescription() != null) {
                    existingPlant.setDescription(plant.getDescription());
                }
                if (plant.getLatitude() != null) {
                    existingPlant.setLatitude(plant.getLatitude());
                }
                if (plant.getLongitude() != null) {
                    existingPlant.setLongitude(plant.getLongitude());
                }
                if (plant.getStartDate() != null) {
                    existingPlant.setStartDate(plant.getStartDate());
                }

                return existingPlant;
            })
            .map(plantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plant.getId().toString())
        );
    }

    /**
     * {@code GET  /plants} : get all the plants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plants in body.
     */
    @GetMapping("")
    public List<Plant> getAllPlants() {
        LOG.debug("REST request to get all Plants");
        return plantRepository.findAll();
    }

    /**
     * {@code GET  /plants/:id} : get the "id" plant.
     *
     * @param id the id of the plant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get Plant : {}", id);
        Optional<Plant> plant = plantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plant);
    }

    /**
     * {@code DELETE  /plants/:id} : delete the "id" plant.
     *
     * @param id the id of the plant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete Plant : {}", id);
        plantRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
