package com.tech.thermography.domain;

import static com.tech.thermography.domain.InspectionRecordGroupEquipmentTestSamples.*;
import static com.tech.thermography.domain.InspectionRecordGroupTestSamples.*;
import static com.tech.thermography.domain.InspectionRecordGroupTestSamples.*;
import static com.tech.thermography.domain.InspectionRecordTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InspectionRecordGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionRecordGroup.class);
        InspectionRecordGroup inspectionRecordGroup1 = getInspectionRecordGroupSample1();
        InspectionRecordGroup inspectionRecordGroup2 = new InspectionRecordGroup();
        assertThat(inspectionRecordGroup1).isNotEqualTo(inspectionRecordGroup2);

        inspectionRecordGroup2.setId(inspectionRecordGroup1.getId());
        assertThat(inspectionRecordGroup1).isEqualTo(inspectionRecordGroup2);

        inspectionRecordGroup2 = getInspectionRecordGroupSample2();
        assertThat(inspectionRecordGroup1).isNotEqualTo(inspectionRecordGroup2);
    }

    @Test
    void inspectionRecordTest() {
        InspectionRecordGroup inspectionRecordGroup = getInspectionRecordGroupRandomSampleGenerator();
        InspectionRecord inspectionRecordBack = getInspectionRecordRandomSampleGenerator();

        inspectionRecordGroup.setInspectionRecord(inspectionRecordBack);
        assertThat(inspectionRecordGroup.getInspectionRecord()).isEqualTo(inspectionRecordBack);

        inspectionRecordGroup.inspectionRecord(null);
        assertThat(inspectionRecordGroup.getInspectionRecord()).isNull();
    }

    @Test
    void parentGroupTest() {
        InspectionRecordGroup inspectionRecordGroup = getInspectionRecordGroupRandomSampleGenerator();
        InspectionRecordGroup inspectionRecordGroupBack = getInspectionRecordGroupRandomSampleGenerator();

        inspectionRecordGroup.setParentGroup(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroup.getParentGroup()).isEqualTo(inspectionRecordGroupBack);

        inspectionRecordGroup.parentGroup(null);
        assertThat(inspectionRecordGroup.getParentGroup()).isNull();
    }

    @Test
    void subGroupsTest() {
        InspectionRecordGroup inspectionRecordGroup = getInspectionRecordGroupRandomSampleGenerator();
        InspectionRecordGroup inspectionRecordGroupBack = getInspectionRecordGroupRandomSampleGenerator();

        inspectionRecordGroup.addSubGroups(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroup.getSubGroups()).containsOnly(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroupBack.getParentGroup()).isEqualTo(inspectionRecordGroup);

        inspectionRecordGroup.removeSubGroups(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroup.getSubGroups()).doesNotContain(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroupBack.getParentGroup()).isNull();

        inspectionRecordGroup.subGroups(new HashSet<>(Set.of(inspectionRecordGroupBack)));
        assertThat(inspectionRecordGroup.getSubGroups()).containsOnly(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroupBack.getParentGroup()).isEqualTo(inspectionRecordGroup);

        inspectionRecordGroup.setSubGroups(new HashSet<>());
        assertThat(inspectionRecordGroup.getSubGroups()).doesNotContain(inspectionRecordGroupBack);
        assertThat(inspectionRecordGroupBack.getParentGroup()).isNull();
    }

    @Test
    void equipmentsTest() {
        InspectionRecordGroup inspectionRecordGroup = getInspectionRecordGroupRandomSampleGenerator();
        InspectionRecordGroupEquipment inspectionRecordGroupEquipmentBack = getInspectionRecordGroupEquipmentRandomSampleGenerator();

        inspectionRecordGroup.addEquipments(inspectionRecordGroupEquipmentBack);
        assertThat(inspectionRecordGroup.getEquipments()).containsOnly(inspectionRecordGroupEquipmentBack);
        assertThat(inspectionRecordGroupEquipmentBack.getInspectionRecordGroup()).isEqualTo(inspectionRecordGroup);

        inspectionRecordGroup.removeEquipments(inspectionRecordGroupEquipmentBack);
        assertThat(inspectionRecordGroup.getEquipments()).doesNotContain(inspectionRecordGroupEquipmentBack);
        assertThat(inspectionRecordGroupEquipmentBack.getInspectionRecordGroup()).isNull();

        inspectionRecordGroup.equipments(new HashSet<>(Set.of(inspectionRecordGroupEquipmentBack)));
        assertThat(inspectionRecordGroup.getEquipments()).containsOnly(inspectionRecordGroupEquipmentBack);
        assertThat(inspectionRecordGroupEquipmentBack.getInspectionRecordGroup()).isEqualTo(inspectionRecordGroup);

        inspectionRecordGroup.setEquipments(new HashSet<>());
        assertThat(inspectionRecordGroup.getEquipments()).doesNotContain(inspectionRecordGroupEquipmentBack);
        assertThat(inspectionRecordGroupEquipmentBack.getInspectionRecordGroup()).isNull();
    }
}
