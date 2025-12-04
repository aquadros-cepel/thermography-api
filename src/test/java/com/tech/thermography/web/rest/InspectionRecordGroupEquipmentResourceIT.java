package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.InspectionRecordGroupEquipmentAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.InspectionRecordGroupEquipment;
import com.tech.thermography.domain.enumeration.EquipmentInspectionStatus;
import com.tech.thermography.repository.InspectionRecordGroupEquipmentRepository;
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
 * Integration tests for the {@link InspectionRecordGroupEquipmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspectionRecordGroupEquipmentResourceIT {

    private static final Integer DEFAULT_ORDER_INDEX = 1;
    private static final Integer UPDATED_ORDER_INDEX = 2;

    private static final EquipmentInspectionStatus DEFAULT_STATUS = EquipmentInspectionStatus.INSPECTED;
    private static final EquipmentInspectionStatus UPDATED_STATUS = EquipmentInspectionStatus.NOT_INSPECTED;

    private static final String ENTITY_API_URL = "/api/inspection-record-group-equipments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InspectionRecordGroupEquipmentRepository inspectionRecordGroupEquipmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectionRecordGroupEquipmentMockMvc;

    private InspectionRecordGroupEquipment inspectionRecordGroupEquipment;

    private InspectionRecordGroupEquipment insertedInspectionRecordGroupEquipment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRecordGroupEquipment createEntity() {
        return new InspectionRecordGroupEquipment().orderIndex(DEFAULT_ORDER_INDEX).status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRecordGroupEquipment createUpdatedEntity() {
        return new InspectionRecordGroupEquipment().orderIndex(UPDATED_ORDER_INDEX).status(UPDATED_STATUS);
    }

    @BeforeEach
    void initTest() {
        inspectionRecordGroupEquipment = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInspectionRecordGroupEquipment != null) {
            inspectionRecordGroupEquipmentRepository.delete(insertedInspectionRecordGroupEquipment);
            insertedInspectionRecordGroupEquipment = null;
        }
    }

    @Test
    @Transactional
    void createInspectionRecordGroupEquipment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InspectionRecordGroupEquipment
        var returnedInspectionRecordGroupEquipment = om.readValue(
            restInspectionRecordGroupEquipmentMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(inspectionRecordGroupEquipment))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InspectionRecordGroupEquipment.class
        );

        // Validate the InspectionRecordGroupEquipment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInspectionRecordGroupEquipmentUpdatableFieldsEquals(
            returnedInspectionRecordGroupEquipment,
            getPersistedInspectionRecordGroupEquipment(returnedInspectionRecordGroupEquipment)
        );

        insertedInspectionRecordGroupEquipment = returnedInspectionRecordGroupEquipment;
    }

    @Test
    @Transactional
    void createInspectionRecordGroupEquipmentWithExistingId() throws Exception {
        // Create the InspectionRecordGroupEquipment with an existing ID
        insertedInspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.saveAndFlush(inspectionRecordGroupEquipment);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecordGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInspectionRecordGroupEquipments() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.saveAndFlush(inspectionRecordGroupEquipment);

        // Get all the inspectionRecordGroupEquipmentList
        restInspectionRecordGroupEquipmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionRecordGroupEquipment.getId().toString())))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getInspectionRecordGroupEquipment() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.saveAndFlush(inspectionRecordGroupEquipment);

        // Get the inspectionRecordGroupEquipment
        restInspectionRecordGroupEquipmentMockMvc
            .perform(get(ENTITY_API_URL_ID, inspectionRecordGroupEquipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionRecordGroupEquipment.getId().toString()))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInspectionRecordGroupEquipment() throws Exception {
        // Get the inspectionRecordGroupEquipment
        restInspectionRecordGroupEquipmentMockMvc
            .perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInspectionRecordGroupEquipment() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.saveAndFlush(inspectionRecordGroupEquipment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRecordGroupEquipment
        InspectionRecordGroupEquipment updatedInspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository
            .findById(inspectionRecordGroupEquipment.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInspectionRecordGroupEquipment are not directly saved in db
        em.detach(updatedInspectionRecordGroupEquipment);
        updatedInspectionRecordGroupEquipment.orderIndex(UPDATED_ORDER_INDEX).status(UPDATED_STATUS);

        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspectionRecordGroupEquipment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInspectionRecordGroupEquipment))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecordGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInspectionRecordGroupEquipmentToMatchAllProperties(updatedInspectionRecordGroupEquipment);
    }

    @Test
    @Transactional
    void putNonExistingInspectionRecordGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroupEquipment.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspectionRecordGroupEquipment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRecordGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspectionRecordGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroupEquipment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRecordGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspectionRecordGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroupEquipment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecordGroupEquipment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRecordGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectionRecordGroupEquipmentWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.saveAndFlush(inspectionRecordGroupEquipment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRecordGroupEquipment using partial update
        InspectionRecordGroupEquipment partialUpdatedInspectionRecordGroupEquipment = new InspectionRecordGroupEquipment();
        partialUpdatedInspectionRecordGroupEquipment.setId(inspectionRecordGroupEquipment.getId());

        partialUpdatedInspectionRecordGroupEquipment.orderIndex(UPDATED_ORDER_INDEX);

        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRecordGroupEquipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRecordGroupEquipment))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecordGroupEquipment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRecordGroupEquipmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInspectionRecordGroupEquipment, inspectionRecordGroupEquipment),
            getPersistedInspectionRecordGroupEquipment(inspectionRecordGroupEquipment)
        );
    }

    @Test
    @Transactional
    void fullUpdateInspectionRecordGroupEquipmentWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.saveAndFlush(inspectionRecordGroupEquipment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRecordGroupEquipment using partial update
        InspectionRecordGroupEquipment partialUpdatedInspectionRecordGroupEquipment = new InspectionRecordGroupEquipment();
        partialUpdatedInspectionRecordGroupEquipment.setId(inspectionRecordGroupEquipment.getId());

        partialUpdatedInspectionRecordGroupEquipment.orderIndex(UPDATED_ORDER_INDEX).status(UPDATED_STATUS);

        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRecordGroupEquipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRecordGroupEquipment))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecordGroupEquipment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRecordGroupEquipmentUpdatableFieldsEquals(
            partialUpdatedInspectionRecordGroupEquipment,
            getPersistedInspectionRecordGroupEquipment(partialUpdatedInspectionRecordGroupEquipment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInspectionRecordGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroupEquipment.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspectionRecordGroupEquipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRecordGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspectionRecordGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroupEquipment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRecordGroupEquipment))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecordGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspectionRecordGroupEquipment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecordGroupEquipment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordGroupEquipmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRecordGroupEquipment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRecordGroupEquipment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspectionRecordGroupEquipment() throws Exception {
        // Initialize the database
        insertedInspectionRecordGroupEquipment = inspectionRecordGroupEquipmentRepository.saveAndFlush(inspectionRecordGroupEquipment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inspectionRecordGroupEquipment
        restInspectionRecordGroupEquipmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspectionRecordGroupEquipment.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inspectionRecordGroupEquipmentRepository.count();
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

    protected InspectionRecordGroupEquipment getPersistedInspectionRecordGroupEquipment(
        InspectionRecordGroupEquipment inspectionRecordGroupEquipment
    ) {
        return inspectionRecordGroupEquipmentRepository.findById(inspectionRecordGroupEquipment.getId()).orElseThrow();
    }

    protected void assertPersistedInspectionRecordGroupEquipmentToMatchAllProperties(
        InspectionRecordGroupEquipment expectedInspectionRecordGroupEquipment
    ) {
        assertInspectionRecordGroupEquipmentAllPropertiesEquals(
            expectedInspectionRecordGroupEquipment,
            getPersistedInspectionRecordGroupEquipment(expectedInspectionRecordGroupEquipment)
        );
    }

    protected void assertPersistedInspectionRecordGroupEquipmentToMatchUpdatableProperties(
        InspectionRecordGroupEquipment expectedInspectionRecordGroupEquipment
    ) {
        assertInspectionRecordGroupEquipmentAllUpdatablePropertiesEquals(
            expectedInspectionRecordGroupEquipment,
            getPersistedInspectionRecordGroupEquipment(expectedInspectionRecordGroupEquipment)
        );
    }
}
