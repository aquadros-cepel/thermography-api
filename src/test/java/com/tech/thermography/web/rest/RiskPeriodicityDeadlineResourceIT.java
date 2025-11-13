package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.RiskPeriodicityDeadlineAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.RiskPeriodicityDeadline;
import com.tech.thermography.domain.enumeration.DatetimeUnit;
import com.tech.thermography.domain.enumeration.DatetimeUnit;
import com.tech.thermography.repository.RiskPeriodicityDeadlineRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link RiskPeriodicityDeadlineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RiskPeriodicityDeadlineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEADLINE = 1;
    private static final Integer UPDATED_DEADLINE = 2;

    private static final DatetimeUnit DEFAULT_DEADLINE_UNIT = DatetimeUnit.HOUR;
    private static final DatetimeUnit UPDATED_DEADLINE_UNIT = DatetimeUnit.DAY;

    private static final Integer DEFAULT_PERIODICITY = 1;
    private static final Integer UPDATED_PERIODICITY = 2;

    private static final DatetimeUnit DEFAULT_PERIODICITY_UNIT = DatetimeUnit.HOUR;
    private static final DatetimeUnit UPDATED_PERIODICITY_UNIT = DatetimeUnit.DAY;

    private static final String DEFAULT_RECOMMENDATIONS = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMENDATIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/risk-periodicity-deadlines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RiskPeriodicityDeadlineRepository riskPeriodicityDeadlineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRiskPeriodicityDeadlineMockMvc;

    private RiskPeriodicityDeadline riskPeriodicityDeadline;

    private RiskPeriodicityDeadline insertedRiskPeriodicityDeadline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskPeriodicityDeadline createEntity() {
        return new RiskPeriodicityDeadline()
            .name(DEFAULT_NAME)
            .deadline(DEFAULT_DEADLINE)
            .deadlineUnit(DEFAULT_DEADLINE_UNIT)
            .periodicity(DEFAULT_PERIODICITY)
            .periodicityUnit(DEFAULT_PERIODICITY_UNIT)
            .recommendations(DEFAULT_RECOMMENDATIONS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskPeriodicityDeadline createUpdatedEntity() {
        return new RiskPeriodicityDeadline()
            .name(UPDATED_NAME)
            .deadline(UPDATED_DEADLINE)
            .deadlineUnit(UPDATED_DEADLINE_UNIT)
            .periodicity(UPDATED_PERIODICITY)
            .periodicityUnit(UPDATED_PERIODICITY_UNIT)
            .recommendations(UPDATED_RECOMMENDATIONS);
    }

    @BeforeEach
    void initTest() {
        riskPeriodicityDeadline = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedRiskPeriodicityDeadline != null) {
            riskPeriodicityDeadlineRepository.delete(insertedRiskPeriodicityDeadline);
            insertedRiskPeriodicityDeadline = null;
        }
    }

    @Test
    @Transactional
    void createRiskPeriodicityDeadline() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RiskPeriodicityDeadline
        var returnedRiskPeriodicityDeadline = om.readValue(
            restRiskPeriodicityDeadlineMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskPeriodicityDeadline))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RiskPeriodicityDeadline.class
        );

        // Validate the RiskPeriodicityDeadline in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRiskPeriodicityDeadlineUpdatableFieldsEquals(
            returnedRiskPeriodicityDeadline,
            getPersistedRiskPeriodicityDeadline(returnedRiskPeriodicityDeadline)
        );

        insertedRiskPeriodicityDeadline = returnedRiskPeriodicityDeadline;
    }

    @Test
    @Transactional
    void createRiskPeriodicityDeadlineWithExistingId() throws Exception {
        // Create the RiskPeriodicityDeadline with an existing ID
        insertedRiskPeriodicityDeadline = riskPeriodicityDeadlineRepository.saveAndFlush(riskPeriodicityDeadline);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskPeriodicityDeadlineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskPeriodicityDeadline)))
            .andExpect(status().isBadRequest());

        // Validate the RiskPeriodicityDeadline in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRiskPeriodicityDeadlines() throws Exception {
        // Initialize the database
        insertedRiskPeriodicityDeadline = riskPeriodicityDeadlineRepository.saveAndFlush(riskPeriodicityDeadline);

        // Get all the riskPeriodicityDeadlineList
        restRiskPeriodicityDeadlineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riskPeriodicityDeadline.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE)))
            .andExpect(jsonPath("$.[*].deadlineUnit").value(hasItem(DEFAULT_DEADLINE_UNIT.toString())))
            .andExpect(jsonPath("$.[*].periodicity").value(hasItem(DEFAULT_PERIODICITY)))
            .andExpect(jsonPath("$.[*].periodicityUnit").value(hasItem(DEFAULT_PERIODICITY_UNIT.toString())))
            .andExpect(jsonPath("$.[*].recommendations").value(hasItem(DEFAULT_RECOMMENDATIONS)));
    }

    @Test
    @Transactional
    void getRiskPeriodicityDeadline() throws Exception {
        // Initialize the database
        insertedRiskPeriodicityDeadline = riskPeriodicityDeadlineRepository.saveAndFlush(riskPeriodicityDeadline);

        // Get the riskPeriodicityDeadline
        restRiskPeriodicityDeadlineMockMvc
            .perform(get(ENTITY_API_URL_ID, riskPeriodicityDeadline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(riskPeriodicityDeadline.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deadline").value(DEFAULT_DEADLINE))
            .andExpect(jsonPath("$.deadlineUnit").value(DEFAULT_DEADLINE_UNIT.toString()))
            .andExpect(jsonPath("$.periodicity").value(DEFAULT_PERIODICITY))
            .andExpect(jsonPath("$.periodicityUnit").value(DEFAULT_PERIODICITY_UNIT.toString()))
            .andExpect(jsonPath("$.recommendations").value(DEFAULT_RECOMMENDATIONS));
    }

    @Test
    @Transactional
    void getNonExistingRiskPeriodicityDeadline() throws Exception {
        // Get the riskPeriodicityDeadline
        restRiskPeriodicityDeadlineMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRiskPeriodicityDeadline() throws Exception {
        // Initialize the database
        insertedRiskPeriodicityDeadline = riskPeriodicityDeadlineRepository.saveAndFlush(riskPeriodicityDeadline);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riskPeriodicityDeadline
        RiskPeriodicityDeadline updatedRiskPeriodicityDeadline = riskPeriodicityDeadlineRepository
            .findById(riskPeriodicityDeadline.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedRiskPeriodicityDeadline are not directly saved in db
        em.detach(updatedRiskPeriodicityDeadline);
        updatedRiskPeriodicityDeadline
            .name(UPDATED_NAME)
            .deadline(UPDATED_DEADLINE)
            .deadlineUnit(UPDATED_DEADLINE_UNIT)
            .periodicity(UPDATED_PERIODICITY)
            .periodicityUnit(UPDATED_PERIODICITY_UNIT)
            .recommendations(UPDATED_RECOMMENDATIONS);

        restRiskPeriodicityDeadlineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRiskPeriodicityDeadline.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRiskPeriodicityDeadline))
            )
            .andExpect(status().isOk());

        // Validate the RiskPeriodicityDeadline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRiskPeriodicityDeadlineToMatchAllProperties(updatedRiskPeriodicityDeadline);
    }

    @Test
    @Transactional
    void putNonExistingRiskPeriodicityDeadline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskPeriodicityDeadline.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskPeriodicityDeadlineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, riskPeriodicityDeadline.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(riskPeriodicityDeadline))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskPeriodicityDeadline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRiskPeriodicityDeadline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskPeriodicityDeadline.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskPeriodicityDeadlineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(riskPeriodicityDeadline))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskPeriodicityDeadline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRiskPeriodicityDeadline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskPeriodicityDeadline.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskPeriodicityDeadlineMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskPeriodicityDeadline)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RiskPeriodicityDeadline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRiskPeriodicityDeadlineWithPatch() throws Exception {
        // Initialize the database
        insertedRiskPeriodicityDeadline = riskPeriodicityDeadlineRepository.saveAndFlush(riskPeriodicityDeadline);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riskPeriodicityDeadline using partial update
        RiskPeriodicityDeadline partialUpdatedRiskPeriodicityDeadline = new RiskPeriodicityDeadline();
        partialUpdatedRiskPeriodicityDeadline.setId(riskPeriodicityDeadline.getId());

        partialUpdatedRiskPeriodicityDeadline
            .deadline(UPDATED_DEADLINE)
            .deadlineUnit(UPDATED_DEADLINE_UNIT)
            .periodicity(UPDATED_PERIODICITY);

        restRiskPeriodicityDeadlineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiskPeriodicityDeadline.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRiskPeriodicityDeadline))
            )
            .andExpect(status().isOk());

        // Validate the RiskPeriodicityDeadline in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRiskPeriodicityDeadlineUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRiskPeriodicityDeadline, riskPeriodicityDeadline),
            getPersistedRiskPeriodicityDeadline(riskPeriodicityDeadline)
        );
    }

    @Test
    @Transactional
    void fullUpdateRiskPeriodicityDeadlineWithPatch() throws Exception {
        // Initialize the database
        insertedRiskPeriodicityDeadline = riskPeriodicityDeadlineRepository.saveAndFlush(riskPeriodicityDeadline);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riskPeriodicityDeadline using partial update
        RiskPeriodicityDeadline partialUpdatedRiskPeriodicityDeadline = new RiskPeriodicityDeadline();
        partialUpdatedRiskPeriodicityDeadline.setId(riskPeriodicityDeadline.getId());

        partialUpdatedRiskPeriodicityDeadline
            .name(UPDATED_NAME)
            .deadline(UPDATED_DEADLINE)
            .deadlineUnit(UPDATED_DEADLINE_UNIT)
            .periodicity(UPDATED_PERIODICITY)
            .periodicityUnit(UPDATED_PERIODICITY_UNIT)
            .recommendations(UPDATED_RECOMMENDATIONS);

        restRiskPeriodicityDeadlineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiskPeriodicityDeadline.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRiskPeriodicityDeadline))
            )
            .andExpect(status().isOk());

        // Validate the RiskPeriodicityDeadline in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRiskPeriodicityDeadlineUpdatableFieldsEquals(
            partialUpdatedRiskPeriodicityDeadline,
            getPersistedRiskPeriodicityDeadline(partialUpdatedRiskPeriodicityDeadline)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRiskPeriodicityDeadline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskPeriodicityDeadline.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskPeriodicityDeadlineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, riskPeriodicityDeadline.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(riskPeriodicityDeadline))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskPeriodicityDeadline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRiskPeriodicityDeadline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskPeriodicityDeadline.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskPeriodicityDeadlineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(riskPeriodicityDeadline))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskPeriodicityDeadline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRiskPeriodicityDeadline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskPeriodicityDeadline.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskPeriodicityDeadlineMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(riskPeriodicityDeadline))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RiskPeriodicityDeadline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRiskPeriodicityDeadline() throws Exception {
        // Initialize the database
        insertedRiskPeriodicityDeadline = riskPeriodicityDeadlineRepository.saveAndFlush(riskPeriodicityDeadline);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the riskPeriodicityDeadline
        restRiskPeriodicityDeadlineMockMvc
            .perform(delete(ENTITY_API_URL_ID, riskPeriodicityDeadline.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return riskPeriodicityDeadlineRepository.count();
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

    protected RiskPeriodicityDeadline getPersistedRiskPeriodicityDeadline(RiskPeriodicityDeadline riskPeriodicityDeadline) {
        return riskPeriodicityDeadlineRepository.findById(riskPeriodicityDeadline.getId()).orElseThrow();
    }

    protected void assertPersistedRiskPeriodicityDeadlineToMatchAllProperties(RiskPeriodicityDeadline expectedRiskPeriodicityDeadline) {
        assertRiskPeriodicityDeadlineAllPropertiesEquals(
            expectedRiskPeriodicityDeadline,
            getPersistedRiskPeriodicityDeadline(expectedRiskPeriodicityDeadline)
        );
    }

    protected void assertPersistedRiskPeriodicityDeadlineToMatchUpdatableProperties(
        RiskPeriodicityDeadline expectedRiskPeriodicityDeadline
    ) {
        assertRiskPeriodicityDeadlineAllUpdatablePropertiesEquals(
            expectedRiskPeriodicityDeadline,
            getPersistedRiskPeriodicityDeadline(expectedRiskPeriodicityDeadline)
        );
    }
}
