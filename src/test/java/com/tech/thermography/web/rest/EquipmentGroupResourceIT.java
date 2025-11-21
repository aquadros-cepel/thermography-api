package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.EquipmentGroupAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.EquipmentGroup;
import com.tech.thermography.repository.EquipmentGroupRepository;
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
 * Integration tests for the {@link EquipmentGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipmentGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/equipment-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EquipmentGroupRepository equipmentGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipmentGroupMockMvc;

    private EquipmentGroup equipmentGroup;

    private EquipmentGroup insertedEquipmentGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentGroup createEntity() {
        return new EquipmentGroup().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentGroup createUpdatedEntity() {
        return new EquipmentGroup().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    void initTest() {
        equipmentGroup = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEquipmentGroup != null) {
            equipmentGroupRepository.delete(insertedEquipmentGroup);
            insertedEquipmentGroup = null;
        }
    }

    @Test
    @Transactional
    void createEquipmentGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EquipmentGroup
        var returnedEquipmentGroup = om.readValue(
            restEquipmentGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentGroup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EquipmentGroup.class
        );

        // Validate the EquipmentGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEquipmentGroupUpdatableFieldsEquals(returnedEquipmentGroup, getPersistedEquipmentGroup(returnedEquipmentGroup));

        insertedEquipmentGroup = returnedEquipmentGroup;
    }

    @Test
    @Transactional
    void createEquipmentGroupWithExistingId() throws Exception {
        // Create the EquipmentGroup with an existing ID
        insertedEquipmentGroup = equipmentGroupRepository.saveAndFlush(equipmentGroup);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentGroup)))
            .andExpect(status().isBadRequest());

        // Validate the EquipmentGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipmentGroup.setName(null);

        // Create the EquipmentGroup, which fails.

        restEquipmentGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentGroup)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEquipmentGroups() throws Exception {
        // Initialize the database
        insertedEquipmentGroup = equipmentGroupRepository.saveAndFlush(equipmentGroup);

        // Get all the equipmentGroupList
        restEquipmentGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipmentGroup.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getEquipmentGroup() throws Exception {
        // Initialize the database
        insertedEquipmentGroup = equipmentGroupRepository.saveAndFlush(equipmentGroup);

        // Get the equipmentGroup
        restEquipmentGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, equipmentGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipmentGroup.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingEquipmentGroup() throws Exception {
        // Get the equipmentGroup
        restEquipmentGroupMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipmentGroup() throws Exception {
        // Initialize the database
        insertedEquipmentGroup = equipmentGroupRepository.saveAndFlush(equipmentGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentGroup
        EquipmentGroup updatedEquipmentGroup = equipmentGroupRepository.findById(equipmentGroup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEquipmentGroup are not directly saved in db
        em.detach(updatedEquipmentGroup);
        updatedEquipmentGroup.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restEquipmentGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEquipmentGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEquipmentGroup))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEquipmentGroupToMatchAllProperties(updatedEquipmentGroup);
    }

    @Test
    @Transactional
    void putNonExistingEquipmentGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentGroup.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipmentGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipmentGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipmentGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipmentGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipmentGroupWithPatch() throws Exception {
        // Initialize the database
        insertedEquipmentGroup = equipmentGroupRepository.saveAndFlush(equipmentGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentGroup using partial update
        EquipmentGroup partialUpdatedEquipmentGroup = new EquipmentGroup();
        partialUpdatedEquipmentGroup.setId(equipmentGroup.getId());

        partialUpdatedEquipmentGroup.name(UPDATED_NAME);

        restEquipmentGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipmentGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipmentGroup))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipmentGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEquipmentGroup, equipmentGroup),
            getPersistedEquipmentGroup(equipmentGroup)
        );
    }

    @Test
    @Transactional
    void fullUpdateEquipmentGroupWithPatch() throws Exception {
        // Initialize the database
        insertedEquipmentGroup = equipmentGroupRepository.saveAndFlush(equipmentGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentGroup using partial update
        EquipmentGroup partialUpdatedEquipmentGroup = new EquipmentGroup();
        partialUpdatedEquipmentGroup.setId(equipmentGroup.getId());

        partialUpdatedEquipmentGroup.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restEquipmentGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipmentGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipmentGroup))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipmentGroupUpdatableFieldsEquals(partialUpdatedEquipmentGroup, getPersistedEquipmentGroup(partialUpdatedEquipmentGroup));
    }

    @Test
    @Transactional
    void patchNonExistingEquipmentGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentGroup.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipmentGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipmentGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipmentGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipmentGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipmentGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(equipmentGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipmentGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipmentGroup() throws Exception {
        // Initialize the database
        insertedEquipmentGroup = equipmentGroupRepository.saveAndFlush(equipmentGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the equipmentGroup
        restEquipmentGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipmentGroup.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return equipmentGroupRepository.count();
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

    protected EquipmentGroup getPersistedEquipmentGroup(EquipmentGroup equipmentGroup) {
        return equipmentGroupRepository.findById(equipmentGroup.getId()).orElseThrow();
    }

    protected void assertPersistedEquipmentGroupToMatchAllProperties(EquipmentGroup expectedEquipmentGroup) {
        assertEquipmentGroupAllPropertiesEquals(expectedEquipmentGroup, getPersistedEquipmentGroup(expectedEquipmentGroup));
    }

    protected void assertPersistedEquipmentGroupToMatchUpdatableProperties(EquipmentGroup expectedEquipmentGroup) {
        assertEquipmentGroupAllUpdatablePropertiesEquals(expectedEquipmentGroup, getPersistedEquipmentGroup(expectedEquipmentGroup));
    }
}
