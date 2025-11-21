package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.RiskRecommendationTranslationAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.RiskRecommendationTranslation;
import com.tech.thermography.repository.RiskRecommendationTranslationRepository;
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
 * Integration tests for the {@link RiskRecommendationTranslationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RiskRecommendationTranslationResourceIT {

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/risk-recommendation-translations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RiskRecommendationTranslationRepository riskRecommendationTranslationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRiskRecommendationTranslationMockMvc;

    private RiskRecommendationTranslation riskRecommendationTranslation;

    private RiskRecommendationTranslation insertedRiskRecommendationTranslation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskRecommendationTranslation createEntity() {
        return new RiskRecommendationTranslation().language(DEFAULT_LANGUAGE).name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskRecommendationTranslation createUpdatedEntity() {
        return new RiskRecommendationTranslation().language(UPDATED_LANGUAGE).name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        riskRecommendationTranslation = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedRiskRecommendationTranslation != null) {
            riskRecommendationTranslationRepository.delete(insertedRiskRecommendationTranslation);
            insertedRiskRecommendationTranslation = null;
        }
    }

    @Test
    @Transactional
    void createRiskRecommendationTranslation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RiskRecommendationTranslation
        var returnedRiskRecommendationTranslation = om.readValue(
            restRiskRecommendationTranslationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(riskRecommendationTranslation))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RiskRecommendationTranslation.class
        );

        // Validate the RiskRecommendationTranslation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRiskRecommendationTranslationUpdatableFieldsEquals(
            returnedRiskRecommendationTranslation,
            getPersistedRiskRecommendationTranslation(returnedRiskRecommendationTranslation)
        );

        insertedRiskRecommendationTranslation = returnedRiskRecommendationTranslation;
    }

    @Test
    @Transactional
    void createRiskRecommendationTranslationWithExistingId() throws Exception {
        // Create the RiskRecommendationTranslation with an existing ID
        insertedRiskRecommendationTranslation = riskRecommendationTranslationRepository.saveAndFlush(riskRecommendationTranslation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskRecommendationTranslationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskRecommendationTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskRecommendationTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        riskRecommendationTranslation.setLanguage(null);

        // Create the RiskRecommendationTranslation, which fails.

        restRiskRecommendationTranslationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskRecommendationTranslation))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        riskRecommendationTranslation.setName(null);

        // Create the RiskRecommendationTranslation, which fails.

        restRiskRecommendationTranslationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskRecommendationTranslation))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRiskRecommendationTranslations() throws Exception {
        // Initialize the database
        insertedRiskRecommendationTranslation = riskRecommendationTranslationRepository.saveAndFlush(riskRecommendationTranslation);

        // Get all the riskRecommendationTranslationList
        restRiskRecommendationTranslationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riskRecommendationTranslation.getId().toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRiskRecommendationTranslation() throws Exception {
        // Initialize the database
        insertedRiskRecommendationTranslation = riskRecommendationTranslationRepository.saveAndFlush(riskRecommendationTranslation);

        // Get the riskRecommendationTranslation
        restRiskRecommendationTranslationMockMvc
            .perform(get(ENTITY_API_URL_ID, riskRecommendationTranslation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(riskRecommendationTranslation.getId().toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRiskRecommendationTranslation() throws Exception {
        // Get the riskRecommendationTranslation
        restRiskRecommendationTranslationMockMvc
            .perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRiskRecommendationTranslation() throws Exception {
        // Initialize the database
        insertedRiskRecommendationTranslation = riskRecommendationTranslationRepository.saveAndFlush(riskRecommendationTranslation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riskRecommendationTranslation
        RiskRecommendationTranslation updatedRiskRecommendationTranslation = riskRecommendationTranslationRepository
            .findById(riskRecommendationTranslation.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedRiskRecommendationTranslation are not directly saved in db
        em.detach(updatedRiskRecommendationTranslation);
        updatedRiskRecommendationTranslation.language(UPDATED_LANGUAGE).name(UPDATED_NAME);

        restRiskRecommendationTranslationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRiskRecommendationTranslation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRiskRecommendationTranslation))
            )
            .andExpect(status().isOk());

        // Validate the RiskRecommendationTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRiskRecommendationTranslationToMatchAllProperties(updatedRiskRecommendationTranslation);
    }

    @Test
    @Transactional
    void putNonExistingRiskRecommendationTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskRecommendationTranslation.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskRecommendationTranslationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, riskRecommendationTranslation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(riskRecommendationTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskRecommendationTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRiskRecommendationTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskRecommendationTranslation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskRecommendationTranslationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(riskRecommendationTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskRecommendationTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRiskRecommendationTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskRecommendationTranslation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskRecommendationTranslationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riskRecommendationTranslation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RiskRecommendationTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRiskRecommendationTranslationWithPatch() throws Exception {
        // Initialize the database
        insertedRiskRecommendationTranslation = riskRecommendationTranslationRepository.saveAndFlush(riskRecommendationTranslation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riskRecommendationTranslation using partial update
        RiskRecommendationTranslation partialUpdatedRiskRecommendationTranslation = new RiskRecommendationTranslation();
        partialUpdatedRiskRecommendationTranslation.setId(riskRecommendationTranslation.getId());

        partialUpdatedRiskRecommendationTranslation.name(UPDATED_NAME);

        restRiskRecommendationTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiskRecommendationTranslation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRiskRecommendationTranslation))
            )
            .andExpect(status().isOk());

        // Validate the RiskRecommendationTranslation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRiskRecommendationTranslationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRiskRecommendationTranslation, riskRecommendationTranslation),
            getPersistedRiskRecommendationTranslation(riskRecommendationTranslation)
        );
    }

    @Test
    @Transactional
    void fullUpdateRiskRecommendationTranslationWithPatch() throws Exception {
        // Initialize the database
        insertedRiskRecommendationTranslation = riskRecommendationTranslationRepository.saveAndFlush(riskRecommendationTranslation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riskRecommendationTranslation using partial update
        RiskRecommendationTranslation partialUpdatedRiskRecommendationTranslation = new RiskRecommendationTranslation();
        partialUpdatedRiskRecommendationTranslation.setId(riskRecommendationTranslation.getId());

        partialUpdatedRiskRecommendationTranslation.language(UPDATED_LANGUAGE).name(UPDATED_NAME);

        restRiskRecommendationTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiskRecommendationTranslation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRiskRecommendationTranslation))
            )
            .andExpect(status().isOk());

        // Validate the RiskRecommendationTranslation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRiskRecommendationTranslationUpdatableFieldsEquals(
            partialUpdatedRiskRecommendationTranslation,
            getPersistedRiskRecommendationTranslation(partialUpdatedRiskRecommendationTranslation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRiskRecommendationTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskRecommendationTranslation.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskRecommendationTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, riskRecommendationTranslation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(riskRecommendationTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskRecommendationTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRiskRecommendationTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskRecommendationTranslation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskRecommendationTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(riskRecommendationTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskRecommendationTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRiskRecommendationTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riskRecommendationTranslation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskRecommendationTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(riskRecommendationTranslation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RiskRecommendationTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRiskRecommendationTranslation() throws Exception {
        // Initialize the database
        insertedRiskRecommendationTranslation = riskRecommendationTranslationRepository.saveAndFlush(riskRecommendationTranslation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the riskRecommendationTranslation
        restRiskRecommendationTranslationMockMvc
            .perform(delete(ENTITY_API_URL_ID, riskRecommendationTranslation.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return riskRecommendationTranslationRepository.count();
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

    protected RiskRecommendationTranslation getPersistedRiskRecommendationTranslation(
        RiskRecommendationTranslation riskRecommendationTranslation
    ) {
        return riskRecommendationTranslationRepository.findById(riskRecommendationTranslation.getId()).orElseThrow();
    }

    protected void assertPersistedRiskRecommendationTranslationToMatchAllProperties(
        RiskRecommendationTranslation expectedRiskRecommendationTranslation
    ) {
        assertRiskRecommendationTranslationAllPropertiesEquals(
            expectedRiskRecommendationTranslation,
            getPersistedRiskRecommendationTranslation(expectedRiskRecommendationTranslation)
        );
    }

    protected void assertPersistedRiskRecommendationTranslationToMatchUpdatableProperties(
        RiskRecommendationTranslation expectedRiskRecommendationTranslation
    ) {
        assertRiskRecommendationTranslationAllUpdatablePropertiesEquals(
            expectedRiskRecommendationTranslation,
            getPersistedRiskRecommendationTranslation(expectedRiskRecommendationTranslation)
        );
    }
}
