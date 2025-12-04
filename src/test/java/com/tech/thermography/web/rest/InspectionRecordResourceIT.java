package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.InspectionRecordAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.InspectionRecord;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.repository.InspectionRecordRepository;
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
 * Integration tests for the {@link InspectionRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspectionRecordResourceIT {

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

    private static final String ENTITY_API_URL = "/api/inspection-records";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InspectionRecordRepository inspectionRecordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectionRecordMockMvc;

    private InspectionRecord inspectionRecord;

    private InspectionRecord insertedInspectionRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRecord createEntity(EntityManager em) {
        InspectionRecord inspectionRecord = new InspectionRecord()
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
        // Add required entity
        Plant plant;
        if (TestUtil.findAll(em, Plant.class).isEmpty()) {
            plant = PlantResourceIT.createEntity();
            em.persist(plant);
            em.flush();
        } else {
            plant = TestUtil.findAll(em, Plant.class).get(0);
        }
        inspectionRecord.setPlant(plant);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        inspectionRecord.setCreatedBy(userInfo);
        return inspectionRecord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionRecord createUpdatedEntity(EntityManager em) {
        InspectionRecord updatedInspectionRecord = new InspectionRecord()
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
        // Add required entity
        Plant plant;
        if (TestUtil.findAll(em, Plant.class).isEmpty()) {
            plant = PlantResourceIT.createUpdatedEntity();
            em.persist(plant);
            em.flush();
        } else {
            plant = TestUtil.findAll(em, Plant.class).get(0);
        }
        updatedInspectionRecord.setPlant(plant);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createUpdatedEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        updatedInspectionRecord.setCreatedBy(userInfo);
        return updatedInspectionRecord;
    }

    @BeforeEach
    void initTest() {
        inspectionRecord = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedInspectionRecord != null) {
            inspectionRecordRepository.delete(insertedInspectionRecord);
            insertedInspectionRecord = null;
        }
    }

    @Test
    @Transactional
    void createInspectionRecord() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InspectionRecord
        var returnedInspectionRecord = om.readValue(
            restInspectionRecordMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecord)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InspectionRecord.class
        );

        // Validate the InspectionRecord in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInspectionRecordUpdatableFieldsEquals(returnedInspectionRecord, getPersistedInspectionRecord(returnedInspectionRecord));

        insertedInspectionRecord = returnedInspectionRecord;
    }

    @Test
    @Transactional
    void createInspectionRecordWithExistingId() throws Exception {
        // Create the InspectionRecord with an existing ID
        insertedInspectionRecord = inspectionRecordRepository.saveAndFlush(inspectionRecord);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecord)))
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRecord.setName(null);

        // Create the InspectionRecord, which fails.

        restInspectionRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRecord.setCreatedAt(null);

        // Create the InspectionRecord, which fails.

        restInspectionRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpectedStartDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRecord.setExpectedStartDate(null);

        // Create the InspectionRecord, which fails.

        restInspectionRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpectedEndDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inspectionRecord.setExpectedEndDate(null);

        // Create the InspectionRecord, which fails.

        restInspectionRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInspectionRecords() throws Exception {
        // Initialize the database
        insertedInspectionRecord = inspectionRecordRepository.saveAndFlush(inspectionRecord);

        // Get all the inspectionRecordList
        restInspectionRecordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionRecord.getId().toString())))
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
    void getInspectionRecord() throws Exception {
        // Initialize the database
        insertedInspectionRecord = inspectionRecordRepository.saveAndFlush(inspectionRecord);

        // Get the inspectionRecord
        restInspectionRecordMockMvc
            .perform(get(ENTITY_API_URL_ID, inspectionRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionRecord.getId().toString()))
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
    void getNonExistingInspectionRecord() throws Exception {
        // Get the inspectionRecord
        restInspectionRecordMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInspectionRecord() throws Exception {
        // Initialize the database
        insertedInspectionRecord = inspectionRecordRepository.saveAndFlush(inspectionRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRecord
        InspectionRecord updatedInspectionRecord = inspectionRecordRepository.findById(inspectionRecord.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInspectionRecord are not directly saved in db
        em.detach(updatedInspectionRecord);
        updatedInspectionRecord
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

        restInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInspectionRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInspectionRecord))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInspectionRecordToMatchAllProperties(updatedInspectionRecord);
    }

    @Test
    @Transactional
    void putNonExistingInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecord.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspectionRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inspectionRecord)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspectionRecordWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRecord = inspectionRecordRepository.saveAndFlush(inspectionRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRecord using partial update
        InspectionRecord partialUpdatedInspectionRecord = new InspectionRecord();
        partialUpdatedInspectionRecord.setId(inspectionRecord.getId());

        partialUpdatedInspectionRecord
            .code(UPDATED_CODE)
            .maintenanceDocument(UPDATED_MAINTENANCE_DOCUMENT)
            .expectedStartDate(UPDATED_EXPECTED_START_DATE)
            .started(UPDATED_STARTED)
            .finished(UPDATED_FINISHED);

        restInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRecord))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRecordUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInspectionRecord, inspectionRecord),
            getPersistedInspectionRecord(inspectionRecord)
        );
    }

    @Test
    @Transactional
    void fullUpdateInspectionRecordWithPatch() throws Exception {
        // Initialize the database
        insertedInspectionRecord = inspectionRecordRepository.saveAndFlush(inspectionRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inspectionRecord using partial update
        InspectionRecord partialUpdatedInspectionRecord = new InspectionRecord();
        partialUpdatedInspectionRecord.setId(inspectionRecord.getId());

        partialUpdatedInspectionRecord
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

        restInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspectionRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInspectionRecord))
            )
            .andExpect(status().isOk());

        // Validate the InspectionRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInspectionRecordUpdatableFieldsEquals(
            partialUpdatedInspectionRecord,
            getPersistedInspectionRecord(partialUpdatedInspectionRecord)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecord.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspectionRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the InspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inspectionRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspectionRecordMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inspectionRecord)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspectionRecord() throws Exception {
        // Initialize the database
        insertedInspectionRecord = inspectionRecordRepository.saveAndFlush(inspectionRecord);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inspectionRecord
        restInspectionRecordMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspectionRecord.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inspectionRecordRepository.count();
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

    protected InspectionRecord getPersistedInspectionRecord(InspectionRecord inspectionRecord) {
        return inspectionRecordRepository.findById(inspectionRecord.getId()).orElseThrow();
    }

    protected void assertPersistedInspectionRecordToMatchAllProperties(InspectionRecord expectedInspectionRecord) {
        assertInspectionRecordAllPropertiesEquals(expectedInspectionRecord, getPersistedInspectionRecord(expectedInspectionRecord));
    }

    protected void assertPersistedInspectionRecordToMatchUpdatableProperties(InspectionRecord expectedInspectionRecord) {
        assertInspectionRecordAllUpdatablePropertiesEquals(
            expectedInspectionRecord,
            getPersistedInspectionRecord(expectedInspectionRecord)
        );
    }
}
