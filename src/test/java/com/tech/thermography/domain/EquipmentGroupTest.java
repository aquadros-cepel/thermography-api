package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentGroupTestSamples.*;
import static com.tech.thermography.domain.EquipmentGroupTestSamples.*;
import static com.tech.thermography.domain.PlantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipmentGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentGroup.class);
        EquipmentGroup equipmentGroup1 = getEquipmentGroupSample1();
        EquipmentGroup equipmentGroup2 = new EquipmentGroup();
        assertThat(equipmentGroup1).isNotEqualTo(equipmentGroup2);

        equipmentGroup2.setId(equipmentGroup1.getId());
        assertThat(equipmentGroup1).isEqualTo(equipmentGroup2);

        equipmentGroup2 = getEquipmentGroupSample2();
        assertThat(equipmentGroup1).isNotEqualTo(equipmentGroup2);
    }

    @Test
    void plantTest() {
        EquipmentGroup equipmentGroup = getEquipmentGroupRandomSampleGenerator();
        Plant plantBack = getPlantRandomSampleGenerator();

        equipmentGroup.setPlant(plantBack);
        assertThat(equipmentGroup.getPlant()).isEqualTo(plantBack);

        equipmentGroup.plant(null);
        assertThat(equipmentGroup.getPlant()).isNull();
    }

    @Test
    void parentGroupTest() {
        EquipmentGroup equipmentGroup = getEquipmentGroupRandomSampleGenerator();
        EquipmentGroup equipmentGroupBack = getEquipmentGroupRandomSampleGenerator();

        equipmentGroup.setParentGroup(equipmentGroupBack);
        assertThat(equipmentGroup.getParentGroup()).isEqualTo(equipmentGroupBack);

        equipmentGroup.parentGroup(null);
        assertThat(equipmentGroup.getParentGroup()).isNull();
    }

    @Test
    void subGroupsTest() {
        EquipmentGroup equipmentGroup = getEquipmentGroupRandomSampleGenerator();
        EquipmentGroup equipmentGroupBack = getEquipmentGroupRandomSampleGenerator();

        equipmentGroup.addSubGroups(equipmentGroupBack);
        assertThat(equipmentGroup.getSubGroups()).containsOnly(equipmentGroupBack);
        assertThat(equipmentGroupBack.getParentGroup()).isEqualTo(equipmentGroup);

        equipmentGroup.removeSubGroups(equipmentGroupBack);
        assertThat(equipmentGroup.getSubGroups()).doesNotContain(equipmentGroupBack);
        assertThat(equipmentGroupBack.getParentGroup()).isNull();

        equipmentGroup.subGroups(new HashSet<>(Set.of(equipmentGroupBack)));
        assertThat(equipmentGroup.getSubGroups()).containsOnly(equipmentGroupBack);
        assertThat(equipmentGroupBack.getParentGroup()).isEqualTo(equipmentGroup);

        equipmentGroup.setSubGroups(new HashSet<>());
        assertThat(equipmentGroup.getSubGroups()).doesNotContain(equipmentGroupBack);
        assertThat(equipmentGroupBack.getParentGroup()).isNull();
    }
}
