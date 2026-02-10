package com.tech.thermography.web.rest;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentGroup;
import com.tech.thermography.domain.InspectionRoute;
import com.tech.thermography.domain.InspectionRouteGroup;
import com.tech.thermography.domain.InspectionRouteGroupEquipment;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.User;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.domain.enumeration.Periodicity;
import com.tech.thermography.repository.EquipmentGroupRepository;
import com.tech.thermography.repository.EquipmentRepository;
import com.tech.thermography.repository.InspectionRouteGroupEquipmentRepository;
import com.tech.thermography.repository.InspectionRouteGroupRepository;
import com.tech.thermography.repository.InspectionRouteRepository;
import com.tech.thermography.repository.PlantRepository;
import com.tech.thermography.service.AuthenticatedUserService;
import com.tech.thermography.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
 * {@link com.tech.thermography.domain.InspectionRoute}.
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
    private final InspectionRouteGroupRepository inspectionRouteGroupRepository;
    private final InspectionRouteGroupEquipmentRepository inspectionRouteGroupEquipmentRepository;
    private final EquipmentGroupRepository equipmentGroupRepository;
    private final EquipmentRepository equipmentRepository;
    private final PlantRepository plantRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final InspectionRecordResource inspectionRecordResource;

    public InspectionRouteResource(
        InspectionRouteRepository inspectionRouteRepository,
        InspectionRouteGroupRepository inspectionRouteGroupRepository,
        InspectionRouteGroupEquipmentRepository inspectionRouteGroupEquipmentRepository,
        EquipmentGroupRepository equipmentGroupRepository,
        EquipmentRepository equipmentRepository,
        PlantRepository plantRepository,
        AuthenticatedUserService authenticatedUserService,
        InspectionRecordResource inspectionRecordResource
    ) {
        this.inspectionRouteRepository = inspectionRouteRepository;
        this.inspectionRouteGroupRepository = inspectionRouteGroupRepository;
        this.inspectionRouteGroupEquipmentRepository = inspectionRouteGroupEquipmentRepository;
        this.equipmentGroupRepository = equipmentGroupRepository;
        this.equipmentRepository = equipmentRepository;
        this.plantRepository = plantRepository;
        this.authenticatedUserService = authenticatedUserService;
        this.inspectionRecordResource = inspectionRecordResource;
    }

    /**
     * Exception handler for NotFoundException.
     * Returns a 404 NOT FOUND response when a NotFoundException is thrown.
     *
     * @param ex the NotFoundException that was thrown
     * @return ResponseEntity with 404 status
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        LOG.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * {@code POST  /inspection-routes} : Create a new inspectionRoute.
     *
     * @param inspectionRoute the inspectionRoute to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new inspectionRoute, or with status
     *         {@code 400 (Bad Request)} if the inspectionRoute has already an ID.
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
     * @param id              the id of the inspectionRoute to save.
     * @param inspectionRoute the inspectionRoute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated inspectionRoute,
     *         or with status {@code 400 (Bad Request)} if the inspectionRoute is
     *         not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         inspectionRoute couldn't be updated.
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
     * {@code PATCH  /inspection-routes/:id} : Partial updates given fields of an
     * existing inspectionRoute, field will ignore if it is null
     *
     * @param id              the id of the inspectionRoute to save.
     * @param inspectionRoute the inspectionRoute to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated inspectionRoute,
     *         or with status {@code 400 (Bad Request)} if the inspectionRoute is
     *         not valid,
     *         or with status {@code 404 (Not Found)} if the inspectionRoute is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         inspectionRoute couldn't be updated.
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
                if (inspectionRoute.getCode() != null) {
                    existingInspectionRoute.setCode(inspectionRoute.getCode());
                }
                if (inspectionRoute.getName() != null) {
                    existingInspectionRoute.setName(inspectionRoute.getName());
                }
                if (inspectionRoute.getDescription() != null) {
                    existingInspectionRoute.setDescription(inspectionRoute.getDescription());
                }
                if (inspectionRoute.getMaintenancePlan() != null) {
                    existingInspectionRoute.setMaintenancePlan(inspectionRoute.getMaintenancePlan());
                }
                if (inspectionRoute.getPeriodicity() != null) {
                    existingInspectionRoute.setPeriodicity(inspectionRoute.getPeriodicity());
                }
                if (inspectionRoute.getDuration() != null) {
                    existingInspectionRoute.setDuration(inspectionRoute.getDuration());
                }
                if (inspectionRoute.getExpectedStartDate() != null) {
                    existingInspectionRoute.setExpectedStartDate(inspectionRoute.getExpectedStartDate());
                }
                if (inspectionRoute.getCreatedAt() != null) {
                    existingInspectionRoute.setCreatedAt(inspectionRoute.getCreatedAt());
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of inspectionRoutes in body.
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the inspectionRoute, or with status {@code 404 (Not Found)}.
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

    // **********************************************************************************************
    // ******************************************** */ Testes sem DTO
    // ******************************
    // **********************************************************************************************

    @GetMapping("/actions/{id}")
    public ResponseEntity<InspectionRoute> getInspectionRouteWithFullHierarchy(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get InspectionRoute : {}", id);
        Optional<InspectionRoute> inspectionRoute = inspectionRouteRepository.findByIdWithFullHierarchy(id);
        return ResponseUtil.wrapOrNotFound(inspectionRoute);
    }

    /**
     * {@code POST  /create-route} : Receive an InspectionRoute and return its JSON
     * representation.
     *
     * @param inspectionRoute the inspectionRoute to process.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the JSON representation of the inspectionRoute including all nested
     *         objects.
     */
    @PostMapping("/actions/create-route")
    public ResponseEntity<InspectionRoute> createRoute(@RequestBody InspectionRoute inspectionRoute) {
        InspectionRoute inspectionRouteSaved = saveRoute(inspectionRoute, true);
        try {
            if (inspectionRouteSaved != null && inspectionRouteSaved.getId() != null) {
                inspectionRecordResource.createRecord(inspectionRouteSaved.getId());
            }
            return ResponseEntity.created(new URI("/api/inspection-routes/" + inspectionRouteSaved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inspectionRoute.getId().toString()))
                .body(inspectionRoute);
        } catch (Exception e) {
            LOG.error("Erro ao criar Rota", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actions/update-route/{id}")
    public ResponseEntity<InspectionRoute> updateRoute(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody InspectionRoute inspectionRoute
    ) {
        InspectionRoute inspectionRouteSaved = saveRoute(inspectionRoute, false);

        try {
            return ResponseEntity.created(new URI("/api/inspection-routes/" + inspectionRouteSaved.getId()))
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectionRoute.getId().toString()))
                .body(inspectionRoute);
        } catch (Exception e) {
            LOG.error("Erro ao criar Rota", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/actions/delete-route/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete InspectionRoute : {}", id);
        InspectionRoute inspectionRoute = inspectionRouteRepository
            .findByIdWithFullHierarchy(id)
            .orElseThrow(() -> new NotFoundException("InspectionRoute not found with id: " + id));

        inspectionRoute
            .getGroups()
            .forEach(group -> {
                group
                    .getSubGroups()
                    .forEach(subGroup -> {
                        subGroup
                            .getEquipments()
                            .forEach(equipment -> {
                                inspectionRouteGroupEquipmentRepository.delete(equipment);
                            });
                        inspectionRouteGroupRepository.delete(subGroup);
                    });
                inspectionRouteGroupRepository.delete(group);
            });

        inspectionRouteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    public InspectionRoute saveRoute(InspectionRoute inspectionRoute, boolean isNew) {
        try {
            UserInfo userInfo = authenticatedUserService.requireCurrentUserInfo();

            if (userInfo == null) {
                User user = authenticatedUserService.getCurrentUser();
                LOG.debug("UserInfo not found for authenticated user id: {}", user.getId());
                return null;
            }

            if (isNew) inspectionRoute.setId(null);

            inspectionRoute.setCreatedAt(Instant.now());
            inspectionRoute.setCreatedBy(userInfo);
            LOG.debug("InspectionRoute.name = ", inspectionRoute.getName());

            InspectionRoute inspectionRouteSaved = inspectionRouteRepository.save(inspectionRoute);

            for (InspectionRouteGroup group : inspectionRoute.getGroups()) {
                if (isNew) group.setId(null);
                group.setInspectionRoute(inspectionRouteSaved);

                InspectionRouteGroup groupSaved = inspectionRouteGroupRepository.save(group);

                for (InspectionRouteGroup subGroup : group.getSubGroups()) {
                    if (isNew) subGroup.setId(null);

                    subGroup.setParentGroup(groupSaved);
                    InspectionRouteGroup subGroupSaved = inspectionRouteGroupRepository.save(subGroup);

                    for (InspectionRouteGroupEquipment groupEquipment : subGroup.getEquipments()) {
                        if (isNew) groupEquipment.setId(null);
                        groupEquipment.setInspectionRouteGroup(subGroupSaved);
                        inspectionRouteGroupEquipmentRepository.save(groupEquipment);
                    }
                }
            }
            return inspectionRouteSaved;
        } catch (Exception e) {
            LOG.error("Erro ao criar Rota", e);
            return null;
        }
    }

    /**
     * {@code GET  /inspection-routes/new2/:id} : get a complex InspectionRoute
     * with groups, equipments and plant data assembled.
     *
     * @param id the id of the inspectionRoute to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the constructed InspectionRoute, or with status
     *         {@code 404 (Not Found)}.
     */
    @GetMapping("/new/{plantId}")
    public ResponseEntity<InspectionRoute> getNewInspectionRoute(
        @PathVariable("plantId") UUID plantId,
        @AuthenticationPrincipal User user
    ) {
        LOG.debug("REST request to get new InspectionRoute: {}", plantId);

        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new NotFoundException("Plant not found with id: " + plantId));

        // Create InspectionRoute
        InspectionRoute route = new InspectionRoute();
        route.setId(UUID.randomUUID());

        // Count existing routes for this plant
        Integer numRoutes = inspectionRouteRepository
            .findAll()
            .stream()
            .filter(r -> r.getPlant() != null && r.getPlant().getId().equals(plantId))
            .toList()
            .size();

        // Format route number as XXX (001, 002, etc.)
        String formattedRouteNumber = String.format("%03d", numRoutes + 1);

        route.setName("RI_" + plant.getCode() + "_C" + formattedRouteNumber);
        route.setDuration(15);
        route.setPeriodicity(Periodicity.SEMI_ANNUAL);
        route.setPlant(plant);
        // route.setCreatedAt(Instant.now());

        // Fetch equipment groups for the plant
        List<EquipmentGroup> equipmentGroups = equipmentGroupRepository.findByPlant(plant);

        // Map EquipmentGroups to InspectionRouteGroupDTO
        Set<InspectionRouteGroup> rootGroups = new HashSet<>();
        int[] rootOrderIndex = { 1 };

        for (EquipmentGroup eg : equipmentGroups) {
            if (eg.getParentGroup() == null) {
                rootGroups.add(mapGroup(eg, route, null, rootOrderIndex));
            }
        }

        route.setGroups(rootGroups);
        LOG.debug("REST request to get new InspectionRoute: {}", route);

        return ResponseEntity.ok(route);
    }

    private InspectionRouteGroup mapGroup(EquipmentGroup eg, InspectionRoute route, InspectionRouteGroup parent, int[] orderIndexCounter) {
        // Grupo
        InspectionRouteGroup inspectionRouteGroup = new InspectionRouteGroup();
        inspectionRouteGroup.setId(UUID.randomUUID());
        inspectionRouteGroup.setCode(eg.getCode());
        inspectionRouteGroup.setName(eg.getName());
        inspectionRouteGroup.setDescription(eg.getDescription());
        inspectionRouteGroup.setIncluded(true);
        inspectionRouteGroup.setOrderIndex(orderIndexCounter[0]++);
        inspectionRouteGroup.setInspectionRoute(route);
        inspectionRouteGroup.setParentGroup(parent);

        // Subgroups
        if (eg.getSubGroups() != null && !eg.getSubGroups().isEmpty()) {
            Set<InspectionRouteGroup> subGroups = new HashSet<>();
            int[] subOrderIndex = { 1 };
            for (EquipmentGroup subEg : eg.getSubGroups()) {
                subGroups.add(mapGroup(subEg, null, inspectionRouteGroup, subOrderIndex));
            }
            inspectionRouteGroup.setSubGroups(subGroups);
        }

        // Equipments
        List<Equipment> equipments = equipmentRepository.findByGroup(eg);
        if (equipments != null && !equipments.isEmpty()) {
            Set<InspectionRouteGroupEquipment> equipmentsHashSet = new HashSet<>();
            int equipmentOrderIndex = 1;

            for (Equipment eq : equipments) {
                InspectionRouteGroupEquipment inspectionRouteGroupEquipment = new InspectionRouteGroupEquipment();
                inspectionRouteGroupEquipment.setId(UUID.randomUUID());
                inspectionRouteGroupEquipment.setIncluded(true);
                inspectionRouteGroupEquipment.setOrderIndex(equipmentOrderIndex++);
                inspectionRouteGroupEquipment.setInspectionRouteGroup(inspectionRouteGroup);
                inspectionRouteGroupEquipment.setEquipment(eq);
                equipmentsHashSet.add(inspectionRouteGroupEquipment);
            }
            inspectionRouteGroup.setEquipments(equipmentsHashSet);
        }

        return inspectionRouteGroup;
    }
}
