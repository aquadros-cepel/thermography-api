package com.tech.thermography.domain;

import static com.tech.thermography.domain.InspectionRouteTestSamples.*;
import static com.tech.thermography.domain.PlantTestSamples.*;
import static com.tech.thermography.domain.UserInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InspectionRouteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionRoute.class);
        InspectionRoute inspectionRoute1 = getInspectionRouteSample1();
        InspectionRoute inspectionRoute2 = new InspectionRoute();
        assertThat(inspectionRoute1).isNotEqualTo(inspectionRoute2);

        inspectionRoute2.setId(inspectionRoute1.getId());
        assertThat(inspectionRoute1).isEqualTo(inspectionRoute2);

        inspectionRoute2 = getInspectionRouteSample2();
        assertThat(inspectionRoute1).isNotEqualTo(inspectionRoute2);
    }

    @Test
    void plantTest() {
        InspectionRoute inspectionRoute = getInspectionRouteRandomSampleGenerator();
        Plant plantBack = getPlantRandomSampleGenerator();

        inspectionRoute.setPlant(plantBack);
        assertThat(inspectionRoute.getPlant()).isEqualTo(plantBack);

        inspectionRoute.plant(null);
        assertThat(inspectionRoute.getPlant()).isNull();
    }

    @Test
    void createdByTest() {
        InspectionRoute inspectionRoute = getInspectionRouteRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        inspectionRoute.setCreatedBy(userInfoBack);
        assertThat(inspectionRoute.getCreatedBy()).isEqualTo(userInfoBack);

        inspectionRoute.createdBy(null);
        assertThat(inspectionRoute.getCreatedBy()).isNull();
    }
}
