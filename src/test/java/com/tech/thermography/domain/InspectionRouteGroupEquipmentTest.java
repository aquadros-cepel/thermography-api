package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentTestSamples.*;
import static com.tech.thermography.domain.InspectionRouteGroupEquipmentTestSamples.*;
import static com.tech.thermography.domain.InspectionRouteGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InspectionRouteGroupEquipmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionRouteGroupEquipment.class);
        InspectionRouteGroupEquipment inspectionRouteGroupEquipment1 = getInspectionRouteGroupEquipmentSample1();
        InspectionRouteGroupEquipment inspectionRouteGroupEquipment2 = new InspectionRouteGroupEquipment();
        assertThat(inspectionRouteGroupEquipment1).isNotEqualTo(inspectionRouteGroupEquipment2);

        inspectionRouteGroupEquipment2.setId(inspectionRouteGroupEquipment1.getId());
        assertThat(inspectionRouteGroupEquipment1).isEqualTo(inspectionRouteGroupEquipment2);

        inspectionRouteGroupEquipment2 = getInspectionRouteGroupEquipmentSample2();
        assertThat(inspectionRouteGroupEquipment1).isNotEqualTo(inspectionRouteGroupEquipment2);
    }

    @Test
    void inspectionRouteGroupTest() {
        InspectionRouteGroupEquipment inspectionRouteGroupEquipment = getInspectionRouteGroupEquipmentRandomSampleGenerator();
        InspectionRouteGroup inspectionRouteGroupBack = getInspectionRouteGroupRandomSampleGenerator();

        inspectionRouteGroupEquipment.setInspectionRouteGroup(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroupEquipment.getInspectionRouteGroup()).isEqualTo(inspectionRouteGroupBack);

        inspectionRouteGroupEquipment.inspectionRouteGroup(null);
        assertThat(inspectionRouteGroupEquipment.getInspectionRouteGroup()).isNull();
    }

    @Test
    void equipmentTest() {
        InspectionRouteGroupEquipment inspectionRouteGroupEquipment = getInspectionRouteGroupEquipmentRandomSampleGenerator();
        Equipment equipmentBack = getEquipmentRandomSampleGenerator();

        inspectionRouteGroupEquipment.setEquipment(equipmentBack);
        assertThat(inspectionRouteGroupEquipment.getEquipment()).isEqualTo(equipmentBack);

        inspectionRouteGroupEquipment.equipment(null);
        assertThat(inspectionRouteGroupEquipment.getEquipment()).isNull();
    }
}
