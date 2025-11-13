package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.EquipmentTypeTranslationAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.EquipmentTypeTranslation;
import com.tech.thermography.domain.enumeration.EquipmentType;
import com.tech.thermography.repository.EquipmentTypeTranslationRepository;
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
 * Integration tests for the {@link EquipmentTypeTranslationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipmentTypeTranslationResourceIT {

    private static final EquipmentType DEFAULT_CODE = EquipmentType.AUTOTRANSFORMER;
    private static final EquipmentType UPDATED_CODE = EquipmentType.BATTERY_BANK;

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/equipment-type-translations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EquipmentTypeTranslationRepository equipmentTypeTranslationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipmentTypeTranslationMockMvc;

    private EquipmentTypeTranslation equipmentTypeTranslation;

    private EquipmentTypeTranslation insertedEquipmentTypeTranslation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentTypeTranslation createEntity() {
        return new EquipmentTypeTranslation().code(DEFAULT_CODE).language(DEFAULT_LANGUAGE).name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentTypeTranslation createUpdatedEntity() {
        return new EquipmentTypeTranslation().code(UPDATED_CODE).language(UPDATED_LANGUAGE).name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        equipmentTypeTranslation = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEquipmentTypeTranslation != null) {
            equipmentTypeTranslationRepository.delete(insertedEquipmentTypeTranslation);
            insertedEquipmentTypeTranslation = null;
        }
    }

    @Test
    @Transactional
    void createEquipmentTypeTranslation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EquipmentTypeTranslation
        var returnedEquipmentTypeTranslation = om.readValue(
            restEquipmentTypeTranslationMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentTypeTranslation))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EquipmentTypeTranslation.class
        );

        // Validate the EquipmentTypeTranslation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEquipmentTypeTranslationUpdatableFieldsEquals(
            returnedEquipmentTypeTranslation,
            getPersistedEquipmentTypeTranslation(returnedEquipmentTypeTranslation)
        );

        insertedEquipmentTypeTranslation = returnedEquipmentTypeTranslation;
    }

    @Test
    @Transactional
    void createEquipmentTypeTranslationWithExistingId() throws Exception {
        // Create the EquipmentTypeTranslation with an existing ID
        insertedEquipmentTypeTranslation = equipmentTypeTranslationRepository.saveAndFlush(equipmentTypeTranslation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentTypeTranslationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentTypeTranslation)))
            .andExpect(status().isBadRequest());

        // Validate the EquipmentTypeTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipmentTypeTranslation.setCode(null);

        // Create the EquipmentTypeTranslation, which fails.

        restEquipmentTypeTranslationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentTypeTranslation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLanguageIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipmentTypeTranslation.setLanguage(null);

        // Create the EquipmentTypeTranslation, which fails.

        restEquipmentTypeTranslationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentTypeTranslation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipmentTypeTranslation.setName(null);

        // Create the EquipmentTypeTranslation, which fails.

        restEquipmentTypeTranslationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentTypeTranslation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEquipmentTypeTranslations() throws Exception {
        // Initialize the database
        insertedEquipmentTypeTranslation = equipmentTypeTranslationRepository.saveAndFlush(equipmentTypeTranslation);

        // Get all the equipmentTypeTranslationList
        restEquipmentTypeTranslationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipmentTypeTranslation.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getEquipmentTypeTranslation() throws Exception {
        // Initialize the database
        insertedEquipmentTypeTranslation = equipmentTypeTranslationRepository.saveAndFlush(equipmentTypeTranslation);

        // Get the equipmentTypeTranslation
        restEquipmentTypeTranslationMockMvc
            .perform(get(ENTITY_API_URL_ID, equipmentTypeTranslation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipmentTypeTranslation.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingEquipmentTypeTranslation() throws Exception {
        // Get the equipmentTypeTranslation
        restEquipmentTypeTranslationMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipmentTypeTranslation() throws Exception {
        // Initialize the database
        insertedEquipmentTypeTranslation = equipmentTypeTranslationRepository.saveAndFlush(equipmentTypeTranslation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentTypeTranslation
        EquipmentTypeTranslation updatedEquipmentTypeTranslation = equipmentTypeTranslationRepository
            .findById(equipmentTypeTranslation.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedEquipmentTypeTranslation are not directly saved in db
        em.detach(updatedEquipmentTypeTranslation);
        updatedEquipmentTypeTranslation.code(UPDATED_CODE).language(UPDATED_LANGUAGE).name(UPDATED_NAME);

        restEquipmentTypeTranslationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEquipmentTypeTranslation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEquipmentTypeTranslation))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentTypeTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEquipmentTypeTranslationToMatchAllProperties(updatedEquipmentTypeTranslation);
    }

    @Test
    @Transactional
    void putNonExistingEquipmentTypeTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentTypeTranslation.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentTypeTranslationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipmentTypeTranslation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentTypeTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentTypeTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipmentTypeTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentTypeTranslation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentTypeTranslationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentTypeTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentTypeTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipmentTypeTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentTypeTranslation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentTypeTranslationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentTypeTranslation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipmentTypeTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipmentTypeTranslationWithPatch() throws Exception {
        // Initialize the database
        insertedEquipmentTypeTranslation = equipmentTypeTranslationRepository.saveAndFlush(equipmentTypeTranslation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentTypeTranslation using partial update
        EquipmentTypeTranslation partialUpdatedEquipmentTypeTranslation = new EquipmentTypeTranslation();
        partialUpdatedEquipmentTypeTranslation.setId(equipmentTypeTranslation.getId());

        partialUpdatedEquipmentTypeTranslation.name(UPDATED_NAME);

        restEquipmentTypeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipmentTypeTranslation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipmentTypeTranslation))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentTypeTranslation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipmentTypeTranslationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEquipmentTypeTranslation, equipmentTypeTranslation),
            getPersistedEquipmentTypeTranslation(equipmentTypeTranslation)
        );
    }

    @Test
    @Transactional
    void fullUpdateEquipmentTypeTranslationWithPatch() throws Exception {
        // Initialize the database
        insertedEquipmentTypeTranslation = equipmentTypeTranslationRepository.saveAndFlush(equipmentTypeTranslation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentTypeTranslation using partial update
        EquipmentTypeTranslation partialUpdatedEquipmentTypeTranslation = new EquipmentTypeTranslation();
        partialUpdatedEquipmentTypeTranslation.setId(equipmentTypeTranslation.getId());

        partialUpdatedEquipmentTypeTranslation.code(UPDATED_CODE).language(UPDATED_LANGUAGE).name(UPDATED_NAME);

        restEquipmentTypeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipmentTypeTranslation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipmentTypeTranslation))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentTypeTranslation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipmentTypeTranslationUpdatableFieldsEquals(
            partialUpdatedEquipmentTypeTranslation,
            getPersistedEquipmentTypeTranslation(partialUpdatedEquipmentTypeTranslation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEquipmentTypeTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentTypeTranslation.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentTypeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipmentTypeTranslation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipmentTypeTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentTypeTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipmentTypeTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentTypeTranslation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentTypeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipmentTypeTranslation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentTypeTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipmentTypeTranslation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentTypeTranslation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentTypeTranslationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(equipmentTypeTranslation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipmentTypeTranslation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipmentTypeTranslation() throws Exception {
        // Initialize the database
        insertedEquipmentTypeTranslation = equipmentTypeTranslationRepository.saveAndFlush(equipmentTypeTranslation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the equipmentTypeTranslation
        restEquipmentTypeTranslationMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipmentTypeTranslation.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return equipmentTypeTranslationRepository.count();
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

    protected EquipmentTypeTranslation getPersistedEquipmentTypeTranslation(EquipmentTypeTranslation equipmentTypeTranslation) {
        return equipmentTypeTranslationRepository.findById(equipmentTypeTranslation.getId()).orElseThrow();
    }

    protected void assertPersistedEquipmentTypeTranslationToMatchAllProperties(EquipmentTypeTranslation expectedEquipmentTypeTranslation) {
        assertEquipmentTypeTranslationAllPropertiesEquals(
            expectedEquipmentTypeTranslation,
            getPersistedEquipmentTypeTranslation(expectedEquipmentTypeTranslation)
        );
    }

    protected void assertPersistedEquipmentTypeTranslationToMatchUpdatableProperties(
        EquipmentTypeTranslation expectedEquipmentTypeTranslation
    ) {
        assertEquipmentTypeTranslationAllUpdatablePropertiesEquals(
            expectedEquipmentTypeTranslation,
            getPersistedEquipmentTypeTranslation(expectedEquipmentTypeTranslation)
        );
    }
}
