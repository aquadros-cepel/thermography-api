package com.tech.thermography.web.rest;

import static com.tech.thermography.domain.UserInfoAsserts.*;
import static com.tech.thermography.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.thermography.IntegrationTest;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.repository.UserInfoRepository;
import com.tech.thermography.repository.UserRepository;
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
 * Integration tests for the {@link UserInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserInfoResourceIT {

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserInfoMockMvc;

    private UserInfo userInfo;

    private UserInfo insertedUserInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createEntity() {
        return new UserInfo().position(DEFAULT_POSITION).phoneNumber(DEFAULT_PHONE_NUMBER);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createUpdatedEntity() {
        return new UserInfo().position(UPDATED_POSITION).phoneNumber(UPDATED_PHONE_NUMBER);
    }

    @BeforeEach
    void initTest() {
        userInfo = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedUserInfo != null) {
            userInfoRepository.delete(insertedUserInfo);
            insertedUserInfo = null;
        }
    }

    @Test
    @Transactional
    void createUserInfo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UserInfo
        var returnedUserInfo = om.readValue(
            restUserInfoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userInfo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UserInfo.class
        );

        // Validate the UserInfo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUserInfoUpdatableFieldsEquals(returnedUserInfo, getPersistedUserInfo(returnedUserInfo));

        insertedUserInfo = returnedUserInfo;
    }

    @Test
    @Transactional
    void createUserInfoWithExistingId() throws Exception {
        // Create the UserInfo with an existing ID
        insertedUserInfo = userInfoRepository.saveAndFlush(userInfo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userInfo)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserInfos() throws Exception {
        // Initialize the database
        insertedUserInfo = userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    @Transactional
    void getUserInfo() throws Exception {
        // Initialize the database
        insertedUserInfo = userInfoRepository.saveAndFlush(userInfo);

        // Get the userInfo
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, userInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userInfo.getId().toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingUserInfo() throws Exception {
        // Get the userInfo
        restUserInfoMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserInfo() throws Exception {
        // Initialize the database
        insertedUserInfo = userInfoRepository.saveAndFlush(userInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userInfo
        UserInfo updatedUserInfo = userInfoRepository.findById(userInfo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUserInfo are not directly saved in db
        em.detach(updatedUserInfo);
        updatedUserInfo.position(UPDATED_POSITION).phoneNumber(UPDATED_PHONE_NUMBER);

        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUserInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUserInfoToMatchAllProperties(updatedUserInfo);
    }

    @Test
    @Transactional
    void putNonExistingUserInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userInfo.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userInfo.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserInfoWithPatch() throws Exception {
        // Initialize the database
        insertedUserInfo = userInfoRepository.saveAndFlush(userInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userInfo using partial update
        UserInfo partialUpdatedUserInfo = new UserInfo();
        partialUpdatedUserInfo.setId(userInfo.getId());

        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserInfoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedUserInfo, userInfo), getPersistedUserInfo(userInfo));
    }

    @Test
    @Transactional
    void fullUpdateUserInfoWithPatch() throws Exception {
        // Initialize the database
        insertedUserInfo = userInfoRepository.saveAndFlush(userInfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userInfo using partial update
        UserInfo partialUpdatedUserInfo = new UserInfo();
        partialUpdatedUserInfo.setId(userInfo.getId());

        partialUpdatedUserInfo.position(UPDATED_POSITION).phoneNumber(UPDATED_PHONE_NUMBER);

        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserInfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserInfoUpdatableFieldsEquals(partialUpdatedUserInfo, getPersistedUserInfo(partialUpdatedUserInfo));
    }

    @Test
    @Transactional
    void patchNonExistingUserInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userInfo.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserInfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(userInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserInfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserInfo() throws Exception {
        // Initialize the database
        insertedUserInfo = userInfoRepository.saveAndFlush(userInfo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the userInfo
        restUserInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, userInfo.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return userInfoRepository.count();
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

    protected UserInfo getPersistedUserInfo(UserInfo userInfo) {
        return userInfoRepository.findById(userInfo.getId()).orElseThrow();
    }

    protected void assertPersistedUserInfoToMatchAllProperties(UserInfo expectedUserInfo) {
        assertUserInfoAllPropertiesEquals(expectedUserInfo, getPersistedUserInfo(expectedUserInfo));
    }

    protected void assertPersistedUserInfoToMatchUpdatableProperties(UserInfo expectedUserInfo) {
        assertUserInfoAllUpdatablePropertiesEquals(expectedUserInfo, getPersistedUserInfo(expectedUserInfo));
    }
}
