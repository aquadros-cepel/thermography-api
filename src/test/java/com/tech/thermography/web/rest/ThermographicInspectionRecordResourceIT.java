package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.ThermographicInspectionRecordAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.Thermogram;
import com.tech.thermography.domain.ThermographicInspectionRecord;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.domain.enumeration.ConditionType;
import com.tech.thermography.domain.enumeration.ThermographicInspectionRecordType;
import com.tech.thermography.repository.ThermographicInspectionRecordRepository;
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
 * Integration tests for the {@link ThermographicInspectionRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThermographicInspectionRecordResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ThermographicInspectionRecordType DEFAULT_TYPE = ThermographicInspectionRecordType.NO_ANOMALY;
    private static final ThermographicInspectionRecordType UPDATED_TYPE = ThermographicInspectionRecordType.ANOMALY_INITIAL;

    private static final String DEFAULT_SERVICE_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_ORDER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ANALYSIS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS_DESCRIPTION = "BBBBBBBBBB";

    private static final ConditionType DEFAULT_CONDITION = ConditionType.NORMAL;
    private static final ConditionType UPDATED_CONDITION = ConditionType.LOW_RISK;

    private static final Double DEFAULT_DELTA_T = 1D;
    private static final Double UPDATED_DELTA_T = 2D;

    private static final Integer DEFAULT_PERIODICITY = 1;
    private static final Integer UPDATED_PERIODICITY = 2;

    private static final LocalDate DEFAULT_DEADLINE_EXECUTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEADLINE_EXECUTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_NEXT_MONITORING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEXT_MONITORING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RECOMMENDATIONS = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMENDATIONS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FINISHED = false;
    private static final Boolean UPDATED_FINISHED = true;

    private static final Instant DEFAULT_FINISHED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISHED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/thermographic-inspection-records";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ThermographicInspectionRecordRepository thermographicInspectionRecordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThermographicInspectionRecordMockMvc;

    private ThermographicInspectionRecord thermographicInspectionRecord;

    private ThermographicInspectionRecord insertedThermographicInspectionRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThermographicInspectionRecord createEntity(EntityManager em) {
        ThermographicInspectionRecord thermographicInspectionRecord = new ThermographicInspectionRecord()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .serviceOrder(DEFAULT_SERVICE_ORDER)
            .createdAt(DEFAULT_CREATED_AT)
            .analysisDescription(DEFAULT_ANALYSIS_DESCRIPTION)
            .condition(DEFAULT_CONDITION)
            .deltaT(DEFAULT_DELTA_T)
            .periodicity(DEFAULT_PERIODICITY)
            .deadlineExecution(DEFAULT_DEADLINE_EXECUTION)
            .nextMonitoring(DEFAULT_NEXT_MONITORING)
            .recommendations(DEFAULT_RECOMMENDATIONS)
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
        thermographicInspectionRecord.setPlant(plant);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        thermographicInspectionRecord.setEquipment(equipment);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        thermographicInspectionRecord.setCreatedBy(userInfo);
        // Add required entity
        thermographicInspectionRecord.setFinishedBy(userInfo);
        // Add required entity
        Thermogram thermogram;
        if (TestUtil.findAll(em, Thermogram.class).isEmpty()) {
            thermogram = ThermogramResourceIT.createEntity(em);
            em.persist(thermogram);
            em.flush();
        } else {
            thermogram = TestUtil.findAll(em, Thermogram.class).get(0);
        }
        thermographicInspectionRecord.setThermogram(thermogram);
        return thermographicInspectionRecord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThermographicInspectionRecord createUpdatedEntity(EntityManager em) {
        ThermographicInspectionRecord updatedThermographicInspectionRecord = new ThermographicInspectionRecord()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .serviceOrder(UPDATED_SERVICE_ORDER)
            .createdAt(UPDATED_CREATED_AT)
            .analysisDescription(UPDATED_ANALYSIS_DESCRIPTION)
            .condition(UPDATED_CONDITION)
            .deltaT(UPDATED_DELTA_T)
            .periodicity(UPDATED_PERIODICITY)
            .deadlineExecution(UPDATED_DEADLINE_EXECUTION)
            .nextMonitoring(UPDATED_NEXT_MONITORING)
            .recommendations(UPDATED_RECOMMENDATIONS)
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
        updatedThermographicInspectionRecord.setPlant(plant);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createUpdatedEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        updatedThermographicInspectionRecord.setEquipment(equipment);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createUpdatedEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        updatedThermographicInspectionRecord.setCreatedBy(userInfo);
        // Add required entity
        updatedThermographicInspectionRecord.setFinishedBy(userInfo);
        // Add required entity
        Thermogram thermogram;
        if (TestUtil.findAll(em, Thermogram.class).isEmpty()) {
            thermogram = ThermogramResourceIT.createUpdatedEntity(em);
            em.persist(thermogram);
            em.flush();
        } else {
            thermogram = TestUtil.findAll(em, Thermogram.class).get(0);
        }
        updatedThermographicInspectionRecord.setThermogram(thermogram);
        return updatedThermographicInspectionRecord;
    }

    @BeforeEach
    void initTest() {
        thermographicInspectionRecord = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedThermographicInspectionRecord != null) {
            thermographicInspectionRecordRepository.delete(insertedThermographicInspectionRecord);
            insertedThermographicInspectionRecord = null;
        }
    }

    @Test
    @Transactional
    void createThermographicInspectionRecord() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ThermographicInspectionRecord
        var returnedThermographicInspectionRecord = om.readValue(
            restThermographicInspectionRecordMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(thermographicInspectionRecord))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ThermographicInspectionRecord.class
        );

        // Validate the ThermographicInspectionRecord in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertThermographicInspectionRecordUpdatableFieldsEquals(
            returnedThermographicInspectionRecord,
            getPersistedThermographicInspectionRecord(returnedThermographicInspectionRecord)
        );

        insertedThermographicInspectionRecord = returnedThermographicInspectionRecord;
    }

    @Test
    @Transactional
    void createThermographicInspectionRecordWithExistingId() throws Exception {
        // Create the ThermographicInspectionRecord with an existing ID
        insertedThermographicInspectionRecord = thermographicInspectionRecordRepository.saveAndFlush(thermographicInspectionRecord);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThermographicInspectionRecordMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThermographicInspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicInspectionRecord.setName(null);

        // Create the ThermographicInspectionRecord, which fails.

        restThermographicInspectionRecordMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicInspectionRecord.setType(null);

        // Create the ThermographicInspectionRecord, which fails.

        restThermographicInspectionRecordMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicInspectionRecord.setCreatedAt(null);

        // Create the ThermographicInspectionRecord, which fails.

        restThermographicInspectionRecordMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConditionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicInspectionRecord.setCondition(null);

        // Create the ThermographicInspectionRecord, which fails.

        restThermographicInspectionRecordMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeltaTIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicInspectionRecord.setDeltaT(null);

        // Create the ThermographicInspectionRecord, which fails.

        restThermographicInspectionRecordMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllThermographicInspectionRecords() throws Exception {
        // Initialize the database
        insertedThermographicInspectionRecord = thermographicInspectionRecordRepository.saveAndFlush(thermographicInspectionRecord);

        // Get all the thermographicInspectionRecordList
        restThermographicInspectionRecordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thermographicInspectionRecord.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].serviceOrder").value(hasItem(DEFAULT_SERVICE_ORDER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].analysisDescription").value(hasItem(DEFAULT_ANALYSIS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].deltaT").value(hasItem(DEFAULT_DELTA_T)))
            .andExpect(jsonPath("$.[*].periodicity").value(hasItem(DEFAULT_PERIODICITY)))
            .andExpect(jsonPath("$.[*].deadlineExecution").value(hasItem(DEFAULT_DEADLINE_EXECUTION.toString())))
            .andExpect(jsonPath("$.[*].nextMonitoring").value(hasItem(DEFAULT_NEXT_MONITORING.toString())))
            .andExpect(jsonPath("$.[*].recommendations").value(hasItem(DEFAULT_RECOMMENDATIONS)))
            .andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED)))
            .andExpect(jsonPath("$.[*].finishedAt").value(hasItem(DEFAULT_FINISHED_AT.toString())));
    }

    @Test
    @Transactional
    void getThermographicInspectionRecord() throws Exception {
        // Initialize the database
        insertedThermographicInspectionRecord = thermographicInspectionRecordRepository.saveAndFlush(thermographicInspectionRecord);

        // Get the thermographicInspectionRecord
        restThermographicInspectionRecordMockMvc
            .perform(get(ENTITY_API_URL_ID, thermographicInspectionRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thermographicInspectionRecord.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.serviceOrder").value(DEFAULT_SERVICE_ORDER))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.analysisDescription").value(DEFAULT_ANALYSIS_DESCRIPTION))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.toString()))
            .andExpect(jsonPath("$.deltaT").value(DEFAULT_DELTA_T))
            .andExpect(jsonPath("$.periodicity").value(DEFAULT_PERIODICITY))
            .andExpect(jsonPath("$.deadlineExecution").value(DEFAULT_DEADLINE_EXECUTION.toString()))
            .andExpect(jsonPath("$.nextMonitoring").value(DEFAULT_NEXT_MONITORING.toString()))
            .andExpect(jsonPath("$.recommendations").value(DEFAULT_RECOMMENDATIONS))
            .andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED))
            .andExpect(jsonPath("$.finishedAt").value(DEFAULT_FINISHED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingThermographicInspectionRecord() throws Exception {
        // Get the thermographicInspectionRecord
        restThermographicInspectionRecordMockMvc
            .perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingThermographicInspectionRecord() throws Exception {
        // Initialize the database
        insertedThermographicInspectionRecord = thermographicInspectionRecordRepository.saveAndFlush(thermographicInspectionRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thermographicInspectionRecord
        ThermographicInspectionRecord updatedThermographicInspectionRecord = thermographicInspectionRecordRepository
            .findById(thermographicInspectionRecord.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedThermographicInspectionRecord are not directly saved in db
        em.detach(updatedThermographicInspectionRecord);
        updatedThermographicInspectionRecord
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .serviceOrder(UPDATED_SERVICE_ORDER)
            .createdAt(UPDATED_CREATED_AT)
            .analysisDescription(UPDATED_ANALYSIS_DESCRIPTION)
            .condition(UPDATED_CONDITION)
            .deltaT(UPDATED_DELTA_T)
            .periodicity(UPDATED_PERIODICITY)
            .deadlineExecution(UPDATED_DEADLINE_EXECUTION)
            .nextMonitoring(UPDATED_NEXT_MONITORING)
            .recommendations(UPDATED_RECOMMENDATIONS)
            .finished(UPDATED_FINISHED)
            .finishedAt(UPDATED_FINISHED_AT);

        restThermographicInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThermographicInspectionRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedThermographicInspectionRecord))
            )
            .andExpect(status().isOk());

        // Validate the ThermographicInspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedThermographicInspectionRecordToMatchAllProperties(updatedThermographicInspectionRecord);
    }

    @Test
    @Transactional
    void putNonExistingThermographicInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicInspectionRecord.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThermographicInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thermographicInspectionRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThermographicInspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThermographicInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicInspectionRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermographicInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThermographicInspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThermographicInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicInspectionRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermographicInspectionRecordMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThermographicInspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThermographicInspectionRecordWithPatch() throws Exception {
        // Initialize the database
        insertedThermographicInspectionRecord = thermographicInspectionRecordRepository.saveAndFlush(thermographicInspectionRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thermographicInspectionRecord using partial update
        ThermographicInspectionRecord partialUpdatedThermographicInspectionRecord = new ThermographicInspectionRecord();
        partialUpdatedThermographicInspectionRecord.setId(thermographicInspectionRecord.getId());

        partialUpdatedThermographicInspectionRecord
            .name(UPDATED_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .deltaT(UPDATED_DELTA_T)
            .recommendations(UPDATED_RECOMMENDATIONS);

        restThermographicInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThermographicInspectionRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThermographicInspectionRecord))
            )
            .andExpect(status().isOk());

        // Validate the ThermographicInspectionRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThermographicInspectionRecordUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedThermographicInspectionRecord, thermographicInspectionRecord),
            getPersistedThermographicInspectionRecord(thermographicInspectionRecord)
        );
    }

    @Test
    @Transactional
    void fullUpdateThermographicInspectionRecordWithPatch() throws Exception {
        // Initialize the database
        insertedThermographicInspectionRecord = thermographicInspectionRecordRepository.saveAndFlush(thermographicInspectionRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thermographicInspectionRecord using partial update
        ThermographicInspectionRecord partialUpdatedThermographicInspectionRecord = new ThermographicInspectionRecord();
        partialUpdatedThermographicInspectionRecord.setId(thermographicInspectionRecord.getId());

        partialUpdatedThermographicInspectionRecord
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .serviceOrder(UPDATED_SERVICE_ORDER)
            .createdAt(UPDATED_CREATED_AT)
            .analysisDescription(UPDATED_ANALYSIS_DESCRIPTION)
            .condition(UPDATED_CONDITION)
            .deltaT(UPDATED_DELTA_T)
            .periodicity(UPDATED_PERIODICITY)
            .deadlineExecution(UPDATED_DEADLINE_EXECUTION)
            .nextMonitoring(UPDATED_NEXT_MONITORING)
            .recommendations(UPDATED_RECOMMENDATIONS)
            .finished(UPDATED_FINISHED)
            .finishedAt(UPDATED_FINISHED_AT);

        restThermographicInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThermographicInspectionRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThermographicInspectionRecord))
            )
            .andExpect(status().isOk());

        // Validate the ThermographicInspectionRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThermographicInspectionRecordUpdatableFieldsEquals(
            partialUpdatedThermographicInspectionRecord,
            getPersistedThermographicInspectionRecord(partialUpdatedThermographicInspectionRecord)
        );
    }

    @Test
    @Transactional
    void patchNonExistingThermographicInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicInspectionRecord.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThermographicInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thermographicInspectionRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThermographicInspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThermographicInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicInspectionRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermographicInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThermographicInspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThermographicInspectionRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicInspectionRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermographicInspectionRecordMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thermographicInspectionRecord))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThermographicInspectionRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThermographicInspectionRecord() throws Exception {
        // Initialize the database
        insertedThermographicInspectionRecord = thermographicInspectionRecordRepository.saveAndFlush(thermographicInspectionRecord);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the thermographicInspectionRecord
        restThermographicInspectionRecordMockMvc
            .perform(delete(ENTITY_API_URL_ID, thermographicInspectionRecord.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return thermographicInspectionRecordRepository.count();
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

    protected ThermographicInspectionRecord getPersistedThermographicInspectionRecord(
        ThermographicInspectionRecord thermographicInspectionRecord
    ) {
        return thermographicInspectionRecordRepository.findById(thermographicInspectionRecord.getId()).orElseThrow();
    }

    protected void assertPersistedThermographicInspectionRecordToMatchAllProperties(
        ThermographicInspectionRecord expectedThermographicInspectionRecord
    ) {
        assertThermographicInspectionRecordAllPropertiesEquals(
            expectedThermographicInspectionRecord,
            getPersistedThermographicInspectionRecord(expectedThermographicInspectionRecord)
        );
    }

    protected void assertPersistedThermographicInspectionRecordToMatchUpdatableProperties(
        ThermographicInspectionRecord expectedThermographicInspectionRecord
    ) {
        assertThermographicInspectionRecordAllUpdatablePropertiesEquals(
            expectedThermographicInspectionRecord,
            getPersistedThermographicInspectionRecord(expectedThermographicInspectionRecord)
        );
    }
}
