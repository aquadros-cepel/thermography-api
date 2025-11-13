package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.ThermogramAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.Thermogram;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.repository.ThermogramRepository;
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
 * Integration tests for the {@link ThermogramResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThermogramResourceIT {

    private static final String DEFAULT_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_AUDIO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_AUDIO_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_REF_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_REF_PATH = "BBBBBBBBBB";

    private static final Double DEFAULT_MIN_TEMP = 1D;
    private static final Double UPDATED_MIN_TEMP = 2D;

    private static final Double DEFAULT_AVG_TEMP = 1D;
    private static final Double UPDATED_AVG_TEMP = 2D;

    private static final Double DEFAULT_MAX_TEMP = 1D;
    private static final Double UPDATED_MAX_TEMP = 2D;

    private static final Double DEFAULT_EMISSIVITY = 1D;
    private static final Double UPDATED_EMISSIVITY = 2D;

    private static final Double DEFAULT_SUBJECT_DISTANCE = 1D;
    private static final Double UPDATED_SUBJECT_DISTANCE = 2D;

    private static final Double DEFAULT_ATMOSPHERIC_TEMP = 1D;
    private static final Double UPDATED_ATMOSPHERIC_TEMP = 2D;

    private static final Double DEFAULT_REFLECTED_TEMP = 1D;
    private static final Double UPDATED_REFLECTED_TEMP = 2D;

    private static final Double DEFAULT_RELATIVE_HUMIDITY = 1D;
    private static final Double UPDATED_RELATIVE_HUMIDITY = 2D;

    private static final String DEFAULT_CAMERA_LENS = "AAAAAAAAAA";
    private static final String UPDATED_CAMERA_LENS = "BBBBBBBBBB";

    private static final String DEFAULT_CAMERA_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_CAMERA_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_RESOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_RESOLUTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_SELECTED_ROI_ID = UUID.randomUUID();
    private static final UUID UPDATED_SELECTED_ROI_ID = UUID.randomUUID();

    private static final Double DEFAULT_MAX_TEMP_ROI = 1D;
    private static final Double UPDATED_MAX_TEMP_ROI = 2D;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final String ENTITY_API_URL = "/api/thermograms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ThermogramRepository thermogramRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThermogramMockMvc;

    private Thermogram thermogram;

    private Thermogram insertedThermogram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thermogram createEntity(EntityManager em) {
        Thermogram thermogram = new Thermogram()
            .imagePath(DEFAULT_IMAGE_PATH)
            .audioPath(DEFAULT_AUDIO_PATH)
            .imageRefPath(DEFAULT_IMAGE_REF_PATH)
            .minTemp(DEFAULT_MIN_TEMP)
            .avgTemp(DEFAULT_AVG_TEMP)
            .maxTemp(DEFAULT_MAX_TEMP)
            .emissivity(DEFAULT_EMISSIVITY)
            .subjectDistance(DEFAULT_SUBJECT_DISTANCE)
            .atmosphericTemp(DEFAULT_ATMOSPHERIC_TEMP)
            .reflectedTemp(DEFAULT_REFLECTED_TEMP)
            .relativeHumidity(DEFAULT_RELATIVE_HUMIDITY)
            .cameraLens(DEFAULT_CAMERA_LENS)
            .cameraModel(DEFAULT_CAMERA_MODEL)
            .imageResolution(DEFAULT_IMAGE_RESOLUTION)
            .selectedRoiId(DEFAULT_SELECTED_ROI_ID)
            .maxTempRoi(DEFAULT_MAX_TEMP_ROI)
            .createdAt(DEFAULT_CREATED_AT)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        thermogram.setEquipment(equipment);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        thermogram.setCreatedBy(userInfo);
        return thermogram;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thermogram createUpdatedEntity(EntityManager em) {
        Thermogram updatedThermogram = new Thermogram()
            .imagePath(UPDATED_IMAGE_PATH)
            .audioPath(UPDATED_AUDIO_PATH)
            .imageRefPath(UPDATED_IMAGE_REF_PATH)
            .minTemp(UPDATED_MIN_TEMP)
            .avgTemp(UPDATED_AVG_TEMP)
            .maxTemp(UPDATED_MAX_TEMP)
            .emissivity(UPDATED_EMISSIVITY)
            .subjectDistance(UPDATED_SUBJECT_DISTANCE)
            .atmosphericTemp(UPDATED_ATMOSPHERIC_TEMP)
            .reflectedTemp(UPDATED_REFLECTED_TEMP)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .cameraLens(UPDATED_CAMERA_LENS)
            .cameraModel(UPDATED_CAMERA_MODEL)
            .imageResolution(UPDATED_IMAGE_RESOLUTION)
            .selectedRoiId(UPDATED_SELECTED_ROI_ID)
            .maxTempRoi(UPDATED_MAX_TEMP_ROI)
            .createdAt(UPDATED_CREATED_AT)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        // Add required entity
        Equipment equipment;
        if (TestUtil.findAll(em, Equipment.class).isEmpty()) {
            equipment = EquipmentResourceIT.createUpdatedEntity(em);
            em.persist(equipment);
            em.flush();
        } else {
            equipment = TestUtil.findAll(em, Equipment.class).get(0);
        }
        updatedThermogram.setEquipment(equipment);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createUpdatedEntity();
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        updatedThermogram.setCreatedBy(userInfo);
        return updatedThermogram;
    }

    @BeforeEach
    void initTest() {
        thermogram = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedThermogram != null) {
            thermogramRepository.delete(insertedThermogram);
            insertedThermogram = null;
        }
    }

    @Test
    @Transactional
    void createThermogram() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Thermogram
        var returnedThermogram = om.readValue(
            restThermogramMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermogram)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Thermogram.class
        );

        // Validate the Thermogram in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertThermogramUpdatableFieldsEquals(returnedThermogram, getPersistedThermogram(returnedThermogram));

        insertedThermogram = returnedThermogram;
    }

    @Test
    @Transactional
    void createThermogramWithExistingId() throws Exception {
        // Create the Thermogram with an existing ID
        insertedThermogram = thermogramRepository.saveAndFlush(thermogram);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThermogramMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermogram)))
            .andExpect(status().isBadRequest());

        // Validate the Thermogram in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkImagePathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermogram.setImagePath(null);

        // Create the Thermogram, which fails.

        restThermogramMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermogram)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImageRefPathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        thermogram.setImageRefPath(null);

        // Create the Thermogram, which fails.

        restThermogramMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermogram)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllThermograms() throws Exception {
        // Initialize the database
        insertedThermogram = thermogramRepository.saveAndFlush(thermogram);

        // Get all the thermogramList
        restThermogramMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thermogram.getId().toString())))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].audioPath").value(hasItem(DEFAULT_AUDIO_PATH)))
            .andExpect(jsonPath("$.[*].imageRefPath").value(hasItem(DEFAULT_IMAGE_REF_PATH)))
            .andExpect(jsonPath("$.[*].minTemp").value(hasItem(DEFAULT_MIN_TEMP)))
            .andExpect(jsonPath("$.[*].avgTemp").value(hasItem(DEFAULT_AVG_TEMP)))
            .andExpect(jsonPath("$.[*].maxTemp").value(hasItem(DEFAULT_MAX_TEMP)))
            .andExpect(jsonPath("$.[*].emissivity").value(hasItem(DEFAULT_EMISSIVITY)))
            .andExpect(jsonPath("$.[*].subjectDistance").value(hasItem(DEFAULT_SUBJECT_DISTANCE)))
            .andExpect(jsonPath("$.[*].atmosphericTemp").value(hasItem(DEFAULT_ATMOSPHERIC_TEMP)))
            .andExpect(jsonPath("$.[*].reflectedTemp").value(hasItem(DEFAULT_REFLECTED_TEMP)))
            .andExpect(jsonPath("$.[*].relativeHumidity").value(hasItem(DEFAULT_RELATIVE_HUMIDITY)))
            .andExpect(jsonPath("$.[*].cameraLens").value(hasItem(DEFAULT_CAMERA_LENS)))
            .andExpect(jsonPath("$.[*].cameraModel").value(hasItem(DEFAULT_CAMERA_MODEL)))
            .andExpect(jsonPath("$.[*].imageResolution").value(hasItem(DEFAULT_IMAGE_RESOLUTION)))
            .andExpect(jsonPath("$.[*].selectedRoiId").value(hasItem(DEFAULT_SELECTED_ROI_ID.toString())))
            .andExpect(jsonPath("$.[*].maxTempRoi").value(hasItem(DEFAULT_MAX_TEMP_ROI)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)));
    }

    @Test
    @Transactional
    void getThermogram() throws Exception {
        // Initialize the database
        insertedThermogram = thermogramRepository.saveAndFlush(thermogram);

        // Get the thermogram
        restThermogramMockMvc
            .perform(get(ENTITY_API_URL_ID, thermogram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thermogram.getId().toString()))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH))
            .andExpect(jsonPath("$.audioPath").value(DEFAULT_AUDIO_PATH))
            .andExpect(jsonPath("$.imageRefPath").value(DEFAULT_IMAGE_REF_PATH))
            .andExpect(jsonPath("$.minTemp").value(DEFAULT_MIN_TEMP))
            .andExpect(jsonPath("$.avgTemp").value(DEFAULT_AVG_TEMP))
            .andExpect(jsonPath("$.maxTemp").value(DEFAULT_MAX_TEMP))
            .andExpect(jsonPath("$.emissivity").value(DEFAULT_EMISSIVITY))
            .andExpect(jsonPath("$.subjectDistance").value(DEFAULT_SUBJECT_DISTANCE))
            .andExpect(jsonPath("$.atmosphericTemp").value(DEFAULT_ATMOSPHERIC_TEMP))
            .andExpect(jsonPath("$.reflectedTemp").value(DEFAULT_REFLECTED_TEMP))
            .andExpect(jsonPath("$.relativeHumidity").value(DEFAULT_RELATIVE_HUMIDITY))
            .andExpect(jsonPath("$.cameraLens").value(DEFAULT_CAMERA_LENS))
            .andExpect(jsonPath("$.cameraModel").value(DEFAULT_CAMERA_MODEL))
            .andExpect(jsonPath("$.imageResolution").value(DEFAULT_IMAGE_RESOLUTION))
            .andExpect(jsonPath("$.selectedRoiId").value(DEFAULT_SELECTED_ROI_ID.toString()))
            .andExpect(jsonPath("$.maxTempRoi").value(DEFAULT_MAX_TEMP_ROI))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE));
    }

    @Test
    @Transactional
    void getNonExistingThermogram() throws Exception {
        // Get the thermogram
        restThermogramMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingThermogram() throws Exception {
        // Initialize the database
        insertedThermogram = thermogramRepository.saveAndFlush(thermogram);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thermogram
        Thermogram updatedThermogram = thermogramRepository.findById(thermogram.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedThermogram are not directly saved in db
        em.detach(updatedThermogram);
        updatedThermogram
            .imagePath(UPDATED_IMAGE_PATH)
            .audioPath(UPDATED_AUDIO_PATH)
            .imageRefPath(UPDATED_IMAGE_REF_PATH)
            .minTemp(UPDATED_MIN_TEMP)
            .avgTemp(UPDATED_AVG_TEMP)
            .maxTemp(UPDATED_MAX_TEMP)
            .emissivity(UPDATED_EMISSIVITY)
            .subjectDistance(UPDATED_SUBJECT_DISTANCE)
            .atmosphericTemp(UPDATED_ATMOSPHERIC_TEMP)
            .reflectedTemp(UPDATED_REFLECTED_TEMP)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .cameraLens(UPDATED_CAMERA_LENS)
            .cameraModel(UPDATED_CAMERA_MODEL)
            .imageResolution(UPDATED_IMAGE_RESOLUTION)
            .selectedRoiId(UPDATED_SELECTED_ROI_ID)
            .maxTempRoi(UPDATED_MAX_TEMP_ROI)
            .createdAt(UPDATED_CREATED_AT)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restThermogramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThermogram.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedThermogram))
            )
            .andExpect(status().isOk());

        // Validate the Thermogram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedThermogramToMatchAllProperties(updatedThermogram);
    }

    @Test
    @Transactional
    void putNonExistingThermogram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermogram.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThermogramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thermogram.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermogram))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thermogram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThermogram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermogram.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermogramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermogram))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thermogram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThermogram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermogram.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermogramMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(thermogram)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thermogram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThermogramWithPatch() throws Exception {
        // Initialize the database
        insertedThermogram = thermogramRepository.saveAndFlush(thermogram);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thermogram using partial update
        Thermogram partialUpdatedThermogram = new Thermogram();
        partialUpdatedThermogram.setId(thermogram.getId());

        partialUpdatedThermogram
            .minTemp(UPDATED_MIN_TEMP)
            .avgTemp(UPDATED_AVG_TEMP)
            .emissivity(UPDATED_EMISSIVITY)
            .cameraModel(UPDATED_CAMERA_MODEL)
            .maxTempRoi(UPDATED_MAX_TEMP_ROI)
            .createdAt(UPDATED_CREATED_AT)
            .longitude(UPDATED_LONGITUDE);

        restThermogramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThermogram.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThermogram))
            )
            .andExpect(status().isOk());

        // Validate the Thermogram in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThermogramUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedThermogram, thermogram),
            getPersistedThermogram(thermogram)
        );
    }

    @Test
    @Transactional
    void fullUpdateThermogramWithPatch() throws Exception {
        // Initialize the database
        insertedThermogram = thermogramRepository.saveAndFlush(thermogram);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the thermogram using partial update
        Thermogram partialUpdatedThermogram = new Thermogram();
        partialUpdatedThermogram.setId(thermogram.getId());

        partialUpdatedThermogram
            .imagePath(UPDATED_IMAGE_PATH)
            .audioPath(UPDATED_AUDIO_PATH)
            .imageRefPath(UPDATED_IMAGE_REF_PATH)
            .minTemp(UPDATED_MIN_TEMP)
            .avgTemp(UPDATED_AVG_TEMP)
            .maxTemp(UPDATED_MAX_TEMP)
            .emissivity(UPDATED_EMISSIVITY)
            .subjectDistance(UPDATED_SUBJECT_DISTANCE)
            .atmosphericTemp(UPDATED_ATMOSPHERIC_TEMP)
            .reflectedTemp(UPDATED_REFLECTED_TEMP)
            .relativeHumidity(UPDATED_RELATIVE_HUMIDITY)
            .cameraLens(UPDATED_CAMERA_LENS)
            .cameraModel(UPDATED_CAMERA_MODEL)
            .imageResolution(UPDATED_IMAGE_RESOLUTION)
            .selectedRoiId(UPDATED_SELECTED_ROI_ID)
            .maxTempRoi(UPDATED_MAX_TEMP_ROI)
            .createdAt(UPDATED_CREATED_AT)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restThermogramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThermogram.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedThermogram))
            )
            .andExpect(status().isOk());

        // Validate the Thermogram in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertThermogramUpdatableFieldsEquals(partialUpdatedThermogram, getPersistedThermogram(partialUpdatedThermogram));
    }

    @Test
    @Transactional
    void patchNonExistingThermogram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermogram.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThermogramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thermogram.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thermogram))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thermogram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThermogram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermogram.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermogramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(thermogram))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thermogram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThermogram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        thermogram.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThermogramMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(thermogram)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thermogram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThermogram() throws Exception {
        // Initialize the database
        insertedThermogram = thermogramRepository.saveAndFlush(thermogram);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the thermogram
        restThermogramMockMvc
            .perform(delete(ENTITY_API_URL_ID, thermogram.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return thermogramRepository.count();
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

    protected Thermogram getPersistedThermogram(Thermogram thermogram) {
        return thermogramRepository.findById(thermogram.getId()).orElseThrow();
    }

    protected void assertPersistedThermogramToMatchAllProperties(Thermogram expectedThermogram) {
        assertThermogramAllPropertiesEquals(expectedThermogram, getPersistedThermogram(expectedThermogram));
    }

    protected void assertPersistedThermogramToMatchUpdatableProperties(Thermogram expectedThermogram) {
        assertThermogramAllUpdatablePropertiesEquals(expectedThermogram, getPersistedThermogram(expectedThermogram));
    }
}
