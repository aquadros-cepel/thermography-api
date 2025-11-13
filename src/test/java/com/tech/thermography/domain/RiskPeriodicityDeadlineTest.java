package com.tech.thermography.domain;

import static com.tech.thermography.domain.RiskPeriodicityDeadlineTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RiskPeriodicityDeadlineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskPeriodicityDeadline.class);
        RiskPeriodicityDeadline riskPeriodicityDeadline1 = getRiskPeriodicityDeadlineSample1();
        RiskPeriodicityDeadline riskPeriodicityDeadline2 = new RiskPeriodicityDeadline();
        assertThat(riskPeriodicityDeadline1).isNotEqualTo(riskPeriodicityDeadline2);

        riskPeriodicityDeadline2.setId(riskPeriodicityDeadline1.getId());
        assertThat(riskPeriodicityDeadline1).isEqualTo(riskPeriodicityDeadline2);

        riskPeriodicityDeadline2 = getRiskPeriodicityDeadlineSample2();
        assertThat(riskPeriodicityDeadline1).isNotEqualTo(riskPeriodicityDeadline2);
    }
}
