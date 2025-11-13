package com.tech.thermography.domain;

import static com.tech.thermography.domain.BusinessUnitTestSamples.*;
import static com.tech.thermography.domain.CompanyTestSamples.*;
import static com.tech.thermography.domain.PlantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plant.class);
        Plant plant1 = getPlantSample1();
        Plant plant2 = new Plant();
        assertThat(plant1).isNotEqualTo(plant2);

        plant2.setId(plant1.getId());
        assertThat(plant1).isEqualTo(plant2);

        plant2 = getPlantSample2();
        assertThat(plant1).isNotEqualTo(plant2);
    }

    @Test
    void companyTest() {
        Plant plant = getPlantRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        plant.setCompany(companyBack);
        assertThat(plant.getCompany()).isEqualTo(companyBack);

        plant.company(null);
        assertThat(plant.getCompany()).isNull();
    }

    @Test
    void businessUnitTest() {
        Plant plant = getPlantRandomSampleGenerator();
        BusinessUnit businessUnitBack = getBusinessUnitRandomSampleGenerator();

        plant.setBusinessUnit(businessUnitBack);
        assertThat(plant.getBusinessUnit()).isEqualTo(businessUnitBack);

        plant.businessUnit(null);
        assertThat(plant.getBusinessUnit()).isNull();
    }
}
