package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.InspectionRouteAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.InspectionRoute;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.domain.enumeration.Periodicity;
import com.tech.thermography.repository.InspectionRouteRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InspectionRouteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspectionRouteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MAINTENANCE_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_MAINTENANCE_PLAN = "BBBBBBBBBB";

    private static final Periodicity DEFAULT_PERIODICITY = Periodicity.MONTHLY;
    private static final Periodicity UPDATED_PERIODICITY = Periodicity.QUARTERLY;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final LocalDate DEFAULT_EXPECTED_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/inspection-routes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InspectionRouteRepository inspectionRouteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectionRouteMockMvc;

    private InspectionRoute inspectionRoute;

    private InspectionRoute insertedInspectionRoute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRoute createEntity(EntityManager em) {
        InspectionRoute inspectionRoute = new InspectionRoute()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .maintenancePlan(DEFAULT_MAINTENANCE_PLAN)
            .periodicity(DEFAULT_PERIODICITY)
            .duration(DEFAULT_DURATION)
            .expectedStartDate(DEFAULT_EXPECTED_START_DATE)
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        Plant plant;
        if (TestUtil.findAll(em, Plant.class).isEmpty()) {
            plant = PlantResourceIT.createEntity();
            em.persist(plant);
            em.flush();
        } else {
            plant = TestUtil.findAll(em, Plant.class).get(0);
        }
        inspectionRoute.setPlant(plant);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        inspectionRoute.setCreatedBy(userInfo);
        return inspectionRoute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRoute createUpdatedEntity(EntityManager em) {
        InspectionRoute updatedInspectionRoute = new InspectionRoute()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .maintenancePlan(UPDATED_MAINTENANCE_PLAN)
            .periodicity(UPDATED_PERIODICITY)
            .duration(UPDATED_DURATION)
            .expectedStartDate(UPDATED_EXPECTED_START_DATE)
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        Plant plant;
        if (TestUtil.findAll(em, Plant.class).isEmpty()) {
            plant = PlantResourceIT.createUpdatedEntity();
            em.persist(plant);
            em.flush();
        } else {
            plant = TestUtil.findAll(em, Plant.class).get(0);
        }
        updatedInspectionRoute.setPlant(plant);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createUpdatedEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        updatedInspectionRoute.setCreatedBy(userInfo);
        return updatedInspectionRoute;
    }

    @BeforeEach
    void initTest() {
        inspectionRoute = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedInspectionRoute != null) {
            inspectionRouteRepository.delete(insertedInspectionRoute);
            insertedInspectionRoute = null;
        }
    }

    @Test
    @Transactional
    void createInspectionRoute() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InspectionRoute
        var returnedInspectionRoute = om.readValue(
            restInspectionRouteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRoute)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InspectionRoute.class
        );

        // Validate the InspectionRoute in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInspectionRouteUpdatableFieldsEquals(returnedInspectionRoute, getPersistedInspectionRoute(returnedInspectionRoute));

        insertedInspectionRoute = returnedInspectionRoute;
    }

    @Test
    @Transactional
    void createInspectionRouteWithExistingId() throws Exception {
        // Create the InspectionRoute with an existing ID
        insertedInspectionRoute = inspectionRouteRepository.saveAndFlush(inspectionRoute);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionRouteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRoute)))
            .andExpect(status().isBadRequest());

        // Validate the InspectionRoute in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRoute.setName(null);

        // Create the InspectionRoute, which fails.

        restInspectionRouteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRoute)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRoute.setCreatedAt(null);

        // Create the InspectionRoute, which fails.

        restInspectionRouteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRoute)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInspectionRoutes() throws Exception {
        // Initialize the database
        insertedInspectionRoute = inspectionRouteRepository.saveAndFlush(inspectionRoute);

        // Get all the inspectionRouteList
        restInspectionRouteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionRoute.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].maintenancePlan").value(hasItem(DEFAULT_MAINTENANCE_PLAN)))
            .andExpect(jsonPath("$.[*].periodicity").value(hasItem(DEFAULT_PERIODICITY.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].expectedStartDate").value(hasItem(DEFAULT_EXPECTED_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getInspectionRoute() throws Exception {
        // Initialize the database
        insertedInspectionRoute = inspectionRouteRepository.saveAndFlush(inspectionRoute);

        // Get the inspectionRoute
        restInspectionRouteMockMvc
            .perform(get(ENTITY_API_URL_ID, inspectionRoute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionRoute.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.maintenancePlan").value(DEFAULT_MAINTENANCE_PLAN))
            .andExpect(jsonPath("$.periodicity").value(DEFAULT_PERIODICITY.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.expectedStartDate").value(DEFAULT_EXPECTED_START_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInspectionRoute() throws Exception {
        // Get the inspectionRoute
        restInspectionRouteMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInspectionRoute() throws Exception {
        // Initialize the database
        insertedInspectionRoute = inspectionRouteRepository.saveAndFlush(inspectionRoute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRoute
        InspectionRoute updatedInspectionRoute = inspectionRouteRepository.findById(inspectionRoute.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInspectionRoute are not directly saved in db
        em.detach(updatedInspectionRoute);
        updatedInspectionRoute
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .maintenancePlan(UPDATED_MAINTENANCE_PLAN)
            .periodicity(UPDATED_PERIODICITY)
            .duration(UPDATED_DURATION)
            .expectedStartDate(UPDATED_EXPECTED_START_DATE)
            .createdAt(UPDATED_CREATED_AT);

        restInspectionRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspectionRoute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInspectionRoute))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRoute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInspectionRouteToMatchAllProperties(updatedInspectionRoute);
    }

    @Test
    @Transactional
    void putNonExistingInspectionRoute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRoute.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspectionRoute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRoute))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRoute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspectionRoute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRoute.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRoute))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRoute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspectionRoute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRoute.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRoute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRoute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectionRouteWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRoute = inspectionRouteRepository.saveAndFlush(inspectionRoute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRoute using partial update
        InspectionRoute partialUpdatedInspectionRoute = new InspectionRoute();
        partialUpdatedInspectionRoute.setId(inspectionRoute.getId());

        partialUpdatedInspectionRoute.code(UPDATED_CODE).description(UPDATED_DESCRIPTION).periodicity(UPDATED_PERIODICITY);

        restInspectionRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRoute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRoute))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRoute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRouteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInspectionRoute, inspectionRoute),
            getPersistedInspectionRoute(inspectionRoute)
        );
    }

    @Test
    @Transactional
    void fullUpdateInspectionRouteWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRoute = inspectionRouteRepository.saveAndFlush(inspectionRoute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRoute using partial update
        InspectionRoute partialUpdatedInspectionRoute = new InspectionRoute();
        partialUpdatedInspectionRoute.setId(inspectionRoute.getId());

        partialUpdatedInspectionRoute
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .maintenancePlan(UPDATED_MAINTENANCE_PLAN)
            .periodicity(UPDATED_PERIODICITY)
            .duration(UPDATED_DURATION)
            .expectedStartDate(UPDATED_EXPECTED_START_DATE)
            .createdAt(UPDATED_CREATED_AT);

        restInspectionRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRoute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRoute))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRoute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRouteUpdatableFieldsEquals(
            partialUpdatedInspectionRoute,
            getPersistedInspectionRoute(partialUpdatedInspectionRoute)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInspectionRoute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRoute.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspectionRoute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRoute))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRoute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspectionRoute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRoute.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRoute))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRoute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspectionRoute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRoute.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inspectionRoute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRoute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspectionRoute() throws Exception {
        // Initialize the database
        insertedInspectionRoute = inspectionRouteRepository.saveAndFlush(inspectionRoute);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inspectionRoute
        restInspectionRouteMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspectionRoute.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inspectionRouteRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected InspectionRoute getPersistedInspectionRoute(InspectionRoute inspectionRoute) {
        return inspectionRouteRepository.findById(inspectionRoute.getId()).orElseThrow();
    }

    protected void assertPersistedInspectionRouteToMatchAllProperties(InspectionRoute expectedInspectionRoute) {
        assertInspectionRouteAllPropertiesEquals(expectedInspectionRoute, getPersistedInspectionRoute(expectedInspectionRoute));
    }

    protected void assertPersistedInspectionRouteToMatchUpdatableProperties(InspectionRoute expectedInspectionRoute) {
        assertInspectionRouteAllUpdatablePropertiesEquals(expectedInspectionRoute, getPersistedInspectionRoute(expectedInspectionRoute));
    }
}
