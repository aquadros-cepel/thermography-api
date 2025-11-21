package com.tech.thermography.domain;

import static com.tech.thermography.domain.InspectionRouteRecordTestSamples.*;
import static com.tech.thermography.domain.UserInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InspectionRouteRecordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionRouteRecord.class);
        InspectionRouteRecord inspectionRouteRecord1 = getInspectionRouteRecordSample1();
        InspectionRouteRecord inspectionRouteRecord2 = new InspectionRouteRecord();
        assertThat(inspectionRouteRecord1).isNotEqualTo(inspectionRouteRecord2);

        inspectionRouteRecord2.setId(inspectionRouteRecord1.getId());
        assertThat(inspectionRouteRecord1).isEqualTo(inspectionRouteRecord2);

        inspectionRouteRecord2 = getInspectionRouteRecordSample2();
        assertThat(inspectionRouteRecord1).isNotEqualTo(inspectionRouteRecord2);
    }

    @Test
    void startedByTest() {
        InspectionRouteRecord inspectionRouteRecord = getInspectionRouteRecordRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        inspectionRouteRecord.setStartedBy(userInfoBack);
        assertThat(inspectionRouteRecord.getStartedBy()).isEqualTo(userInfoBack);

        inspectionRouteRecord.startedBy(null);
        assertThat(inspectionRouteRecord.getStartedBy()).isNull();
    }

    @Test
    void finishedByTest() {
        InspectionRouteRecord inspectionRouteRecord = getInspectionRouteRecordRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        inspectionRouteRecord.setFinishedBy(userInfoBack);
        assertThat(inspectionRouteRecord.getFinishedBy()).isEqualTo(userInfoBack);

        inspectionRouteRecord.finishedBy(null);
        assertThat(inspectionRouteRecord.getFinishedBy()).isNull();
    }
}
