package com.tech.thermography.web.rest;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentGroup;
import com.tech.thermography.domain.InspectionRoute;
import com.tech.thermography.domain.InspectionRouteGroup;
import com.tech.thermography.domain.InspectionRouteGroupEquipment;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.repository.EquipmentGroupRepository;
import com.tech.thermography.repository.EquipmentRepository;
import com.tech.thermography.repository.InspectionRouteRepository;
import com.tech.thermography.repository.PlantRepository;
import com.tech.thermography.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
    private final EquipmentGroupRepository equipmentGroupRepository;
    private final EquipmentRepository equipmentRepository;
    private final PlantRepository plantRepository;

    public InspectionRouteResource(
        InspectionRouteRepository inspectionRouteRepository,
        EquipmentGroupRepository equipmentGroupRepository,
        EquipmentRepository equipmentRepository,
        PlantRepository plantRepository
    ) {
        this.inspectionRouteRepository = inspectionRouteRepository;
        this.equipmentGroupRepository = equipmentGroupRepository;
        this.equipmentRepository = equipmentRepository;
        this.plantRepository = plantRepository;
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
    public ResponseEntity<InspectionRoute> getNewInspectionRoute(@PathVariable("plantId") UUID plantId) {
        LOG.debug("REST request to get new InspectionRoute: {}", plantId);

        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new NotFoundException("Plant not found with id: " + plantId));

        // Create InspectionRoute
        InspectionRoute route = new InspectionRoute();
        route.setId(UUID.randomUUID());
        // TODO: Inserir o código de criação do Nome
        // route.setName("Inspection Route " + plant.getName());
        route.setCreatedAt(Instant.now());
        route.setPlant(plant);

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
    // /**
    // * {@code GET /inspection-routes/new/:id} : get a complex InspectionRoute DTO
    // * with groups, equipments and plant data assembled.
    // *
    // * @param id the id of the inspectionRoute to retrieve.
    // * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
    // body
    // * the constructed InspectionRouteDTO, or with status
    // * {@code 404 (Not Found)}.
    // */
    // @GetMapping("/new/{plantId}")
    // public ResponseEntity<InspectionRouteDTO>
    // getNewInspectionRoute(@PathVariable("plantId") UUID plantId) {
    // LOG.debug("REST request to get new InspectionRoute DTO : {}", plantId);

    // Optional<Plant> plantOpt = plantRepository.findById(plantId);

    // if (!plantOpt.isPresent()) {
    // return ResponseEntity.notFound().build();
    // }

    // Plant plant = plantOpt.get();

    // // Create InspectionRouteDTO
    // InspectionRouteDTO routeDto = new InspectionRouteDTO();
    // routeDto.setId(UUID.randomUUID());
    // routeDto.setCreatedAt(Instant.now());

    // // Map Plant to PlantDTO
    // PlantDTO plantDto = new PlantDTO();
    // plantDto.setId(plant.getId());
    // plantDto.setCode(plant.getCode());
    // plantDto.setName(plant.getName());
    // plantDto.setDescription(plant.getDescription());
    // plantDto.setLatitude(plant.getLatitude());
    // plantDto.setLongitude(plant.getLongitude());
    // plantDto.setStartDate(plant.getStartDate() != null ?
    // plant.getStartDate().toString() : null);
    // routeDto.setPlant(plantDto);

    // // Fetch equipment groups for the plant
    // List<EquipmentGroup> equipmentGroups =
    // equipmentGroupRepository.findByPlant(plant);

    // // Map EquipmentGroups to InspectionRouteGroupDTO
    // Set<InspectionRouteGroupDTO> rootGroups = new HashSet<>();
    // int[] rootOrderIndex = { 1 };

    // for (EquipmentGroup eg : equipmentGroups) {
    // if (eg.getParentGroup() == null) {
    // rootGroups.add(mapGroupToDto(eg, routeDto, null, rootOrderIndex));
    // }
    // }

    // routeDto.setGroups(rootGroups);

    // return ResponseEntity.ok(routeDto);
    // }

    // private InspectionRouteGroupDTO mapGroupToDto(
    // EquipmentGroup eg,
    // InspectionRouteDTO routeDto,
    // InspectionRouteGroupDTO parentDto,
    // int[] orderIndexCounter) {
    // InspectionRouteGroupDTO dto = new InspectionRouteGroupDTO();
    // dto.setId(eg.getId()); // Using EquipmentGroup ID as base, but maybe should
    // be new UUID? User said
    // // "copia os dados", usually implies new ID for new entity, but here we are
    // // returning DTO structure. Let's use EG ID for now as it maps to the group.
    // // Wait, the user example shows "group-100", "group-101". If we are creating
    // a
    // // NEW route, we might want new IDs for the route groups. But the user said
    // // "copia os dados objeto para um objeto InspectionRouteGroup". If this is
    // for a
    // // NEW route to be saved later, the IDs should probably be null or new UUIDs.
    // // However, the example shows IDs. Let's assume we use the EquipmentGroup ID
    // for
    // // reference or generate new ones. The user example has "group-100", which
    // looks
    // // like it could be the EG ID. Let's generate new UUIDs for the new
    // // InspectionRouteGroupDTOs to avoid confusion with EG IDs, or use EG IDs if
    // // they are meant to be the source. Actually, for a "new" route, these are
    // // transient DTOs. Let's generate new UUIDs.
    // dto.setId(UUID.randomUUID());
    // dto.setCode(eg.getCode());
    // dto.setName(eg.getName());
    // dto.setDescription(eg.getDescription());
    // dto.setIncluded(true);
    // dto.setOrderIndex(orderIndexCounter[0]++);

    // if (routeDto != null) {
    // InspectionRouteDTO routeRef = new InspectionRouteDTO();
    // routeRef.setId(routeDto.getId());
    // dto.setInspectionRoute(routeRef);
    // }

    // if (parentDto != null) {
    // InspectionRouteGroupDTO parentRef = new InspectionRouteGroupDTO();
    // parentRef.setId(parentDto.getId());
    // dto.setParentGroup(parentRef);
    // }

    // // Subgroups
    // if (eg.getSubGroups() != null && !eg.getSubGroups().isEmpty()) {
    // Set<InspectionRouteGroupDTO> subGroupsDto = new HashSet<>();
    // int[] subOrderIndex = { 1 };
    // for (EquipmentGroup subEg : eg.getSubGroups()) {
    // subGroupsDto.add(mapGroupToDto(subEg, null, dto, subOrderIndex));
    // }
    // dto.setSubGroups(subGroupsDto);
    // }

    // // Equipments
    // List<Equipment> equipments = equipmentRepository.findByGroup(eg);
    // if (equipments != null && !equipments.isEmpty()) {
    // Set<InspectionRouteGroupEquipmentDTO> equipmentsDto = new HashSet<>();
    // int equipmentOrderIndex = 1;
    // for (Equipment eq : equipments) {
    // InspectionRouteGroupEquipmentDTO eqDto = new
    // InspectionRouteGroupEquipmentDTO();
    // eqDto.setId(UUID.randomUUID());
    // eqDto.setIncluded(true);
    // eqDto.setOrderIndex(equipmentOrderIndex++);

    // InspectionRouteGroupDTO groupRef = new InspectionRouteGroupDTO();
    // groupRef.setId(dto.getId());
    // eqDto.setInspectionRouteGroup(groupRef);

    // EquipmentDTO equipmentDto = new EquipmentDTO();
    // equipmentDto.setId(eq.getId());
    // equipmentDto.setCode(eq.getCode());
    // equipmentDto.setName(eq.getName());
    // equipmentDto.setDescription(eq.getDescription());
    // equipmentDto.setType(eq.getType());
    // equipmentDto.setManufacturer(eq.getManufacturer());
    // equipmentDto.setModel(eq.getModel());
    // equipmentDto.setSerialNumber(eq.getSerialNumber());
    // equipmentDto.setVoltageClass(eq.getVoltageClass());
    // equipmentDto.setPhaseType(eq.getPhaseType());
    // equipmentDto.setStartDate(eq.getStartDate());
    // equipmentDto.setLatitude(eq.getLatitude());
    // equipmentDto.setLongitude(eq.getLongitude());

    // eqDto.setEquipment(equipmentDto);
    // equipmentsDto.add(eqDto);
    // }
    // dto.setEquipments(equipmentsDto);
    // }

    // return dto;
    // }
}
