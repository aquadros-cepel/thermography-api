package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.InspectionRouteRecordAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.InspectionRouteRecord;
import com.tech.thermography.repository.InspectionRouteRecordRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link InspectionRouteRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspectionRouteRecordResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MAINTENANCE_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_MAINTENANCE_DOCUMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_EXPECTED_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPECTED_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STARTED = false;
    private static final Boolean UPDATED_STARTED = true;

    private static final Instant DEFAULT_STARTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_FINISHED = false;
    private static final Boolean UPDATED_FINISHED = true;

    private static final Instant DEFAULT_FINISHED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISHED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/inspection-route-records";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InspectionRouteRecordRepository inspectionRouteRecordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectionRouteRecordMockMvc;

    private InspectionRouteRecord inspectionRouteRecord;

    private InspectionRouteRecord insertedInspectionRouteRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRouteRecord createEntity() {
        return new InspectionRouteRecord()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .maintenanceDocument(DEFAULT_MAINTENANCE_DOCUMENT)
            .createdAt(DEFAULT_CREATED_AT)
            .expectedStartDate(DEFAULT_EXPECTED_START_DATE)
            .expectedEndDate(DEFAULT_EXPECTED_END_DATE)
            .started(DEFAULT_STARTED)
            .startedAt(DEFAULT_STARTED_AT)
            .finished(DEFAULT_FINISHED)
            .finishedAt(DEFAULT_FINISHED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRouteRecord createUpdatedEntity() {
        return new InspectionRouteRecord()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .maintenanceDocument(UPDATED_MAINTENANCE_DOCUMENT)
            .createdAt(UPDATED_CREATED_AT)
            .expectedStartDate(UPDATED_EXPECTED_START_DATE)
            .expectedEndDate(UPDATED_EXPECTED_END_DATE)
            .started(UPDATED_STARTED)
            .startedAt(UPDATED_STARTED_AT)
            .finished(UPDATED_FINISHED)
            .finishedAt(UPDATED_FINISHED_AT);
    }

    @BeforeEach
    void initTest() {
        inspectionRouteRecord = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInspectionRouteRecord != null) {
            inspectionRouteRecordRepository.delete(insertedInspectionRouteRecord);
            insertedInspectionRouteRecord = null;
        }
    }

    @Test
    @Transactional
    void createInspectionRouteRecord() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InspectionRouteRecord
        var returnedInspectionRouteRecord = om.readValue(
            restInspectionRouteRecordMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteRecord)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InspectionRouteRecord.class
        );

        // Validate the InspectionRouteRecord in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInspectionRouteRecordUpdatableFieldsEquals(
            returnedInspectionRouteRecord,
            getPersistedInspectionRouteRecord(returnedInspectionRouteRecord)
        );

        insertedInspectionRouteRecord = returnedInspectionRouteRecord;
    }

    @Test
    @Transactional
    void createInspectionRouteRecordWithExistingId() throws Exception {
        // Create the InspectionRouteRecord with an existing ID
        insertedInspectionRouteRecord = inspectionRouteRecordRepository.saveAndFlush(inspectionRouteRecord);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionRouteRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteRecord)))
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRouteRecord.setName(null);

        // Create the InspectionRouteRecord, which fails.

        restInspectionRouteRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRouteRecord.setCreatedAt(null);

        // Create the InspectionRouteRecord, which fails.

        restInspectionRouteRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpectedStartDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRouteRecord.setExpectedStartDate(null);

        // Create the InspectionRouteRecord, which fails.

        restInspectionRouteRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpectedEndDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRouteRecord.setExpectedEndDate(null);

        // Create the InspectionRouteRecord, which fails.

        restInspectionRouteRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInspectionRouteRecords() throws Exception {
        // Initialize the database
        insertedInspectionRouteRecord = inspectionRouteRecordRepository.saveAndFlush(inspectionRouteRecord);

        // Get all the inspectionRouteRecordList
        restInspectionRouteRecordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionRouteRecord.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].maintenanceDocument").value(hasItem(DEFAULT_MAINTENANCE_DOCUMENT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].expectedStartDate").value(hasItem(DEFAULT_EXPECTED_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedEndDate").value(hasItem(DEFAULT_EXPECTED_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].started").value(hasItem(DEFAULT_STARTED)))
            .andExpect(jsonPath("$.[*].startedAt").value(hasItem(DEFAULT_STARTED_AT.toString())))
            .andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED)))
            .andExpect(jsonPath("$.[*].finishedAt").value(hasItem(DEFAULT_FINISHED_AT.toString())));
    }

    @Test
    @Transactional
    void getInspectionRouteRecord() throws Exception {
        // Initialize the database
        insertedInspectionRouteRecord = inspectionRouteRecordRepository.saveAndFlush(inspectionRouteRecord);

        // Get the inspectionRouteRecord
        restInspectionRouteRecordMockMvc
            .perform(get(ENTITY_API_URL_ID, inspectionRouteRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionRouteRecord.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.maintenanceDocument").value(DEFAULT_MAINTENANCE_DOCUMENT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.expectedStartDate").value(DEFAULT_EXPECTED_START_DATE.toString()))
            .andExpect(jsonPath("$.expectedEndDate").value(DEFAULT_EXPECTED_END_DATE.toString()))
            .andExpect(jsonPath("$.started").value(DEFAULT_STARTED))
            .andExpect(jsonPath("$.startedAt").value(DEFAULT_STARTED_AT.toString()))
            .andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED))
            .andExpect(jsonPath("$.finishedAt").value(DEFAULT_FINISHED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInspectionRouteRecord() throws Exception {
        // Get the inspectionRouteRecord
        restInspectionRouteRecordMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInspectionRouteRecord() throws Exception {
        // Initialize the database
        insertedInspectionRouteRecord = inspectionRouteRecordRepository.saveAndFlush(inspectionRouteRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRouteRecord
        InspectionRouteRecord updatedInspectionRouteRecord = inspectionRouteRecordRepository
            .findById(inspectionRouteRecord.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInspectionRouteRecord are not directly saved in db
        em.detach(updatedInspectionRouteRecord);
        updatedInspectionRouteRecord
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .maintenanceDocument(UPDATED_MAINTENANCE_DOCUMENT)
            .createdAt(UPDATED_CREATED_AT)
            .expectedStartDate(UPDATED_EXPECTED_START_DATE)
            .expectedEndDate(UPDATED_EXPECTED_END_DATE)
            .started(UPDATED_STARTED)
            .startedAt(UPDATED_STARTED_AT)
            .finished(UPDATED_FINISHED)
            .finishedAt(UPDATED_FINISHED_AT);

        restInspectionRouteRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspectionRouteRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInspectionRouteRecord))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRouteRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInspectionRouteRecordToMatchAllProperties(updatedInspectionRouteRecord);
    }

    @Test
    @Transactional
    void putNonExistingInspectionRouteRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteRecord.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRouteRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspectionRouteRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRouteRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspectionRouteRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRouteRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspectionRouteRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteRecordMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRouteRecord)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRouteRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectionRouteRecordWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRouteRecord = inspectionRouteRecordRepository.saveAndFlush(inspectionRouteRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRouteRecord using partial update
        InspectionRouteRecord partialUpdatedInspectionRouteRecord = new InspectionRouteRecord();
        partialUpdatedInspectionRouteRecord.setId(inspectionRouteRecord.getId());

        partialUpdatedInspectionRouteRecord
            .description(UPDATED_DESCRIPTION)
            .maintenanceDocument(UPDATED_MAINTENANCE_DOCUMENT)
            .expectedEndDate(UPDATED_EXPECTED_END_DATE)
            .startedAt(UPDATED_STARTED_AT)
            .finishedAt(UPDATED_FINISHED_AT);

        restInspectionRouteRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRouteRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRouteRecord))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRouteRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRouteRecordUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInspectionRouteRecord, inspectionRouteRecord),
            getPersistedInspectionRouteRecord(inspectionRouteRecord)
        );
    }

    @Test
    @Transactional
    void fullUpdateInspectionRouteRecordWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRouteRecord = inspectionRouteRecordRepository.saveAndFlush(inspectionRouteRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRouteRecord using partial update
        InspectionRouteRecord partialUpdatedInspectionRouteRecord = new InspectionRouteRecord();
        partialUpdatedInspectionRouteRecord.setId(inspectionRouteRecord.getId());

        partialUpdatedInspectionRouteRecord
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .maintenanceDocument(UPDATED_MAINTENANCE_DOCUMENT)
            .createdAt(UPDATED_CREATED_AT)
            .expectedStartDate(UPDATED_EXPECTED_START_DATE)
            .expectedEndDate(UPDATED_EXPECTED_END_DATE)
            .started(UPDATED_STARTED)
            .startedAt(UPDATED_STARTED_AT)
            .finished(UPDATED_FINISHED)
            .finishedAt(UPDATED_FINISHED_AT);

        restInspectionRouteRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRouteRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRouteRecord))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRouteRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRouteRecordUpdatableFieldsEquals(
            partialUpdatedInspectionRouteRecord,
            getPersistedInspectionRouteRecord(partialUpdatedInspectionRouteRecord)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInspectionRouteRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteRecord.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRouteRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspectionRouteRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRouteRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspectionRouteRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRouteRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRouteRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspectionRouteRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRouteRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRouteRecordMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inspectionRouteRecord)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRouteRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspectionRouteRecord() throws Exception {
        // Initialize the database
        insertedInspectionRouteRecord = inspectionRouteRecordRepository.saveAndFlush(inspectionRouteRecord);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inspectionRouteRecord
        restInspectionRouteRecordMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspectionRouteRecord.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inspectionRouteRecordRepository.count();
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

    protected InspectionRouteRecord getPersistedInspectionRouteRecord(InspectionRouteRecord inspectionRouteRecord) {
        return inspectionRouteRecordRepository.findById(inspectionRouteRecord.getId()).orElseThrow();
    }

    protected void assertPersistedInspectionRouteRecordToMatchAllProperties(InspectionRouteRecord expectedInspectionRouteRecord) {
        assertInspectionRouteRecordAllPropertiesEquals(
            expectedInspectionRouteRecord,
            getPersistedInspectionRouteRecord(expectedInspectionRouteRecord)
        );
    }

    protected void assertPersistedInspectionRouteRecordToMatchUpdatableProperties(InspectionRouteRecord expectedInspectionRouteRecord) {
        assertInspectionRouteRecordAllUpdatablePropertiesEquals(
            expectedInspectionRouteRecord,
            getPersistedInspectionRouteRecord(expectedInspectionRouteRecord)
        );
    }
}
