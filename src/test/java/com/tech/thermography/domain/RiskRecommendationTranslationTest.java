package com.tech.thermography.domain;

import static com.tech.thermography.domain.RiskPeriodicityDeadlineTestSamples.*;
import static com.tech.thermography.domain.RiskRecommendationTranslationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RiskRecommendationTranslationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskRecommendationTranslation.class);
        RiskRecommendationTranslation riskRecommendationTranslation1 = getRiskRecommendationTranslationSample1();
        RiskRecommendationTranslation riskRecommendationTranslation2 = new RiskRecommendationTranslation();
        assertThat(riskRecommendationTranslation1).isNotEqualTo(riskRecommendationTranslation2);

        riskRecommendationTranslation2.setId(riskRecommendationTranslation1.getId());
        assertThat(riskRecommendationTranslation1).isEqualTo(riskRecommendationTranslation2);

        riskRecommendationTranslation2 = getRiskRecommendationTranslationSample2();
        assertThat(riskRecommendationTranslation1).isNotEqualTo(riskRecommendationTranslation2);
    }

    @Test
    void riskPeriodicityDeadlineTest() {
        RiskRecommendationTranslation riskRecommendationTranslation = getRiskRecommendationTranslationRandomSampleGenerator();
        RiskPeriodicityDeadline riskPeriodicityDeadlineBack = getRiskPeriodicityDeadlineRandomSampleGenerator();

        riskRecommendationTranslation.setRiskPeriodicityDeadline(riskPeriodicityDeadlineBack);
        assertThat(riskRecommendationTranslation.getRiskPeriodicityDeadline()).isEqualTo(riskPeriodicityDeadlineBack);

        riskRecommendationTranslation.riskPeriodicityDeadline(null);
        assertThat(riskRecommendationTranslation.getRiskPeriodicityDeadline()).isNull();
    }
}
