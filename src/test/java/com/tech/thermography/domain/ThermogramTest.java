package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentTestSamples.*;
import static com.tech.thermography.domain.ThermogramTestSamples.*;
import static com.tech.thermography.domain.UserInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThermogramTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thermogram.class);
        Thermogram thermogram1 = getThermogramSample1();
        Thermogram thermogram2 = new Thermogram();
        assertThat(thermogram1).isNotEqualTo(thermogram2);

        thermogram2.setId(thermogram1.getId());
        assertThat(thermogram1).isEqualTo(thermogram2);

        thermogram2 = getThermogramSample2();
        assertThat(thermogram1).isNotEqualTo(thermogram2);
    }

    @Test
    void equipmentTest() {
        Thermogram thermogram = getThermogramRandomSampleGenerator();
        Equipment equipmentBack = getEquipmentRandomSampleGenerator();

        thermogram.setEquipment(equipmentBack);
        assertThat(thermogram.getEquipment()).isEqualTo(equipmentBack);

        thermogram.equipment(null);
        assertThat(thermogram.getEquipment()).isNull();
    }

    @Test
    void createdByTest() {
        Thermogram thermogram = getThermogramRandomSampleGenerator();
        UserInfo userInfoBack = getUserInfoRandomSampleGenerator();

        thermogram.setCreatedBy(userInfoBack);
        assertThat(thermogram.getCreatedBy()).isEqualTo(userInfoBack);

        thermogram.createdBy(null);
        assertThat(thermogram.getCreatedBy()).isNull();
    }
}
