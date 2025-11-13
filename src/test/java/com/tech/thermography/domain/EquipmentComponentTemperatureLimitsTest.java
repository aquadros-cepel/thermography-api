package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentComponentTemperatureLimitsTestSamples.*;
import static com.tech.thermography.domain.EquipmentComponentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EquipmentComponentTemperatureLimitsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentComponentTemperatureLimits.class);
        EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits1 = getEquipmentComponentTemperatureLimitsSample1();
        EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits2 = new EquipmentComponentTemperatureLimits();
        assertThat(equipmentComponentTemperatureLimits1).isNotEqualTo(equipmentComponentTemperatureLimits2);

        equipmentComponentTemperatureLimits2.setId(equipmentComponentTemperatureLimits1.getId());
        assertThat(equipmentComponentTemperatureLimits1).isEqualTo(equipmentComponentTemperatureLimits2);

        equipmentComponentTemperatureLimits2 = getEquipmentComponentTemperatureLimitsSample2();
        assertThat(equipmentComponentTemperatureLimits1).isNotEqualTo(equipmentComponentTemperatureLimits2);
    }

    @Test
    void equipmentComponentTest() {
        EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits =
            getEquipmentComponentTemperatureLimitsRandomSampleGenerator();
        EquipmentComponent equipmentComponentBack = getEquipmentComponentRandomSampleGenerator();

        equipmentComponentTemperatureLimits.setEquipmentComponent(equipmentComponentBack);
        assertThat(equipmentComponentTemperatureLimits.getEquipmentComponent()).isEqualTo(equipmentComponentBack);
        assertThat(equipmentComponentBack.getComponentTemperatureLimits()).isEqualTo(equipmentComponentTemperatureLimits);

        equipmentComponentTemperatureLimits.equipmentComponent(null);
        assertThat(equipmentComponentTemperatureLimits.getEquipmentComponent()).isNull();
        assertThat(equipmentComponentBack.getComponentTemperatureLimits()).isNull();
    }
}
