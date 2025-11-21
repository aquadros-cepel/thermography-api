package com.tech.thermography.domain;

import static com.tech.thermography.domain.InspectionRouteGroupEquipmentTestSamples.*;
import static com.tech.thermography.domain.InspectionRouteGroupTestSamples.*;
import static com.tech.thermography.domain.InspectionRouteGroupTestSamples.*;
import static com.tech.thermography.domain.InspectionRouteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InspectionRouteGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionRouteGroup.class);
        InspectionRouteGroup inspectionRouteGroup1 = getInspectionRouteGroupSample1();
        InspectionRouteGroup inspectionRouteGroup2 = new InspectionRouteGroup();
        assertThat(inspectionRouteGroup1).isNotEqualTo(inspectionRouteGroup2);

        inspectionRouteGroup2.setId(inspectionRouteGroup1.getId());
        assertThat(inspectionRouteGroup1).isEqualTo(inspectionRouteGroup2);

        inspectionRouteGroup2 = getInspectionRouteGroupSample2();
        assertThat(inspectionRouteGroup1).isNotEqualTo(inspectionRouteGroup2);
    }

    @Test
    void inspectionRouteTest() {
        InspectionRouteGroup inspectionRouteGroup = getInspectionRouteGroupRandomSampleGenerator();
        InspectionRoute inspectionRouteBack = getInspectionRouteRandomSampleGenerator();

        inspectionRouteGroup.setInspectionRoute(inspectionRouteBack);
        assertThat(inspectionRouteGroup.getInspectionRoute()).isEqualTo(inspectionRouteBack);

        inspectionRouteGroup.inspectionRoute(null);
        assertThat(inspectionRouteGroup.getInspectionRoute()).isNull();
    }

    @Test
    void parentGroupTest() {
        InspectionRouteGroup inspectionRouteGroup = getInspectionRouteGroupRandomSampleGenerator();
        InspectionRouteGroup inspectionRouteGroupBack = getInspectionRouteGroupRandomSampleGenerator();

        inspectionRouteGroup.setParentGroup(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroup.getParentGroup()).isEqualTo(inspectionRouteGroupBack);

        inspectionRouteGroup.parentGroup(null);
        assertThat(inspectionRouteGroup.getParentGroup()).isNull();
    }

    @Test
    void subGroupsTest() {
        InspectionRouteGroup inspectionRouteGroup = getInspectionRouteGroupRandomSampleGenerator();
        InspectionRouteGroup inspectionRouteGroupBack = getInspectionRouteGroupRandomSampleGenerator();

        inspectionRouteGroup.addSubGroups(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroup.getSubGroups()).containsOnly(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroupBack.getParentGroup()).isEqualTo(inspectionRouteGroup);

        inspectionRouteGroup.removeSubGroups(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroup.getSubGroups()).doesNotContain(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroupBack.getParentGroup()).isNull();

        inspectionRouteGroup.subGroups(new HashSet<>(Set.of(inspectionRouteGroupBack)));
        assertThat(inspectionRouteGroup.getSubGroups()).containsOnly(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroupBack.getParentGroup()).isEqualTo(inspectionRouteGroup);

        inspectionRouteGroup.setSubGroups(new HashSet<>());
        assertThat(inspectionRouteGroup.getSubGroups()).doesNotContain(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroupBack.getParentGroup()).isNull();
    }

    @Test
    void equipmentsTest() {
        InspectionRouteGroup inspectionRouteGroup = getInspectionRouteGroupRandomSampleGenerator();
        InspectionRouteGroupEquipment inspectionRouteGroupEquipmentBack = getInspectionRouteGroupEquipmentRandomSampleGenerator();

        inspectionRouteGroup.addEquipments(inspectionRouteGroupEquipmentBack);
        assertThat(inspectionRouteGroup.getEquipments()).containsOnly(inspectionRouteGroupEquipmentBack);
        assertThat(inspectionRouteGroupEquipmentBack.getInspectionRouteGroup()).isEqualTo(inspectionRouteGroup);

        inspectionRouteGroup.removeEquipments(inspectionRouteGroupEquipmentBack);
        assertThat(inspectionRouteGroup.getEquipments()).doesNotContain(inspectionRouteGroupEquipmentBack);
        assertThat(inspectionRouteGroupEquipmentBack.getInspectionRouteGroup()).isNull();

        inspectionRouteGroup.equipments(new HashSet<>(Set.of(inspectionRouteGroupEquipmentBack)));
        assertThat(inspectionRouteGroup.getEquipments()).containsOnly(inspectionRouteGroupEquipmentBack);
        assertThat(inspectionRouteGroupEquipmentBack.getInspectionRouteGroup()).isEqualTo(inspectionRouteGroup);

        inspectionRouteGroup.setEquipments(new HashSet<>());
        assertThat(inspectionRouteGroup.getEquipments()).doesNotContain(inspectionRouteGroupEquipmentBack);
        assertThat(inspectionRouteGroupEquipmentBack.getInspectionRouteGroup()).isNull();
    }
}
