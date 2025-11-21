package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.EquipmentComponentAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.EquipmentComponent;
import com.tech.thermography.repository.EquipmentComponentRepository;
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
 * Integration tests for the {@link EquipmentComponentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EquipmentComponentResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/equipment-components";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EquipmentComponentRepository equipmentComponentRepository;

    @Mock
    private EquipmentComponentRepository equipmentComponentRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipmentComponentMockMvc;

    private EquipmentComponent equipmentComponent;

    private EquipmentComponent insertedEquipmentComponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentComponent createEntity() {
        return new EquipmentComponent().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentComponent createUpdatedEntity() {
        return new EquipmentComponent().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    void initTest() {
        equipmentComponent = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEquipmentComponent != null) {
            equipmentComponentRepository.delete(insertedEquipmentComponent);
            insertedEquipmentComponent = null;
        }
    }

    @Test
    @Transactional
    void createEquipmentComponent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EquipmentComponent
        var returnedEquipmentComponent = om.readValue(
            restEquipmentComponentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentComponent)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EquipmentComponent.class
        );

        // Validate the EquipmentComponent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEquipmentComponentUpdatableFieldsEquals(
            returnedEquipmentComponent,
            getPersistedEquipmentComponent(returnedEquipmentComponent)
        );

        insertedEquipmentComponent = returnedEquipmentComponent;
    }

    @Test
    @Transactional
    void createEquipmentComponentWithExistingId() throws Exception {
        // Create the EquipmentComponent with an existing ID
        insertedEquipmentComponent = equipmentComponentRepository.saveAndFlush(equipmentComponent);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentComponentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentComponent)))
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipmentComponent.setName(null);

        // Create the EquipmentComponent, which fails.

        restEquipmentComponentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentComponent)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEquipmentComponents() throws Exception {
        // Initialize the database
        insertedEquipmentComponent = equipmentComponentRepository.saveAndFlush(equipmentComponent);

        // Get all the equipmentComponentList
        restEquipmentComponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipmentComponent.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEquipmentComponentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(equipmentComponentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEquipmentComponentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(equipmentComponentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEquipmentComponentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(equipmentComponentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEquipmentComponentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(equipmentComponentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEquipmentComponent() throws Exception {
        // Initialize the database
        insertedEquipmentComponent = equipmentComponentRepository.saveAndFlush(equipmentComponent);

        // Get the equipmentComponent
        restEquipmentComponentMockMvc
            .perform(get(ENTITY_API_URL_ID, equipmentComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipmentComponent.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingEquipmentComponent() throws Exception {
        // Get the equipmentComponent
        restEquipmentComponentMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipmentComponent() throws Exception {
        // Initialize the database
        insertedEquipmentComponent = equipmentComponentRepository.saveAndFlush(equipmentComponent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentComponent
        EquipmentComponent updatedEquipmentComponent = equipmentComponentRepository.findById(equipmentComponent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEquipmentComponent are not directly saved in db
        em.detach(updatedEquipmentComponent);
        updatedEquipmentComponent.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restEquipmentComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEquipmentComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEquipmentComponent))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentComponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEquipmentComponentToMatchAllProperties(updatedEquipmentComponent);
    }

    @Test
    @Transactional
    void putNonExistingEquipmentComponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponent.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipmentComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipmentComponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponent.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipmentComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipmentComponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponent.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentComponentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipmentComponent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipmentComponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipmentComponentWithPatch() throws Exception {
        // Initialize the database
        insertedEquipmentComponent = equipmentComponentRepository.saveAndFlush(equipmentComponent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentComponent using partial update
        EquipmentComponent partialUpdatedEquipmentComponent = new EquipmentComponent();
        partialUpdatedEquipmentComponent.setId(equipmentComponent.getId());

        partialUpdatedEquipmentComponent.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restEquipmentComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipmentComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipmentComponent))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentComponent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipmentComponentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEquipmentComponent, equipmentComponent),
            getPersistedEquipmentComponent(equipmentComponent)
        );
    }

    @Test
    @Transactional
    void fullUpdateEquipmentComponentWithPatch() throws Exception {
        // Initialize the database
        insertedEquipmentComponent = equipmentComponentRepository.saveAndFlush(equipmentComponent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipmentComponent using partial update
        EquipmentComponent partialUpdatedEquipmentComponent = new EquipmentComponent();
        partialUpdatedEquipmentComponent.setId(equipmentComponent.getId());

        partialUpdatedEquipmentComponent.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restEquipmentComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipmentComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipmentComponent))
            )
            .andExpect(status().isOk());

        // Validate the EquipmentComponent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipmentComponentUpdatableFieldsEquals(
            partialUpdatedEquipmentComponent,
            getPersistedEquipmentComponent(partialUpdatedEquipmentComponent)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEquipmentComponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponent.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipmentComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipmentComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipmentComponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponent.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipmentComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipmentComponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipmentComponent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipmentComponent.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipmentComponentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(equipmentComponent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipmentComponent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipmentComponent() throws Exception {
        // Initialize the database
        insertedEquipmentComponent = equipmentComponentRepository.saveAndFlush(equipmentComponent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the equipmentComponent
        restEquipmentComponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipmentComponent.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return equipmentComponentRepository.count();
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

    protected EquipmentComponent getPersistedEquipmentComponent(EquipmentComponent equipmentComponent) {
        return equipmentComponentRepository.findById(equipmentComponent.getId()).orElseThrow();
    }

    protected void assertPersistedEquipmentComponentToMatchAllProperties(EquipmentComponent expectedEquipmentComponent) {
        assertEquipmentComponentAllPropertiesEquals(expectedEquipmentComponent, getPersistedEquipmentComponent(expectedEquipmentComponent));
    }

    protected void assertPersistedEquipmentComponentToMatchUpdatableProperties(EquipmentComponent expectedEquipmentComponent) {
        assertEquipmentComponentAllUpdatablePropertiesEquals(
            expectedEquipmentComponent,
            getPersistedEquipmentComponent(expectedEquipmentComponent)
        );
    }
}
