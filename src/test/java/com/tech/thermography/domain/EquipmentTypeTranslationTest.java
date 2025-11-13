package com.tech.thermography.domain;

import static com.tech.thermography.domain.EquipmentTypeTranslationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EquipmentTypeTranslationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentTypeTranslation.class);
        EquipmentTypeTranslation equipmentTypeTranslation1 = getEquipmentTypeTranslationSample1();
        EquipmentTypeTranslation equipmentTypeTranslation2 = new EquipmentTypeTranslation();
        assertThat(equipmentTypeTranslation1).isNotEqualTo(equipmentTypeTranslation2);

        equipmentTypeTranslation2.setId(equipmentTypeTranslation1.getId());
        assertThat(equipmentTypeTranslation1).isEqualTo(equipmentTypeTranslation2);

        equipmentTypeTranslation2 = getEquipmentTypeTranslationSample2();
        assertThat(equipmentTypeTranslation1).isNotEqualTo(equipmentTypeTranslation2);
    }
}
