package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.EquipmentComponentTemperatureLimitsAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.EquipmentComponentTemperatureLimits;
import com.tech.thermography.repository.EquipmentComponentTemperatureLimitsRepository;
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
 * Integration tests for the {@link EquipmentComponentTemperatureLimitsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipmentComponentTemperatureLimitsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NORMAL = "AAAAAAAAAA";
    private static final String UPDATED_NORMAL = "BBBBBBBBBB";

    private static final String DEFAULT_LOW_RISK = "AAAAAAAAAA";
    private static final String UPDATED_LOW_RISK = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIUM_RISK = "AAAAAAAAAA";
    private static final String UPDATED_MEDIUM_RISK = "BBBBBBBBBB";

    private static final String DEFAULT_HIGH_RISK = "AAAAAAAAAA";
    private static final String UPDATED_HIGH_RISK = "BBBBBBBBBB";

    private static final String DEFAULT_IMMINENT_HIGH_RISK = "AAAAAAAAAA";
    private static final String UPDATED_IMMINENT_HIGH_RISK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/equipment-component-temperature-limits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EquipmentComponentTemperatureLimitsRepository equipmentComponentTemperatureLimitsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipmentComponentTemperatureLimitsMockMvc;

    private EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits;

    private EquipmentComponentTemperatureLimits insertedEquipmentComponentTemperatureLimits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentComponentTemperatureLimits createEntity() {
        return new EquipmentComponentTemperatureLimits()
            .name(DEFAULT_NAME)
            .normal(DEFAULT_NORMAL)
            .lowRisk(DEFAULT_LOW_RISK)
            .mediumRisk(DEFAULT_MEDIUM_RISK)
            .highRisk(DEFAULT_HIGH_RISK)
            .imminentHighRisk(DEFAULT_IMMINENT_HIGH_RISK);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentComponentTemperatureLimits createUpdatedEntity() {
        return new EquipmentComponentTemperatureLimits()
            .name(UPDATED_NAME)
            .normal(UPDATED_NORMAL)
            .lowRisk(UPDATED_LOW_RISK)
            .mediumRisk(UPDATED_MEDIUM_RISK)
            .highRisk(UPDATED_HIGH_RISK)
            .imminentHighRisk(UPDATED_IMMINENT_HIGH_RISK);
    }

    @BeforeEach
    void initTest() {
        equipmentComponentTemperatureLimits = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEquipmentComponentTemperatureLimits != null) {
            equipmentComponentTemperatureLimitsRepository.delete(insertedEquipmentComponentTemperatureLimits);
            insertedEquipmentComponentTemperatureLimits = null;
        }
    }

    @Test
    @Transactional
    void createEquipmentComponentTemperatureLimits() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EquipmentComponentTemperatureLimits
        var returnedEquipmentComponentTemperatureLimits = om.readValue(
            restEquipmentComponentTemperatureLimitsMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(equipmentComponentTemperatureLimits))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EquipmentComponentTemperatureLimits.class
        );

        // Validate the EquipmentComponentTemperatureLimits in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEquipmentComponentTemperatureLimitsUpdatableFieldsEquals(
            returnedEquipmentComponentTemperatureLimits,
            getPersistedEquipmentComponentTemperatureLimits(returnedEquipmentComponentTemperatureLimits)
        );

        insertedEquipmentComponentTemperatureLimits = returnedEquipmentComponentTemperatureLimits;
    }

    @Test
    @Transactional
    void createEquipmentComponentTemperatureLimitsWithExistingId() throws Exception {
        // Create the EquipmentComponentTemperatureLimits with an existing ID
        insertedEquipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository.saveAndFlush(
            equipmentComponentTemperatureLimits
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentComponentTemperatureLimits))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponentTemperatureLimits in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEquipmentComponentTemperatureLimits() throws Exception {
        // Initialize the database
        insertedEquipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository.saveAndFlush(
            equipmentComponentTemperatureLimits
        );

        // Get all the equipmentComponentTemperatureLimitsList
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipmentComponentTemperatureLimits.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].normal").value(hasItem(DEFAULT_NORMAL)))
            .andExpect(jsonPath("$.[*].lowRisk").value(hasItem(DEFAULT_LOW_RISK)))
            .andExpect(jsonPath("$.[*].mediumRisk").value(hasItem(DEFAULT_MEDIUM_RISK)))
            .andExpect(jsonPath("$.[*].highRisk").value(hasItem(DEFAULT_HIGH_RISK)))
            .andExpect(jsonPath("$.[*].imminentHighRisk").value(hasItem(DEFAULT_IMMINENT_HIGH_RISK)));
    }

    @Test
    @Transactional
    void getEquipmentComponentTemperatureLimits() throws Exception {
        // Initialize the database
        insertedEquipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository.saveAndFlush(
            equipmentComponentTemperatureLimits
        );

        // Get the equipmentComponentTemperatureLimits
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(get(ENTITY_API_URL_ID, equipmentComponentTemperatureLimits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipmentComponentTemperatureLimits.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.normal").value(DEFAULT_NORMAL))
            .andExpect(jsonPath("$.lowRisk").value(DEFAULT_LOW_RISK))
            .andExpect(jsonPath("$.mediumRisk").value(DEFAULT_MEDIUM_RISK))
            .andExpect(jsonPath("$.highRisk").value(DEFAULT_HIGH_RISK))
            .andExpect(jsonPath("$.imminentHighRisk").value(DEFAULT_IMMINENT_HIGH_RISK));
    }

    @Test
    @Transactional
    void getNonExistingEquipmentComponentTemperatureLimits() throws Exception {
        // Get the equipmentComponentTemperatureLimits
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipmentComponentTemperatureLimits() throws Exception {
        // Initialize the database
        insertedEquipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository.saveAndFlush(
            equipmentComponentTemperatureLimits
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentComponentTemperatureLimits
        EquipmentComponentTemperatureLimits updatedEquipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository
            .findById(equipmentComponentTemperatureLimits.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedEquipmentComponentTemperatureLimits are not directly saved in db
        em.detach(updatedEquipmentComponentTemperatureLimits);
        updatedEquipmentComponentTemperatureLimits
            .name(UPDATED_NAME)
            .normal(UPDATED_NORMAL)
            .lowRisk(UPDATED_LOW_RISK)
            .mediumRisk(UPDATED_MEDIUM_RISK)
            .highRisk(UPDATED_HIGH_RISK)
            .imminentHighRisk(UPDATED_IMMINENT_HIGH_RISK);

        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEquipmentComponentTemperatureLimits.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEquipmentComponentTemperatureLimits))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentComponentTemperatureLimits in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEquipmentComponentTemperatureLimitsToMatchAllProperties(updatedEquipmentComponentTemperatureLimits);
    }

    @Test
    @Transactional
    void putNonExistingEquipmentComponentTemperatureLimits() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponentTemperatureLimits.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipmentComponentTemperatureLimits.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentComponentTemperatureLimits))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponentTemperatureLimits in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipmentComponentTemperatureLimits() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponentTemperatureLimits.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentComponentTemperatureLimits))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponentTemperatureLimits in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipmentComponentTemperatureLimits() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponentTemperatureLimits.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentComponentTemperatureLimits))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipmentComponentTemperatureLimits in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipmentComponentTemperatureLimitsWithPatch() throws Exception {
        // Initialize the database
        insertedEquipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository.saveAndFlush(
            equipmentComponentTemperatureLimits
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentComponentTemperatureLimits using partial update
        EquipmentComponentTemperatureLimits partialUpdatedEquipmentComponentTemperatureLimits = new EquipmentComponentTemperatureLimits();
        partialUpdatedEquipmentComponentTemperatureLimits.setId(equipmentComponentTemperatureLimits.getId());

        partialUpdatedEquipmentComponentTemperatureLimits.name(UPDATED_NAME).highRisk(UPDATED_HIGH_RISK);

        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipmentComponentTemperatureLimits.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipmentComponentTemperatureLimits))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentComponentTemperatureLimits in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipmentComponentTemperatureLimitsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEquipmentComponentTemperatureLimits, equipmentComponentTemperatureLimits),
            getPersistedEquipmentComponentTemperatureLimits(equipmentComponentTemperatureLimits)
        );
    }

    @Test
    @Transactional
    void fullUpdateEquipmentComponentTemperatureLimitsWithPatch() throws Exception {
        // Initialize the database
        insertedEquipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository.saveAndFlush(
            equipmentComponentTemperatureLimits
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentComponentTemperatureLimits using partial update
        EquipmentComponentTemperatureLimits partialUpdatedEquipmentComponentTemperatureLimits = new EquipmentComponentTemperatureLimits();
        partialUpdatedEquipmentComponentTemperatureLimits.setId(equipmentComponentTemperatureLimits.getId());

        partialUpdatedEquipmentComponentTemperatureLimits
            .name(UPDATED_NAME)
            .normal(UPDATED_NORMAL)
            .lowRisk(UPDATED_LOW_RISK)
            .mediumRisk(UPDATED_MEDIUM_RISK)
            .highRisk(UPDATED_HIGH_RISK)
            .imminentHighRisk(UPDATED_IMMINENT_HIGH_RISK);

        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipmentComponentTemperatureLimits.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipmentComponentTemperatureLimits))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentComponentTemperatureLimits in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipmentComponentTemperatureLimitsUpdatableFieldsEquals(
            partialUpdatedEquipmentComponentTemperatureLimits,
            getPersistedEquipmentComponentTemperatureLimits(partialUpdatedEquipmentComponentTemperatureLimits)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEquipmentComponentTemperatureLimits() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponentTemperatureLimits.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipmentComponentTemperatureLimits.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipmentComponentTemperatureLimits))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponentTemperatureLimits in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipmentComponentTemperatureLimits() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponentTemperatureLimits.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipmentComponentTemperatureLimits))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponentTemperatureLimits in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipmentComponentTemperatureLimits() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponentTemperatureLimits.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipmentComponentTemperatureLimits))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipmentComponentTemperatureLimits in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipmentComponentTemperatureLimits() throws Exception {
        // Initialize the database
        insertedEquipmentComponentTemperatureLimits = equipmentComponentTemperatureLimitsRepository.saveAndFlush(
            equipmentComponentTemperatureLimits
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the equipmentComponentTemperatureLimits
        restEquipmentComponentTemperatureLimitsMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipmentComponentTemperatureLimits.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return equipmentComponentTemperatureLimitsRepository.count();
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

    protected EquipmentComponentTemperatureLimits getPersistedEquipmentComponentTemperatureLimits(
        EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits
    ) {
        return equipmentComponentTemperatureLimitsRepository.findById(equipmentComponentTemperatureLimits.getId()).orElseThrow();
    }

    protected void assertPersistedEquipmentComponentTemperatureLimitsToMatchAllProperties(
        EquipmentComponentTemperatureLimits expectedEquipmentComponentTemperatureLimits
    ) {
        assertEquipmentComponentTemperatureLimitsAllPropertiesEquals(
            expectedEquipmentComponentTemperatureLimits,
            getPersistedEquipmentComponentTemperatureLimits(expectedEquipmentComponentTemperatureLimits)
        );
    }

    protected void assertPersistedEquipmentComponentTemperatureLimitsToMatchUpdatableProperties(
        EquipmentComponentTemperatureLimits expectedEquipmentComponentTemperatureLimits
    ) {
        assertEquipmentComponentTemperatureLimitsAllUpdatablePropertiesEquals(
            expectedEquipmentComponentTemperatureLimits,
            getPersistedEquipmentComponentTemperatureLimits(expectedEquipmentComponentTemperatureLimits)
        );
    }
}
