package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.InspectionRouteGroupEquipmentAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.InspectionRouteGroupEquipment;
import com.tech.thermography.repository.InspectionRouteGroupEquipmentRepository;
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
 * Integration tests for the {@link InspectionRouteGroupEquipmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspectionRouteGroupEquipmentResourceIT {

    private static final Boolean DEFAULT_INCLUDED = false;
    private static final Boolean UPDATED_INCLUDED = true;

    private static final Integer DEFAULT_ORDER_INDEX = 1;
    private static final Integer UPDATED_ORDER_INDEX = 2;

    private static final String ENTITY_API_URL = "/api/inspection-route-group-equipments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InspectionRouteGroupEquipmentRepository inspectionRouteGroupEquipmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectionRouteGroupEquipmentMockMvc;

    private InspectionRouteGroupEquipment inspectionRouteGroupEquipment;

    private InspectionRouteGroupEquipment insertedInspectionRouteGroupEquipment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRouteGroupEquipment createEntity() {
        return new InspectionRouteGroupEquipment().included(DEFAULT_INCLUDED).orderIndex(DEFAULT_ORDER_INDEX);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRouteGroupEquipment createUpdatedEntity() {
        return new InspectionRouteGroupEquipment().included(UPDATED_INCLUDED).orderIndex(UPDATED_ORDER_INDEX);
    }

    @BeforeEach
    void initTest() {
        inspectionRouteGroupEquipment = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInspectionRouteGroupEquipment != null) {
            inspectionRouteGroupEquipmentRepository.delete(insertedInspectionRouteGroupEquipment);
            insertedInspectionRouteGroupEquipment = null;
        }
    }

    @Test
    @Transactional
    void createInspectionRouteGroupEquipment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InspectionRouteGroupEquipment
        var returnedInspectionRouteGroupEquipment = om.readValue(
            restInspectionRouteGroupEquipmentMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(inspectionRouteGroupEquipment))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InspectionRouteGroupEquipment.class
        );

        // Validate the InspectionRouteGroupEquipment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInspectionRouteGroupEquipmentUpdatableFieldsEquals(
            returnedInspectionRouteGroupEquipment,
            getPersistedInspectionRouteGroupEquipment(returnedInspectionRouteGroupEquipment)
        );

        insertedInspectionRouteGroupEquipment = returnedInspectionRouteGroupEquipment;
    }

    @Test
    @Transactional
    void createInspectionRouteGroupEquipmentWithExistingId() throws Exception {
        // Create the InspectionRouteGroupEquipment with an existing ID
        insertedInspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.saveAndFlush(inspectionRouteGroupEquipment);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInspectionRouteGroupEquipments() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.saveAndFlush(inspectionRouteGroupEquipment);

        // Get all the inspectionRouteGroupEquipmentList
        restInspectionRouteGroupEquipmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionRouteGroupEquipment.getId().toString())))
            .andExpect(jsonPath("$.[*].included").value(hasItem(DEFAULT_INCLUDED)))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX)));
    }

    @Test
    @Transactional
    void getInspectionRouteGroupEquipment() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.saveAndFlush(inspectionRouteGroupEquipment);

        // Get the inspectionRouteGroupEquipment
        restInspectionRouteGroupEquipmentMockMvc
            .perform(get(ENTITY_API_URL_ID, inspectionRouteGroupEquipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionRouteGroupEquipment.getId().toString()))
            .andExpect(jsonPath("$.included").value(DEFAULT_INCLUDED))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX));
    }

    @Test
    @Transactional
    void getNonExistingInspectionRouteGroupEquipment() throws Exception {
        // Get the inspectionRouteGroupEquipment
        restInspectionRouteGroupEquipmentMockMvc
            .perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInspectionRouteGroupEquipment() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.saveAndFlush(inspectionRouteGroupEquipment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRouteGroupEquipment
        InspectionRouteGroupEquipment updatedInspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository
            .findById(inspectionRouteGroupEquipment.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInspectionRouteGroupEquipment are not directly saved in db
        em.detach(updatedInspectionRouteGroupEquipment);
        updatedInspectionRouteGroupEquipment.included(UPDATED_INCLUDED).orderIndex(UPDATED_ORDER_INDEX);

        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspectionRouteGroupEquipment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInspectionRouteGroupEquipment))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRouteGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInspectionRouteGroupEquipmentToMatchAllProperties(updatedInspectionRouteGroupEquipment);
    }

    @Test
    @Transactional
    void putNonExistingInspectionRouteGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroupEquipment.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspectionRouteGroupEquipment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRouteGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspectionRouteGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroupEquipment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRouteGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspectionRouteGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroupEquipment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteGroupEquipment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRouteGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectionRouteGroupEquipmentWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.saveAndFlush(inspectionRouteGroupEquipment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRouteGroupEquipment using partial update
        InspectionRouteGroupEquipment partialUpdatedInspectionRouteGroupEquipment = new InspectionRouteGroupEquipment();
        partialUpdatedInspectionRouteGroupEquipment.setId(inspectionRouteGroupEquipment.getId());

        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRouteGroupEquipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRouteGroupEquipment))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRouteGroupEquipment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRouteGroupEquipmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInspectionRouteGroupEquipment, inspectionRouteGroupEquipment),
            getPersistedInspectionRouteGroupEquipment(inspectionRouteGroupEquipment)
        );
    }

    @Test
    @Transactional
    void fullUpdateInspectionRouteGroupEquipmentWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.saveAndFlush(inspectionRouteGroupEquipment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRouteGroupEquipment using partial update
        InspectionRouteGroupEquipment partialUpdatedInspectionRouteGroupEquipment = new InspectionRouteGroupEquipment();
        partialUpdatedInspectionRouteGroupEquipment.setId(inspectionRouteGroupEquipment.getId());

        partialUpdatedInspectionRouteGroupEquipment.included(UPDATED_INCLUDED).orderIndex(UPDATED_ORDER_INDEX);

        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRouteGroupEquipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRouteGroupEquipment))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRouteGroupEquipment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRouteGroupEquipmentUpdatableFieldsEquals(
            partialUpdatedInspectionRouteGroupEquipment,
            getPersistedInspectionRouteGroupEquipment(partialUpdatedInspectionRouteGroupEquipment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInspectionRouteGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroupEquipment.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspectionRouteGroupEquipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRouteGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspectionRouteGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroupEquipment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRouteGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspectionRouteGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteGroupEquipment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRouteGroupEquipment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRouteGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspectionRouteGroupEquipment() throws Exception {
        // Initialize the database
        insertedInspectionRouteGroupEquipment = inspectionRouteGroupEquipmentRepository.saveAndFlush(inspectionRouteGroupEquipment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inspectionRouteGroupEquipment
        restInspectionRouteGroupEquipmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspectionRouteGroupEquipment.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inspectionRouteGroupEquipmentRepository.count();
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

    protected InspectionRouteGroupEquipment getPersistedInspectionRouteGroupEquipment(
        InspectionRouteGroupEquipment inspectionRouteGroupEquipment
    ) {
        return inspectionRouteGroupEquipmentRepository.findById(inspectionRouteGroupEquipment.getId()).orElseThrow();
    }

    protected void assertPersistedInspectionRouteGroupEquipmentToMatchAllProperties(
        InspectionRouteGroupEquipment expectedInspectionRouteGroupEquipment
    ) {
        assertInspectionRouteGroupEquipmentAllPropertiesEquals(
            expectedInspectionRouteGroupEquipment,
            getPersistedInspectionRouteGroupEquipment(expectedInspectionRouteGroupEquipment)
        );
    }

    protected void assertPersistedInspectionRouteGroupEquipmentToMatchUpdatableProperties(
        InspectionRouteGroupEquipment expectedInspectionRouteGroupEquipment
    ) {
        assertInspectionRouteGroupEquipmentAllUpdatablePropertiesEquals(
            expectedInspectionRouteGroupEquipment,
            getPersistedInspectionRouteGroupEquipment(expectedInspectionRouteGroupEquipment)
        );
    }
}
