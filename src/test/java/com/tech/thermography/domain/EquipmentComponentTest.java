package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentComponentTemperatureLimitsTestSamples.*;
import static com.tech.thermography.domain.EquipmentComponentTestSamples.*;
import static com.tech.thermography.domain.EquipmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipmentComponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentComponent.class);
        EquipmentComponent equipmentComponent1 = getEquipmentComponentSample1();
        EquipmentComponent equipmentComponent2 = new EquipmentComponent();
        assertThat(equipmentComponent1).isNotEqualTo(equipmentComponent2);

        equipmentComponent2.setId(equipmentComponent1.getId());
        assertThat(equipmentComponent1).isEqualTo(equipmentComponent2);

        equipmentComponent2 = getEquipmentComponentSample2();
        assertThat(equipmentComponent1).isNotEqualTo(equipmentComponent2);
    }

    @Test
    void componentTemperatureLimitsTest() {
        EquipmentComponent equipmentComponent = getEquipmentComponentRandomSampleGenerator();
        EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimitsBack =
            getEquipmentComponentTemperatureLimitsRandomSampleGenerator();

        equipmentComponent.setComponentTemperatureLimits(equipmentComponentTemperatureLimitsBack);
        assertThat(equipmentComponent.getComponentTemperatureLimits()).isEqualTo(equipmentComponentTemperatureLimitsBack);

        equipmentComponent.componentTemperatureLimits(null);
        assertThat(equipmentComponent.getComponentTemperatureLimits()).isNull();
    }

    @Test
    void equipmentsTest() {
        EquipmentComponent equipmentComponent = getEquipmentComponentRandomSampleGenerator();
        Equipment equipmentBack = getEquipmentRandomSampleGenerator();

        equipmentComponent.addEquipments(equipmentBack);
        assertThat(equipmentComponent.getEquipments()).containsOnly(equipmentBack);

        equipmentComponent.removeEquipments(equipmentBack);
        assertThat(equipmentComponent.getEquipments()).doesNotContain(equipmentBack);

        equipmentComponent.equipments(new HashSet<>(Set.of(equipmentBack)));
        assertThat(equipmentComponent.getEquipments()).containsOnly(equipmentBack);

        equipmentComponent.setEquipments(new HashSet<>());
        assertThat(equipmentComponent.getEquipments()).doesNotContain(equipmentBack);
    }
}
