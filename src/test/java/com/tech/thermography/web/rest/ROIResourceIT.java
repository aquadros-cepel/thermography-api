package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.ROIAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.ROI;
import com.tech.thermography.domain.Thermogram;
import com.tech.thermography.repository.ROIRepository;
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
 * Integration tests for the {@link ROIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ROIResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Double DEFAULT_MAX_TEMP = 1D;
    private static final Double UPDATED_MAX_TEMP = 2D;

    private static final String ENTITY_API_URL = "/api/rois";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ROIRepository rOIRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restROIMockMvc;

    private ROI rOI;

    private ROI insertedROI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ROI createEntity(EntityManager em) {
        ROI rOI = new ROI().type(DEFAULT_TYPE).label(DEFAULT_LABEL).maxTemp(DEFAULT_MAX_TEMP);
        // Add required entity
        Thermogram thermogram;
        if (TestUtil.findAll(em, Thermogram.class).isEmpty()) {
            thermogram = ThermogramResourceIT.createEntity(em);
            em.persist(thermogram);
            em.flush();
        } else {
            thermogram = TestUtil.findAll(em, Thermogram.class).get(0);
        }
        rOI.setThermogram(thermogram);
        return rOI;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ROI createUpdatedEntity(EntityManager em) {
        ROI updatedROI = new ROI().type(UPDATED_TYPE).label(UPDATED_LABEL).maxTemp(UPDATED_MAX_TEMP);
        // Add required entity
        Thermogram thermogram;
        if (TestUtil.findAll(em, Thermogram.class).isEmpty()) {
            thermogram = ThermogramResourceIT.createUpdatedEntity(em);
            em.persist(thermogram);
            em.flush();
        } else {
            thermogram = TestUtil.findAll(em, Thermogram.class).get(0);
        }
        updatedROI.setThermogram(thermogram);
        return updatedROI;
    }

    @BeforeEach
    void initTest() {
        rOI = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedROI != null) {
            rOIRepository.delete(insertedROI);
            insertedROI = null;
        }
    }

    @Test
    @Transactional
    void createROI() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ROI
        var returnedROI = om.readValue(
            restROIMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rOI)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ROI.class
        );

        // Validate the ROI in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertROIUpdatableFieldsEquals(returnedROI, getPersistedROI(returnedROI));

        insertedROI = returnedROI;
    }

    @Test
    @Transactional
    void createROIWithExistingId() throws Exception {
        // Create the ROI with an existing ID
        insertedROI = rOIRepository.saveAndFlush(rOI);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restROIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rOI)))
            .andExpect(status().isBadRequest());

        // Validate the ROI in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rOI.setType(null);

        // Create the ROI, which fails.

        restROIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rOI)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLabelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rOI.setLabel(null);

        // Create the ROI, which fails.

        restROIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rOI)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaxTempIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rOI.setMaxTemp(null);

        // Create the ROI, which fails.

        restROIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rOI)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllROIS() throws Exception {
        // Initialize the database
        insertedROI = rOIRepository.saveAndFlush(rOI);

        // Get all the rOIList
        restROIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rOI.getId().toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].maxTemp").value(hasItem(DEFAULT_MAX_TEMP)));
    }

    @Test
    @Transactional
    void getROI() throws Exception {
        // Initialize the database
        insertedROI = rOIRepository.saveAndFlush(rOI);

        // Get the rOI
        restROIMockMvc
            .perform(get(ENTITY_API_URL_ID, rOI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rOI.getId().toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.maxTemp").value(DEFAULT_MAX_TEMP));
    }

    @Test
    @Transactional
    void getNonExistingROI() throws Exception {
        // Get the rOI
        restROIMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingROI() throws Exception {
        // Initialize the database
        insertedROI = rOIRepository.saveAndFlush(rOI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rOI
        ROI updatedROI = rOIRepository.findById(rOI.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedROI are not directly saved in db
        em.detach(updatedROI);
        updatedROI.type(UPDATED_TYPE).label(UPDATED_LABEL).maxTemp(UPDATED_MAX_TEMP);

        restROIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedROI.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedROI))
            )
            .andExpect(status().isOk());

        // Validate the ROI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedROIToMatchAllProperties(updatedROI);
    }

    @Test
    @Transactional
    void putNonExistingROI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rOI.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restROIMockMvc
            .perform(put(ENTITY_API_URL_ID, rOI.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rOI)))
            .andExpect(status().isBadRequest());

        // Validate the ROI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchROI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rOI.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restROIMockMvc
            .perform(put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rOI)))
            .andExpect(status().isBadRequest());

        // Validate the ROI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamROI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rOI.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restROIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rOI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ROI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateROIWithPatch() throws Exception {
        // Initialize the database
        insertedROI = rOIRepository.saveAndFlush(rOI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rOI using partial update
        ROI partialUpdatedROI = new ROI();
        partialUpdatedROI.setId(rOI.getId());

        partialUpdatedROI.type(UPDATED_TYPE).label(UPDATED_LABEL);

        restROIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedROI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedROI))
            )
            .andExpect(status().isOk());

        // Validate the ROI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertROIUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedROI, rOI), getPersistedROI(rOI));
    }

    @Test
    @Transactional
    void fullUpdateROIWithPatch() throws Exception {
        // Initialize the database
        insertedROI = rOIRepository.saveAndFlush(rOI);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rOI using partial update
        ROI partialUpdatedROI = new ROI();
        partialUpdatedROI.setId(rOI.getId());

        partialUpdatedROI.type(UPDATED_TYPE).label(UPDATED_LABEL).maxTemp(UPDATED_MAX_TEMP);

        restROIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedROI.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedROI))
            )
            .andExpect(status().isOk());

        // Validate the ROI in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertROIUpdatableFieldsEquals(partialUpdatedROI, getPersistedROI(partialUpdatedROI));
    }

    @Test
    @Transactional
    void patchNonExistingROI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rOI.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restROIMockMvc
            .perform(patch(ENTITY_API_URL_ID, rOI.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rOI)))
            .andExpect(status().isBadRequest());

        // Validate the ROI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchROI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rOI.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restROIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rOI))
            )
            .andExpect(status().isBadRequest());

        // Validate the ROI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamROI() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rOI.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restROIMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rOI)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ROI in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteROI() throws Exception {
        // Initialize the database
        insertedROI = rOIRepository.saveAndFlush(rOI);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rOI
        restROIMockMvc
            .perform(delete(ENTITY_API_URL_ID, rOI.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rOIRepository.count();
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

    protected ROI getPersistedROI(ROI rOI) {
        return rOIRepository.findById(rOI.getId()).orElseThrow();
    }

    protected void assertPersistedROIToMatchAllProperties(ROI expectedROI) {
        assertROIAllPropertiesEquals(expectedROI, getPersistedROI(expectedROI));
    }

    protected void assertPersistedROIToMatchUpdatableProperties(ROI expectedROI) {
        assertROIAllUpdatablePropertiesEquals(expectedROI, getPersistedROI(expectedROI));
    }
}
