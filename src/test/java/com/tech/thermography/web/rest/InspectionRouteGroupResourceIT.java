package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.InspectionRouteGroupAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.InspectionRouteGroup;
import com.tech.thermography.repository.InspectionRouteGroupRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InspectionRouteGroupResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InspectionRouteGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inspection-route-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InspectionRouteGroupRepository inspectionRouteGroupRepository;

    @Mock
    private InspectionRouteGroupRepository inspectionRouteGroupRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectionRouteGroupMockMvc;

    private InspectionRouteGroup inspectionRouteGroup;

    private InspectionRouteGroup insertedInspectionRouteGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRouteGroup createEntity() {
        return new InspectionRouteGroup().name(DEFAULT_NAME).title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRouteGroup createUpdatedEntity() {
        return new InspectionRouteGroup().name(UPDATED_NAME).title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    void initTest() {
        inspectionRouteGroup = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInspectionRouteGroup != null) {
            inspectionRouteGroupRepository.delete(insertedInspectionRouteGroup);
            insertedInspectionRouteGroup = null;
        }
    }

    @Test
    @Transactional
    void createInspectionRouteGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InspectionRouteGroup
        var returnedInspectionRouteGroup = om.readValue(
            restInspectionRouteGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteGroup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InspectionRouteGroup.class
        );

        // Validate the InspectionRouteGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInspectionRouteGroupUpdatableFieldsEquals(
            returnedInspectionRouteGroup,
            getPersistedInspectionRouteGroup(returnedInspectionRouteGroup)
        );

        insertedInspectionRouteGroup = returnedInspectionRouteGroup;
    }

    @Test
    @Transactional
    void createInspectionRouteGroupWithExistingId() throws Exception {
        // Create the InspectionRouteGroup with an existing ID
        insertedInspectionRouteGroup = inspectionRouteGroupRepository.saveAndFlush(inspectionRouteGroup);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionRouteGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteGroup)))
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRouteGroup.setName(null);

        // Create the InspectionRouteGroup, which fails.

        restInspectionRouteGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteGroup)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInspectionRouteGroups() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroup = inspectionRouteGroupRepository.saveAndFlush(inspectionRouteGroup);

        // Get all the inspectionRouteGroupList
        restInspectionRouteGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionRouteGroup.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInspectionRouteGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        when(inspectionRouteGroupRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInspectionRouteGroupMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(inspectionRouteGroupRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInspectionRouteGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(inspectionRouteGroupRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInspectionRouteGroupMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(inspectionRouteGroupRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInspectionRouteGroup() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroup = inspectionRouteGroupRepository.saveAndFlush(inspectionRouteGroup);

        // Get the inspectionRouteGroup
        restInspectionRouteGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, inspectionRouteGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionRouteGroup.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingInspectionRouteGroup() throws Exception {
        // Get the inspectionRouteGroup
        restInspectionRouteGroupMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInspectionRouteGroup() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroup = inspectionRouteGroupRepository.saveAndFlush(inspectionRouteGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRouteGroup
        InspectionRouteGroup updatedInspectionRouteGroup = inspectionRouteGroupRepository
            .findById(inspectionRouteGroup.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInspectionRouteGroup are not directly saved in db
        em.detach(updatedInspectionRouteGroup);
        updatedInspectionRouteGroup.name(UPDATED_NAME).title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restInspectionRouteGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspectionRouteGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInspectionRouteGroup))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRouteGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInspectionRouteGroupToMatchAllProperties(updatedInspectionRouteGroup);
    }

    @Test
    @Transactional
    void putNonExistingInspectionRouteGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroup.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRouteGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspectionRouteGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRouteGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspectionRouteGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRouteGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspectionRouteGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRouteGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectionRouteGroupWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroup = inspectionRouteGroupRepository.saveAndFlush(inspectionRouteGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRouteGroup using partial update
        InspectionRouteGroup partialUpdatedInspectionRouteGroup = new InspectionRouteGroup();
        partialUpdatedInspectionRouteGroup.setId(inspectionRouteGroup.getId());

        partialUpdatedInspectionRouteGroup.name(UPDATED_NAME).title(UPDATED_TITLE);

        restInspectionRouteGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRouteGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRouteGroup))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRouteGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRouteGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInspectionRouteGroup, inspectionRouteGroup),
            getPersistedInspectionRouteGroup(inspectionRouteGroup)
        );
    }

    @Test
    @Transactional
    void fullUpdateInspectionRouteGroupWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroup = inspectionRouteGroupRepository.saveAndFlush(inspectionRouteGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRouteGroup using partial update
        InspectionRouteGroup partialUpdatedInspectionRouteGroup = new InspectionRouteGroup();
        partialUpdatedInspectionRouteGroup.setId(inspectionRouteGroup.getId());

        partialUpdatedInspectionRouteGroup.name(UPDATED_NAME).title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restInspectionRouteGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRouteGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRouteGroup))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRouteGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRouteGroupUpdatableFieldsEquals(
            partialUpdatedInspectionRouteGroup,
            getPersistedInspectionRouteGroup(partialUpdatedInspectionRouteGroup)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInspectionRouteGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroup.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRouteGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspectionRouteGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRouteGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspectionRouteGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRouteGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspectionRouteGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroup.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inspectionRouteGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRouteGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspectionRouteGroup() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroup = inspectionRouteGroupRepository.saveAndFlush(inspectionRouteGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inspectionRouteGroup
        restInspectionRouteGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspectionRouteGroup.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inspectionRouteGroupRepository.count();
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

    protected InspectionRouteGroup getPersistedInspectionRouteGroup(InspectionRouteGroup inspectionRouteGroup) {
        return inspectionRouteGroupRepository.findById(inspectionRouteGroup.getId()).orElseThrow();
    }

    protected void assertPersistedInspectionRouteGroupToMatchAllProperties(InspectionRouteGroup expectedInspectionRouteGroup) {
        assertInspectionRouteGroupAllPropertiesEquals(
            expectedInspectionRouteGroup,
            getPersistedInspectionRouteGroup(expectedInspectionRouteGroup)
        );
    }

    protected void assertPersistedInspectionRouteGroupToMatchUpdatableProperties(InspectionRouteGroup expectedInspectionRouteGroup) {
        assertInspectionRouteGroupAllUpdatablePropertiesEquals(
            expectedInspectionRouteGroup,
            getPersistedInspectionRouteGroup(expectedInspectionRouteGroup)
        );
    }
}
