package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentTestSamples.*;
import static com.tech.thermography.domain.InspectionRecordGroupEquipmentTestSamples.*;
import static com.tech.thermography.domain.InspectionRecordGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InspectionRecordGroupEquipmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionRecordGroupEquipment.class);
        InspectionRecordGroupEquipment inspectionRecordGroupEquipment1 = getInspectionRecordGroupEquipmentSample1();
        InspectionRecordGroupEquipment inspectionRecordGroupEquipment2 = new InspectionRecordGroupEquipment();
        assertThat(inspectionRecordGroupEquipment1).isNotEqualTo(inspectionRecordGroupEquipment2);

        inspectionRecordGroupEquipment2.setId(inspectionRecordGroupEquipment1.getId());
        assertThat(inspectionRecordGroupEquipment1).isEqualTo(inspectionRecordGroupEquipment2);

        inspectionRecordGroupEquipment2 = getInspectionRecordGroupEquipmentSample2();
        assertThat(inspectionRecordGroupEquipment1).isNotEqualTo(inspectionRecordGroupEquipment2);
    }

    @Test
    void inspectionRecordGroupTest() {
        InspectionRecordGroupEquipment inspectionRecordGroupEquipment = getInspectionRecordGroupEquipmentRandomSampleGenerator();
        InspectionRecordGroup inspectionRecordGroupBack = getInspectionRecordGroupRandomSampleGenerator();

        inspectionRecordGroupEquipment.setInspectionRecordGroup(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroupEquipment.getInspectionRecordGroup()).isEqualTo(inspectionRecordGroupBack);

        inspectionRecordGroupEquipment.inspectionRecordGroup(null);
        assertThat(inspectionRecordGroupEquipment.getInspectionRecordGroup()).isNull();
    }

    @Test
    void equipmentTest() {
        InspectionRecordGroupEquipment inspectionRecordGroupEquipment = getInspectionRecordGroupEquipmentRandomSampleGenerator();
        Equipment equipmentBack = getEquipmentRandomSampleGenerator();

        inspectionRecordGroupEquipment.setEquipment(equipmentBack);
        assertThat(inspectionRecordGroupEquipment.getEquipment()).isEqualTo(equipmentBack);

        inspectionRecordGroupEquipment.equipment(null);
        assertThat(inspectionRecordGroupEquipment.getEquipment()).isNull();
    }
}
