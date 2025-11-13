package com.tech.thermography.domain;

import static com.tech.thermography.domain.CompanyTestSamples.*;
import static com.tech.thermography.domain.UserInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInfo.class);
        UserInfo userInfo1 = getUserInfoSample1();
        UserInfo userInfo2 = new UserInfo();
        assertThat(userInfo1).isNotEqualTo(userInfo2);

        userInfo2.setId(userInfo1.getId());
        assertThat(userInfo1).isEqualTo(userInfo2);

        userInfo2 = getUserInfoSample2();
        assertThat(userInfo1).isNotEqualTo(userInfo2);
    }

    @Test
    void companyTest() {
        UserInfo userInfo = getUserInfoRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        userInfo.setCompany(companyBack);
        assertThat(userInfo.getCompany()).isEqualTo(companyBack);

        userInfo.company(null);
        assertThat(userInfo.getCompany()).isNull();
    }
}
