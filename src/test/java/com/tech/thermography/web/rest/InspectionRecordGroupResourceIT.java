package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.InspectionRecordGroupAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.InspectionRecordGroup;
import com.tech.thermography.repository.InspectionRecordGroupRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
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
 * Integration tests for the {@link InspectionRecordGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspectionRecordGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_INDEX = 1;
    private static final Integer UPDATED_ORDER_INDEX = 2;

    private static final Boolean DEFAULT_FINISHED = false;
    private static final Boolean UPDATED_FINISHED = true;

    private static final Instant DEFAULT_FINISHED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISHED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/inspection-record-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InspectionRecordGroupRepository inspectionRecordGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectionRecordGroupMockMvc;

    private InspectionRecordGroup inspectionRecordGroup;

    private InspectionRecordGroup insertedInspectionRecordGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRecordGroup createEntity() {
        return new InspectionRecordGroup()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .orderIndex(DEFAULT_ORDER_INDEX)
            .finished(DEFAULT_FINISHED)
            .finishedAt(DEFAULT_FINISHED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRecordGroup createUpdatedEntity() {
        return new InspectionRecordGroup()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .orderIndex(UPDATED_ORDER_INDEX)
            .finished(UPDATED_FINISHED)
            .finishedAt(UPDATED_FINISHED_AT);
    }

    @BeforeEach
    void initTest() {
        inspectionRecordGroup = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInspectionRecordGroup != null) {
            inspectionRecordGroupRepository.delete(insertedInspectionRecordGroup);
            insertedInspectionRecordGroup = null;
        }
    }

    @Test
    @Transactional
    void createInspectionRecordGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InspectionRecordGroup
        var returnedInspectionRecordGroup = om.readValue(
            restInspectionRecordGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecordGroup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InspectionRecordGroup.class
        );

        // Validate the InspectionRecordGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInspectionRecordGroupUpdatableFieldsEquals(
            returnedInspectionRecordGroup,
            getPersistedInspectionRecordGroup(returnedInspectionRecordGroup)
        );

        insertedInspectionRecordGroup = returnedInspectionRecordGroup;
    }

    @Test
    @Transactional
    void createInspectionRecordGroupWithExistingId() throws Exception {
        // Create the InspectionRecordGroup with an existing ID
        insertedInspectionRecordGroup = inspectionRecordGroupRepository.saveAndFlush(inspectionRecordGroup);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionRecordGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecordGroup)))
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRecordGroup.setName(null);

        // Create the InspectionRecordGroup, which fails.

        restInspectionRecordGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecordGroup)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInspectionRecordGroups() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroup = inspectionRecordGroupRepository.saveAndFlush(inspectionRecordGroup);

        // Get all the inspectionRecordGroupList
        restInspectionRecordGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionRecordGroup.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX)))
            .andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED)))
            .andExpect(jsonPath("$.[*].finishedAt").value(hasItem(DEFAULT_FINISHED_AT.toString())));
    }

    @Test
    @Transactional
    void getInspectionRecordGroup() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroup = inspectionRecordGroupRepository.saveAndFlush(inspectionRecordGroup);

        // Get the inspectionRecordGroup
        restInspectionRecordGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, inspectionRecordGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionRecordGroup.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX))
            .andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED))
            .andExpect(jsonPath("$.finishedAt").value(DEFAULT_FINISHED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInspectionRecordGroup() throws Exception {
        // Get the inspectionRecordGroup
        restInspectionRecordGroupMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInspectionRecordGroup() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroup = inspectionRecordGroupRepository.saveAndFlush(inspectionRecordGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRecordGroup
        InspectionRecordGroup updatedInspectionRecordGroup = inspectionRecordGroupRepository
            .findById(inspectionRecordGroup.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInspectionRecordGroup are not directly saved in db
        em.detach(updatedInspectionRecordGroup);
        updatedInspectionRecordGroup
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .orderIndex(UPDATED_ORDER_INDEX)
            .finished(UPDATED_FINISHED)
            .finishedAt(UPDATED_FINISHED_AT);

        restInspectionRecordGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspectionRecordGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInspectionRecordGroup))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecordGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInspectionRecordGroupToMatchAllProperties(updatedInspectionRecordGroup);
    }

    @Test
    @Transactional
    void putNonExistingInspectionRecordGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroup.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRecordGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspectionRecordGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRecordGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspectionRecordGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRecordGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspectionRecordGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecordGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRecordGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectionRecordGroupWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroup = inspectionRecordGroupRepository.saveAndFlush(inspectionRecordGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRecordGroup using partial update
        InspectionRecordGroup partialUpdatedInspectionRecordGroup = new InspectionRecordGroup();
        partialUpdatedInspectionRecordGroup.setId(inspectionRecordGroup.getId());

        partialUpdatedInspectionRecordGroup.name(UPDATED_NAME);

        restInspectionRecordGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRecordGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRecordGroup))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecordGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRecordGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInspectionRecordGroup, inspectionRecordGroup),
            getPersistedInspectionRecordGroup(inspectionRecordGroup)
        );
    }

    @Test
    @Transactional
    void fullUpdateInspectionRecordGroupWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroup = inspectionRecordGroupRepository.saveAndFlush(inspectionRecordGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRecordGroup using partial update
        InspectionRecordGroup partialUpdatedInspectionRecordGroup = new InspectionRecordGroup();
        partialUpdatedInspectionRecordGroup.setId(inspectionRecordGroup.getId());

        partialUpdatedInspectionRecordGroup
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .orderIndex(UPDATED_ORDER_INDEX)
            .finished(UPDATED_FINISHED)
            .finishedAt(UPDATED_FINISHED_AT);

        restInspectionRecordGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRecordGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRecordGroup))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecordGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRecordGroupUpdatableFieldsEquals(
            partialUpdatedInspectionRecordGroup,
            getPersistedInspectionRecordGroup(partialUpdatedInspectionRecordGroup)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInspectionRecordGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroup.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRecordGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspectionRecordGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRecordGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspectionRecordGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRecordGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspectionRecordGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inspectionRecordGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRecordGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspectionRecordGroup() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroup = inspectionRecordGroupRepository.saveAndFlush(inspectionRecordGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inspectionRecordGroup
        restInspectionRecordGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspectionRecordGroup.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inspectionRecordGroupRepository.count();
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

    protected InspectionRecordGroup getPersistedInspectionRecordGroup(InspectionRecordGroup inspectionRecordGroup) {
        return inspectionRecordGroupRepository.findById(inspectionRecordGroup.getId()).orElseThrow();
    }

    protected void assertPersistedInspectionRecordGroupToMatchAllProperties(InspectionRecordGroup expectedInspectionRecordGroup) {
        assertInspectionRecordGroupAllPropertiesEquals(
            expectedInspectionRecordGroup,
            getPersistedInspectionRecordGroup(expectedInspectionRecordGroup)
        );
    }

    protected void assertPersistedInspectionRecordGroupToMatchUpdatableProperties(InspectionRecordGroup expectedInspectionRecordGroup) {
        assertInspectionRecordGroupAllUpdatablePropertiesEquals(
            expectedInspectionRecordGroup,
            getPersistedInspectionRecordGroup(expectedInspectionRecordGroup)
        );
    }
}
