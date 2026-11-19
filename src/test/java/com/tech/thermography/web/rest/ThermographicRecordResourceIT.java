package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.ThermographicRecordAsserts.*;
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
import com.tech.thermography.domain.ThermographicRecord;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.domain.enumeration.ConditionType;
import com.tech.thermography.domain.enumeration.ThermographicRecordType;
import com.tech.thermography.repository.ThermographicRecordRepository;
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
 * Integration tests for the {@link ThermographicRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThermographicRecordResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ThermographicRecordType DEFAULT_TYPE = ThermographicRecordType.NO_ANOMALY;
    private static final ThermographicRecordType UPDATED_TYPE = ThermographicRecordType.ANOMALY_DETECTED;

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

    private static final Double DEFAULT_CURRENT = 1D;
    private static final Double UPDATED_CURRENT = 2D;

    private static final Double DEFAULT_VOLTAGE = 1D;
    private static final Double UPDATED_VOLTAGE = 2D;

    private static final Double DEFAULT_WIND_SPEED = 1D;
    private static final Double UPDATED_WIND_SPEED = 2D;

    private static final String ENTITY_API_URL = "/api/thermographic-records";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ThermographicRecordRepository thermographicRecordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThermographicRecordMockMvc;

    private ThermographicRecord thermographicRecord;

    private ThermographicRecord insertedThermographicRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThermographicRecord createEntity(EntityManager em) {
        ThermographicRecord thermographicRecord = new ThermographicRecord()
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
            .finishedAt(DEFAULT_FINISHED_AT)
            .current(DEFAULT_CURRENT)
            .voltage(DEFAULT_VOLTAGE)
            .windSpeed(DEFAULT_WIND_SPEED);
        // Add required entity
        Plant plant;
        if (TestUtil.findAll(em, Plant.class).isEmpty()) {
            plant = PlantResourceIT.createEntity();
            em.persist(plant);
            em.flush();
        } else {
            plant = TestUtil.findAll(em, Plant.class).get(0);
        }
        thermographicRecord.setPlant(plant);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        thermographicRecord.setEquipment(equipment);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        thermographicRecord.setCreatedBy(userInfo);
        // Add required entity
        thermographicRecord.setFinishedBy(userInfo);
        // Add required entity
        Thermogram thermogram;
        if (TestUtil.findAll(em, Thermogram.class).isEmpty()) {
            thermogram = ThermogramResourceIT.createEntity(em);
            em.persist(thermogram);
            em.flush();
        } else {
            thermogram = TestUtil.findAll(em, Thermogram.class).get(0);
        }
        thermographicRecord.setThermogram(thermogram);
        return thermographicRecord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThermographicRecord createUpdatedEntity(EntityManager em) {
        ThermographicRecord updatedThermographicRecord = new ThermographicRecord()
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
            .finishedAt(UPDATED_FINISHED_AT)
            .current(UPDATED_CURRENT)
            .voltage(UPDATED_VOLTAGE)
            .windSpeed(UPDATED_WIND_SPEED);
        // Add required entity
        Plant plant;
        if (TestUtil.findAll(em, Plant.class).isEmpty()) {
            plant = PlantResourceIT.createUpdatedEntity();
            em.persist(plant);
            em.flush();
        } else {
            plant = TestUtil.findAll(em, Plant.class).get(0);
        }
        updatedThermographicRecord.setPlant(plant);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createUpdatedEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        updatedThermographicRecord.setEquipment(equipment);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createUpdatedEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        updatedThermographicRecord.setCreatedBy(userInfo);
        // Add required entity
        updatedThermographicRecord.setFinishedBy(userInfo);
        // Add required entity
        Thermogram thermogram;
        if (TestUtil.findAll(em, Thermogram.class).isEmpty()) {
            thermogram = ThermogramResourceIT.createUpdatedEntity(em);
            em.persist(thermogram);
            em.flush();
        } else {
            thermogram = TestUtil.findAll(em, Thermogram.class).get(0);
        }
        updatedThermographicRecord.setThermogram(thermogram);
        return updatedThermographicRecord;
    }

    @BeforeEach
    void initTest() {
        thermographicRecord = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedThermographicRecord != null) {
            thermographicRecordRepository.delete(insertedThermographicRecord);
            insertedThermographicRecord = null;
        }
    }

    @Test
    @Transactional
    void createThermographicRecord() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ThermographicRecord
        var returnedThermographicRecord = om.readValue(
            restThermographicRecordMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicRecord)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ThermographicRecord.class
        );

        // Validate the ThermographicRecord in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertThermographicRecordUpdatableFieldsEquals(
            returnedThermographicRecord,
            getPersistedThermographicRecord(returnedThermographicRecord)
        );

        insertedThermographicRecord = returnedThermographicRecord;
    }

    @Test
    @Transactional
    void createThermographicRecordWithExistingId() throws Exception {
        // Create the ThermographicRecord with an existing ID
        insertedThermographicRecord = thermographicRecordRepository.saveAndFlush(thermographicRecord);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThermographicRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicRecord)))
            .andExpect(status().isBadRequest());

        // Validate the ThermographicRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicRecord.setName(null);

        // Create the ThermographicRecord, which fails.

        restThermographicRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicRecord.setType(null);

        // Create the ThermographicRecord, which fails.

        restThermographicRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicRecord.setCreatedAt(null);

        // Create the ThermographicRecord, which fails.

        restThermographicRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConditionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicRecord.setCondition(null);

        // Create the ThermographicRecord, which fails.

        restThermographicRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeltaTIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermographicRecord.setDeltaT(null);

        // Create the ThermographicRecord, which fails.

        restThermographicRecordMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicRecord)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllThermographicRecords() throws Exception {
        // Initialize the database
        insertedThermographicRecord = thermographicRecordRepository.saveAndFlush(thermographicRecord);

        // Get all the thermographicRecordList
        restThermographicRecordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thermographicRecord.getId().toString())))
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
            .andExpect(jsonPath("$.[*].finishedAt").value(hasItem(DEFAULT_FINISHED_AT.toString())))
            .andExpect(jsonPath("$.[*].current").value(hasItem(DEFAULT_CURRENT)))
            .andExpect(jsonPath("$.[*].voltage").value(hasItem(DEFAULT_VOLTAGE)))
            .andExpect(jsonPath("$.[*].windSpeed").value(hasItem(DEFAULT_WIND_SPEED)));
    }

    @Test
    @Transactional
    void getThermographicRecord() throws Exception {
        // Initialize the database
        insertedThermographicRecord = thermographicRecordRepository.saveAndFlush(thermographicRecord);

        // Get the thermographicRecord
        restThermographicRecordMockMvc
            .perform(get(ENTITY_API_URL_ID, thermographicRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thermographicRecord.getId().toString()))
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
            .andExpect(jsonPath("$.finishedAt").value(DEFAULT_FINISHED_AT.toString()))
            .andExpect(jsonPath("$.current").value(DEFAULT_CURRENT))
            .andExpect(jsonPath("$.voltage").value(DEFAULT_VOLTAGE))
            .andExpect(jsonPath("$.windSpeed").value(DEFAULT_WIND_SPEED));
    }

    @Test
    @Transactional
    void getNonExistingThermographicRecord() throws Exception {
        // Get the thermographicRecord
        restThermographicRecordMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingThermographicRecord() throws Exception {
        // Initialize the database
        insertedThermographicRecord = thermographicRecordRepository.saveAndFlush(thermographicRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thermographicRecord
        ThermographicRecord updatedThermographicRecord = thermographicRecordRepository.findById(thermographicRecord.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedThermographicRecord are not directly saved in db
        em.detach(updatedThermographicRecord);
        updatedThermographicRecord
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
            .finishedAt(UPDATED_FINISHED_AT)
            .current(UPDATED_CURRENT)
            .voltage(UPDATED_VOLTAGE)
            .windSpeed(UPDATED_WIND_SPEED);

        restThermographicRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThermographicRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedThermographicRecord))
            )
            .andExpect(status().isOk());

        // Validate the ThermographicRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedThermographicRecordToMatchAllProperties(updatedThermographicRecord);
    }

    @Test
    @Transactional
    void putNonExistingThermographicRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicRecord.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThermographicRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thermographicRecord.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thermographicRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThermographicRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThermographicRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermographicRecordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(thermographicRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThermographicRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThermographicRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermographicRecordMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermographicRecord)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThermographicRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThermographicRecordWithPatch() throws Exception {
        // Initialize the database
        insertedThermographicRecord = thermographicRecordRepository.saveAndFlush(thermographicRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thermographicRecord using partial update
        ThermographicRecord partialUpdatedThermographicRecord = new ThermographicRecord();
        partialUpdatedThermographicRecord.setId(thermographicRecord.getId());

        partialUpdatedThermographicRecord
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .analysisDescription(UPDATED_ANALYSIS_DESCRIPTION)
            .periodicity(UPDATED_PERIODICITY)
            .recommendations(UPDATED_RECOMMENDATIONS)
            .voltage(UPDATED_VOLTAGE)
            .windSpeed(UPDATED_WIND_SPEED);

        restThermographicRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThermographicRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThermographicRecord))
            )
            .andExpect(status().isOk());

        // Validate the ThermographicRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThermographicRecordUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedThermographicRecord, thermographicRecord),
            getPersistedThermographicRecord(thermographicRecord)
        );
    }

    @Test
    @Transactional
    void fullUpdateThermographicRecordWithPatch() throws Exception {
        // Initialize the database
        insertedThermographicRecord = thermographicRecordRepository.saveAndFlush(thermographicRecord);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thermographicRecord using partial update
        ThermographicRecord partialUpdatedThermographicRecord = new ThermographicRecord();
        partialUpdatedThermographicRecord.setId(thermographicRecord.getId());

        partialUpdatedThermographicRecord
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
            .finishedAt(UPDATED_FINISHED_AT)
            .current(UPDATED_CURRENT)
            .voltage(UPDATED_VOLTAGE)
            .windSpeed(UPDATED_WIND_SPEED);

        restThermographicRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThermographicRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThermographicRecord))
            )
            .andExpect(status().isOk());

        // Validate the ThermographicRecord in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThermographicRecordUpdatableFieldsEquals(
            partialUpdatedThermographicRecord,
            getPersistedThermographicRecord(partialUpdatedThermographicRecord)
        );
    }

    @Test
    @Transactional
    void patchNonExistingThermographicRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicRecord.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThermographicRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thermographicRecord.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thermographicRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThermographicRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThermographicRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermographicRecordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thermographicRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThermographicRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThermographicRecord() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermographicRecord.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermographicRecordMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(thermographicRecord)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThermographicRecord in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThermographicRecord() throws Exception {
        // Initialize the database
        insertedThermographicRecord = thermographicRecordRepository.saveAndFlush(thermographicRecord);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the thermographicRecord
        restThermographicRecordMockMvc
            .perform(delete(ENTITY_API_URL_ID, thermographicRecord.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return thermographicRecordRepository.count();
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

    protected ThermographicRecord getPersistedThermographicRecord(ThermographicRecord thermographicRecord) {
        return thermographicRecordRepository.findById(thermographicRecord.getId()).orElseThrow();
    }

    protected void assertPersistedThermographicRecordToMatchAllProperties(ThermographicRecord expectedThermographicRecord) {
        assertThermographicRecordAllPropertiesEquals(
            expectedThermographicRecord,
            getPersistedThermographicRecord(expectedThermographicRecord)
        );
    }

    protected void assertPersistedThermographicRecordToMatchUpdatableProperties(ThermographicRecord expectedThermographicRecord) {
        assertThermographicRecordAllUpdatablePropertiesEquals(
            expectedThermographicRecord,
            getPersistedThermographicRecord(expectedThermographicRecord)
        );
    }
}
