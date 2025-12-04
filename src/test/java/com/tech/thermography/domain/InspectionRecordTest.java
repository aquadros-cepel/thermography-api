package com.tech.thermography.domain;

import static com.tech.thermography.domain.InspectionRecordGroupTestSamples.*;
import static com.tech.thermography.domain.InspectionRecordTestSamples.*;
import static com.tech.thermography.domain.InspectionRouteTestSamples.*;
import static com.tech.thermography.domain.PlantTestSamples.*;
import static com.tech.thermography.domain.UserInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InspectionRecordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionRecord.class);
        InspectionRecord inspectionRecord1 = getInspectionRecordSample1();
        InspectionRecord inspectionRecord2 = new InspectionRecord();
        assertThat(inspectionRecord1).isNotEqualTo(inspectionRecord2);

        inspectionRecord2.setId(inspectionRecord1.getId());
        assertThat(inspectionRecord1).isEqualTo(inspectionRecord2);

        inspectionRecord2 = getInspectionRecordSample2();
        assertThat(inspectionRecord1).isNotEqualTo(inspectionRecord2);
    }

    @Test
    void plantTest() {
        InspectionRecord inspectionRecord = getInspectionRecordRandomSampleGenerator();
        Plant plantBack = getPlantRandomSampleGenerator();

        inspectionRecord.setPlant(plantBack);
        assertThat(inspectionRecord.getPlant()).isEqualTo(plantBack);

        inspectionRecord.plant(null);
        assertThat(inspectionRecord.getPlant()).isNull();
    }

    @Test
    void inspectionRouteTest() {
        InspectionRecord inspectionRecord = getInspectionRecordRandomSampleGenerator();
        InspectionRoute inspectionRouteBack = getInspectionRouteRandomSampleGenerator();

        inspectionRecord.setInspectionRoute(inspectionRouteBack);
        assertThat(inspectionRecord.getInspectionRoute()).isEqualTo(inspectionRouteBack);

        inspectionRecord.inspectionRoute(null);
        assertThat(inspectionRecord.getInspectionRoute()).isNull();
    }

    @Test
    void createdByTest() {
        InspectionRecord inspectionRecord = getInspectionRecordRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        inspectionRecord.setCreatedBy(userInfoBack);
        assertThat(inspectionRecord.getCreatedBy()).isEqualTo(userInfoBack);

        inspectionRecord.createdBy(null);
        assertThat(inspectionRecord.getCreatedBy()).isNull();
    }

    @Test
    void startedByTest() {
        InspectionRecord inspectionRecord = getInspectionRecordRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        inspectionRecord.setStartedBy(userInfoBack);
        assertThat(inspectionRecord.getStartedBy()).isEqualTo(userInfoBack);

        inspectionRecord.startedBy(null);
        assertThat(inspectionRecord.getStartedBy()).isNull();
    }

    @Test
    void finishedByTest() {
        InspectionRecord inspectionRecord = getInspectionRecordRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        inspectionRecord.setFinishedBy(userInfoBack);
        assertThat(inspectionRecord.getFinishedBy()).isEqualTo(userInfoBack);

        inspectionRecord.finishedBy(null);
        assertThat(inspectionRecord.getFinishedBy()).isNull();
    }

    @Test
    void groupsTest() {
        InspectionRecord inspectionRecord = getInspectionRecordRandomSampleGenerator();
        InspectionRecordGroup inspectionRecordGroupBack = getInspectionRecordGroupRandomSampleGenerator();

        inspectionRecord.addGroups(inspectionRecordGroupBack);
        assertThat(inspectionRecord.getGroups()).containsOnly(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroupBack.getInspectionRecord()).isEqualTo(inspectionRecord);

        inspectionRecord.removeGroups(inspectionRecordGroupBack);
        assertThat(inspectionRecord.getGroups()).doesNotContain(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroupBack.getInspectionRecord()).isNull();

        inspectionRecord.groups(new HashSet<>(Set.of(inspectionRecordGroupBack)));
        assertThat(inspectionRecord.getGroups()).containsOnly(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroupBack.getInspectionRecord()).isEqualTo(inspectionRecord);

        inspectionRecord.setGroups(new HashSet<>());
        assertThat(inspectionRecord.getGroups()).doesNotContain(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroupBack.getInspectionRecord()).isNull();
    }
}
