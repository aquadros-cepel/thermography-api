package com.tech.thermography.domain;

import static com.tech.thermography.domain.ROITestSamples.*;
import static com.tech.thermography.domain.ThermogramTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ROITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ROI.class);
        ROI rOI1 = getROISample1();
        ROI rOI2 = new ROI();
        assertThat(rOI1).isNotEqualTo(rOI2);

        rOI2.setId(rOI1.getId());
        assertThat(rOI1).isEqualTo(rOI2);

        rOI2 = getROISample2();
        assertThat(rOI1).isNotEqualTo(rOI2);
    }

    @Test
    void thermogramTest() {
        ROI rOI = getROIRandomSampleGenerator();
        Thermogram thermogramBack = getThermogramRandomSampleGenerator();

        rOI.setThermogram(thermogramBack);
        assertThat(rOI.getThermogram()).isEqualTo(thermogramBack);

        rOI.thermogram(null);
        assertThat(rOI.getThermogram()).isNull();
    }
}
