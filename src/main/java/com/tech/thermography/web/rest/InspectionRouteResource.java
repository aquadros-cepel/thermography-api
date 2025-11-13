package com.tech.thermography.web.rest;

import com.tech.thermography.domain.InspectionRoute;
import com.tech.thermography.repository.InspectionRouteRepository;
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
 * REST controller for managing {@link com.tech.thermography.domain.InspectionRoute}.
 */
@RestController
@RequestMapping("/api/inspection-routes")
@Transactional
public class InspectionRouteResource {

    private static final Logger LOG = LoggerFactory.getLogger(InspectionRouteResource.class);

    private static final String ENTITY_NAME = "inspectionRoute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionRouteRepository inspectionRouteRepository;

    public InspectionRouteResource(InspectionRouteRepository inspectionRouteRepository) {
        this.inspectionRouteRepository = inspectionRouteRepository;
    }

    /**
     * {@code POST  /inspection-routes} : Create a new inspectionRoute.
     *
     * @param inspectionRoute the inspectionRoute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectionRoute, or with status {@code 400 (Bad Request)} if the inspectionRoute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InspectionRoute> createInspectionRoute(@Valid @RequestBody InspectionRoute inspectionRoute)
        throws URISyntaxException {
        LOG.debug("REST request to save InspectionRoute : {}", inspectionRoute);
        if (inspectionRoute.getId() != null) {
            throw new BadRequestAlertException("A new inspectionRoute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inspectionRoute = inspectionRouteRepository.save(inspectionRoute);
        return ResponseEntity.created(new URI("/api/inspection-routes/" + inspectionRoute.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inspectionRoute.getId().toString()))
            .body(inspectionRoute);
    }

    /**
     * {@code PUT  /inspection-routes/:id} : Updates an existing inspectionRoute.
     *
     * @param id the id of the inspectionRoute to save.
     * @param inspectionRoute the inspectionRoute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRoute,
     * or with status {@code 400 (Bad Request)} if the inspectionRoute is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRoute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InspectionRoute> updateInspectionRoute(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody InspectionRoute inspectionRoute
    ) throws URISyntaxException {
        LOG.debug("REST request to update InspectionRoute : {}, {}", id, inspectionRoute);
        if (inspectionRoute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRoute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRouteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inspectionRoute = inspectionRouteRepository.save(inspectionRoute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRoute.getId().toString()))
            .body(inspectionRoute);
    }

    /**
     * {@code PATCH  /inspection-routes/:id} : Partial updates given fields of an existing inspectionRoute, field will ignore if it is null
     *
     * @param id the id of the inspectionRoute to save.
     * @param inspectionRoute the inspectionRoute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionRoute,
     * or with status {@code 400 (Bad Request)} if the inspectionRoute is not valid,
     * or with status {@code 404 (Not Found)} if the inspectionRoute is not found,
     * or with status {@code 500 (Internal Server Error)} if the inspectionRoute couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InspectionRoute> partialUpdateInspectionRoute(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody InspectionRoute inspectionRoute
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InspectionRoute partially : {}, {}", id, inspectionRoute);
        if (inspectionRoute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectionRoute.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectionRouteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InspectionRoute> result = inspectionRouteRepository
            .findById(inspectionRoute.getId())
            .map(existingInspectionRoute -> {
                if (inspectionRoute.getName() != null) {
                    existingInspectionRoute.setName(inspectionRoute.getName());
                }
                if (inspectionRoute.getTitle() != null) {
                    existingInspectionRoute.setTitle(inspectionRoute.getTitle());
                }
                if (inspectionRoute.getDescription() != null) {
                    existingInspectionRoute.setDescription(inspectionRoute.getDescription());
                }
                if (inspectionRoute.getPlanNote() != null) {
                    existingInspectionRoute.setPlanNote(inspectionRoute.getPlanNote());
                }
                if (inspectionRoute.getCreatedAt() != null) {
                    existingInspectionRoute.setCreatedAt(inspectionRoute.getCreatedAt());
                }
                if (inspectionRoute.getStartDate() != null) {
                    existingInspectionRoute.setStartDate(inspectionRoute.getStartDate());
                }
                if (inspectionRoute.getStarted() != null) {
                    existingInspectionRoute.setStarted(inspectionRoute.getStarted());
                }
                if (inspectionRoute.getStartedAt() != null) {
                    existingInspectionRoute.setStartedAt(inspectionRoute.getStartedAt());
                }
                if (inspectionRoute.getEndDate() != null) {
                    existingInspectionRoute.setEndDate(inspectionRoute.getEndDate());
                }
                if (inspectionRoute.getFinished() != null) {
                    existingInspectionRoute.setFinished(inspectionRoute.getFinished());
                }
                if (inspectionRoute.getFinishedAt() != null) {
                    existingInspectionRoute.setFinishedAt(inspectionRoute.getFinishedAt());
                }

                return existingInspectionRoute;
            })
            .map(inspectionRouteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRoute.getId().toString())
        );
    }

    /**
     * {@code GET  /inspection-routes} : get all the inspectionRoutes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectionRoutes in body.
     */
    @GetMapping("")
    public List<InspectionRoute> getAllInspectionRoutes() {
        LOG.debug("REST request to get all InspectionRoutes");
        return inspectionRouteRepository.findAll();
    }

    /**
     * {@code GET  /inspection-routes/:id} : get the "id" inspectionRoute.
     *
     * @param id the id of the inspectionRoute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectionRoute, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InspectionRoute> getInspectionRoute(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get InspectionRoute : {}", id);
        Optional<InspectionRoute> inspectionRoute = inspectionRouteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspectionRoute);
    }

    /**
     * {@code DELETE  /inspection-routes/:id} : delete the "id" inspectionRoute.
     *
     * @param id the id of the inspectionRoute to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectionRoute(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete InspectionRoute : {}", id);
        inspectionRouteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
