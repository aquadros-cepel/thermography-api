package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentComponentTestSamples.*;
import static com.tech.thermography.domain.EquipmentGroupTestSamples.*;
import static com.tech.thermography.domain.EquipmentTestSamples.*;
import static com.tech.thermography.domain.PlantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipment.class);
        Equipment equipment1 = getEquipmentSample1();
        Equipment equipment2 = new Equipment();
        assertThat(equipment1).isNotEqualTo(equipment2);

        equipment2.setId(equipment1.getId());
        assertThat(equipment1).isEqualTo(equipment2);

        equipment2 = getEquipmentSample2();
        assertThat(equipment1).isNotEqualTo(equipment2);
    }

    @Test
    void plantTest() {
        Equipment equipment = getEquipmentRandomSampleGenerator();
        Plant plantBack = getPlantRandomSampleGenerator();

        equipment.setPlant(plantBack);
        assertThat(equipment.getPlant()).isEqualTo(plantBack);

        equipment.plant(null);
        assertThat(equipment.getPlant()).isNull();
    }

    @Test
    void groupTest() {
        Equipment equipment = getEquipmentRandomSampleGenerator();
        EquipmentGroup equipmentGroupBack = getEquipmentGroupRandomSampleGenerator();

        equipment.setGroup(equipmentGroupBack);
        assertThat(equipment.getGroup()).isEqualTo(equipmentGroupBack);

        equipment.group(null);
        assertThat(equipment.getGroup()).isNull();
    }

    @Test
    void componentsTest() {
        Equipment equipment = getEquipmentRandomSampleGenerator();
        EquipmentComponent equipmentComponentBack = getEquipmentComponentRandomSampleGenerator();

        equipment.addComponents(equipmentComponentBack);
        assertThat(equipment.getComponents()).containsOnly(equipmentComponentBack);
        assertThat(equipmentComponentBack.getEquipments()).containsOnly(equipment);

        equipment.removeComponents(equipmentComponentBack);
        assertThat(equipment.getComponents()).doesNotContain(equipmentComponentBack);
        assertThat(equipmentComponentBack.getEquipments()).doesNotContain(equipment);

        equipment.components(new HashSet<>(Set.of(equipmentComponentBack)));
        assertThat(equipment.getComponents()).containsOnly(equipmentComponentBack);
        assertThat(equipmentComponentBack.getEquipments()).containsOnly(equipment);

        equipment.setComponents(new HashSet<>());
        assertThat(equipment.getComponents()).doesNotContain(equipmentComponentBack);
        assertThat(equipmentComponentBack.getEquipments()).doesNotContain(equipment);
    }
}
