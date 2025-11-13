package com.tech.thermography.domain;

import static com.tech.thermography.domain.BusinessUnitTestSamples.*;
import static com.tech.thermography.domain.CompanyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessUnit.class);
        BusinessUnit businessUnit1 = getBusinessUnitSample1();
        BusinessUnit businessUnit2 = new BusinessUnit();
        assertThat(businessUnit1).isNotEqualTo(businessUnit2);

        businessUnit2.setId(businessUnit1.getId());
        assertThat(businessUnit1).isEqualTo(businessUnit2);

        businessUnit2 = getBusinessUnitSample2();
        assertThat(businessUnit1).isNotEqualTo(businessUnit2);
    }

    @Test
    void companyTest() {
        BusinessUnit businessUnit = getBusinessUnitRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        businessUnit.setCompany(companyBack);
        assertThat(businessUnit.getCompany()).isEqualTo(companyBack);

        businessUnit.company(null);
        assertThat(businessUnit.getCompany()).isNull();
    }
}
