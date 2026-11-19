package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentComponentTestSamples.*;
import static com.tech.thermography.domain.EquipmentTestSamples.*;
import static com.tech.thermography.domain.InspectionRecordTestSamples.*;
import static com.tech.thermography.domain.PlantTestSamples.*;
import static com.tech.thermography.domain.ThermogramTestSamples.*;
import static com.tech.thermography.domain.ThermographicRecordTestSamples.*;
import static com.tech.thermography.domain.ThermographicRecordTestSamples.*;
import static com.tech.thermography.domain.UserInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ThermographicRecordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThermographicRecord.class);
        ThermographicRecord thermographicRecord1 = getThermographicRecordSample1();
        ThermographicRecord thermographicRecord2 = new ThermographicRecord();
        assertThat(thermographicRecord1).isNotEqualTo(thermographicRecord2);

        thermographicRecord2.setId(thermographicRecord1.getId());
        assertThat(thermographicRecord1).isEqualTo(thermographicRecord2);

        thermographicRecord2 = getThermographicRecordSample2();
        assertThat(thermographicRecord1).isNotEqualTo(thermographicRecord2);
    }

    @Test
    void plantTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        Plant plantBack = getPlantRandomSampleGenerator();

        thermographicRecord.setPlant(plantBack);
        assertThat(thermographicRecord.getPlant()).isEqualTo(plantBack);

        thermographicRecord.plant(null);
        assertThat(thermographicRecord.getPlant()).isNull();
    }

    @Test
    void routeTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        InspectionRecord inspectionRecordBack = getInspectionRecordRandomSampleGenerator();

        thermographicRecord.setRoute(inspectionRecordBack);
        assertThat(thermographicRecord.getRoute()).isEqualTo(inspectionRecordBack);

        thermographicRecord.route(null);
        assertThat(thermographicRecord.getRoute()).isNull();
    }

    @Test
    void equipmentTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        Equipment equipmentBack = getEquipmentRandomSampleGenerator();

        thermographicRecord.setEquipment(equipmentBack);
        assertThat(thermographicRecord.getEquipment()).isEqualTo(equipmentBack);

        thermographicRecord.equipment(null);
        assertThat(thermographicRecord.getEquipment()).isNull();
    }

    @Test
    void componentTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        EquipmentComponent equipmentComponentBack = getEquipmentComponentRandomSampleGenerator();

        thermographicRecord.setComponent(equipmentComponentBack);
        assertThat(thermographicRecord.getComponent()).isEqualTo(equipmentComponentBack);

        thermographicRecord.component(null);
        assertThat(thermographicRecord.getComponent()).isNull();
    }

    @Test
    void createdByTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        thermographicRecord.setCreatedBy(userInfoBack);
        assertThat(thermographicRecord.getCreatedBy()).isEqualTo(userInfoBack);

        thermographicRecord.createdBy(null);
        assertThat(thermographicRecord.getCreatedBy()).isNull();
    }

    @Test
    void finishedByTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        thermographicRecord.setFinishedBy(userInfoBack);
        assertThat(thermographicRecord.getFinishedBy()).isEqualTo(userInfoBack);

        thermographicRecord.finishedBy(null);
        assertThat(thermographicRecord.getFinishedBy()).isNull();
    }

    @Test
    void thermogramTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        Thermogram thermogramBack = getThermogramRandomSampleGenerator();

        thermographicRecord.setThermogram(thermogramBack);
        assertThat(thermographicRecord.getThermogram()).isEqualTo(thermogramBack);

        thermographicRecord.thermogram(null);
        assertThat(thermographicRecord.getThermogram()).isNull();
    }

    @Test
    void thermogramRefTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        Thermogram thermogramBack = getThermogramRandomSampleGenerator();

        thermographicRecord.setThermogramRef(thermogramBack);
        assertThat(thermographicRecord.getThermogramRef()).isEqualTo(thermogramBack);

        thermographicRecord.thermogramRef(null);
        assertThat(thermographicRecord.getThermogramRef()).isNull();
    }

    @Test
    void parentRecordTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        ThermographicRecord thermographicRecordBack = getThermographicRecordRandomSampleGenerator();

        thermographicRecord.setParentRecord(thermographicRecordBack);
        assertThat(thermographicRecord.getParentRecord()).isEqualTo(thermographicRecordBack);

        thermographicRecord.parentRecord(null);
        assertThat(thermographicRecord.getParentRecord()).isNull();
    }

    @Test
    void childRecordsTest() {
        ThermographicRecord thermographicRecord = getThermographicRecordRandomSampleGenerator();
        ThermographicRecord thermographicRecordBack = getThermographicRecordRandomSampleGenerator();

        thermographicRecord.addChildRecords(thermographicRecordBack);
        assertThat(thermographicRecord.getChildRecords()).containsOnly(thermographicRecordBack);
        assertThat(thermographicRecordBack.getParentRecord()).isEqualTo(thermographicRecord);

        thermographicRecord.removeChildRecords(thermographicRecordBack);
        assertThat(thermographicRecord.getChildRecords()).doesNotContain(thermographicRecordBack);
        assertThat(thermographicRecordBack.getParentRecord()).isNull();

        thermographicRecord.childRecords(new HashSet<>(Set.of(thermographicRecordBack)));
        assertThat(thermographicRecord.getChildRecords()).containsOnly(thermographicRecordBack);
        assertThat(thermographicRecordBack.getParentRecord()).isEqualTo(thermographicRecord);

        thermographicRecord.setChildRecords(new HashSet<>());
        assertThat(thermographicRecord.getChildRecords()).doesNotContain(thermographicRecordBack);
        assertThat(thermographicRecordBack.getParentRecord()).isNull();
    }
}
