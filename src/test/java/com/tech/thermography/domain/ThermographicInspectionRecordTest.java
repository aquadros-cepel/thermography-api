package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentComponentTestSamples.*;
import static com.tech.thermography.domain.EquipmentTestSamples.*;
import static com.tech.thermography.domain.InspectionRecordTestSamples.*;
import static com.tech.thermography.domain.PlantTestSamples.*;
import static com.tech.thermography.domain.ThermogramTestSamples.*;
import static com.tech.thermography.domain.ThermographicInspectionRecordTestSamples.*;
import static com.tech.thermography.domain.UserInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThermographicInspectionRecordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThermographicInspectionRecord.class);
        ThermographicInspectionRecord thermographicInspectionRecord1 = getThermographicInspectionRecordSample1();
        ThermographicInspectionRecord thermographicInspectionRecord2 = new ThermographicInspectionRecord();
        assertThat(thermographicInspectionRecord1).isNotEqualTo(thermographicInspectionRecord2);

        thermographicInspectionRecord2.setId(thermographicInspectionRecord1.getId());
        assertThat(thermographicInspectionRecord1).isEqualTo(thermographicInspectionRecord2);

        thermographicInspectionRecord2 = getThermographicInspectionRecordSample2();
        assertThat(thermographicInspectionRecord1).isNotEqualTo(thermographicInspectionRecord2);
    }

    @Test
    void plantTest() {
        ThermographicInspectionRecord thermographicInspectionRecord = getThermographicInspectionRecordRandomSampleGenerator();
        Plant plantBack = getPlantRandomSampleGenerator();

        thermographicInspectionRecord.setPlant(plantBack);
        assertThat(thermographicInspectionRecord.getPlant()).isEqualTo(plantBack);

        thermographicInspectionRecord.plant(null);
        assertThat(thermographicInspectionRecord.getPlant()).isNull();
    }

    @Test
    void routeTest() {
        ThermographicInspectionRecord thermographicInspectionRecord = getThermographicInspectionRecordRandomSampleGenerator();
        InspectionRecord inspectionRecordBack = getInspectionRecordRandomSampleGenerator();

        thermographicInspectionRecord.setRoute(inspectionRecordBack);
        assertThat(thermographicInspectionRecord.getRoute()).isEqualTo(inspectionRecordBack);

        thermographicInspectionRecord.route(null);
        assertThat(thermographicInspectionRecord.getRoute()).isNull();
    }

    @Test
    void equipmentTest() {
        ThermographicInspectionRecord thermographicInspectionRecord = getThermographicInspectionRecordRandomSampleGenerator();
        Equipment equipmentBack = getEquipmentRandomSampleGenerator();

        thermographicInspectionRecord.setEquipment(equipmentBack);
        assertThat(thermographicInspectionRecord.getEquipment()).isEqualTo(equipmentBack);

        thermographicInspectionRecord.equipment(null);
        assertThat(thermographicInspectionRecord.getEquipment()).isNull();
    }

    @Test
    void componentTest() {
        ThermographicInspectionRecord thermographicInspectionRecord = getThermographicInspectionRecordRandomSampleGenerator();
        EquipmentComponent equipmentComponentBack = getEquipmentComponentRandomSampleGenerator();

        thermographicInspectionRecord.setComponent(equipmentComponentBack);
        assertThat(thermographicInspectionRecord.getComponent()).isEqualTo(equipmentComponentBack);

        thermographicInspectionRecord.component(null);
        assertThat(thermographicInspectionRecord.getComponent()).isNull();
    }

    @Test
    void createdByTest() {
        ThermographicInspectionRecord thermographicInspectionRecord = getThermographicInspectionRecordRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        thermographicInspectionRecord.setCreatedBy(userInfoBack);
        assertThat(thermographicInspectionRecord.getCreatedBy()).isEqualTo(userInfoBack);

        thermographicInspectionRecord.createdBy(null);
        assertThat(thermographicInspectionRecord.getCreatedBy()).isNull();
    }

    @Test
    void finishedByTest() {
        ThermographicInspectionRecord thermographicInspectionRecord = getThermographicInspectionRecordRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        thermographicInspectionRecord.setFinishedBy(userInfoBack);
        assertThat(thermographicInspectionRecord.getFinishedBy()).isEqualTo(userInfoBack);

        thermographicInspectionRecord.finishedBy(null);
        assertThat(thermographicInspectionRecord.getFinishedBy()).isNull();
    }

    @Test
    void thermogramTest() {
        ThermographicInspectionRecord thermographicInspectionRecord = getThermographicInspectionRecordRandomSampleGenerator();
        Thermogram thermogramBack = getThermogramRandomSampleGenerator();

        thermographicInspectionRecord.setThermogram(thermogramBack);
        assertThat(thermographicInspectionRecord.getThermogram()).isEqualTo(thermogramBack);

        thermographicInspectionRecord.thermogram(null);
        assertThat(thermographicInspectionRecord.getThermogram()).isNull();
    }

    @Test
    void thermogramRefTest() {
        ThermographicInspectionRecord thermographicInspectionRecord = getThermographicInspectionRecordRandomSampleGenerator();
        Thermogram thermogramBack = getThermogramRandomSampleGenerator();

        thermographicInspectionRecord.setThermogramRef(thermogramBack);
        assertThat(thermographicInspectionRecord.getThermogramRef()).isEqualTo(thermogramBack);

        thermographicInspectionRecord.thermogramRef(null);
        assertThat(thermographicInspectionRecord.getThermogramRef()).isNull();
    }
}
