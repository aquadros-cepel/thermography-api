package com.tech.thermography.domain;

import static com.tech.thermography.domain.InspectionRouteGroupTestSamples.*;
import static com.tech.thermography.domain.InspectionRouteGroupTestSamples.*;
import static com.tech.thermography.domain.InspectionRouteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tech.thermography.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InspectionRouteGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionRouteGroup.class);
        InspectionRouteGroup inspectionRouteGroup1 = getInspectionRouteGroupSample1();
        InspectionRouteGroup inspectionRouteGroup2 = new InspectionRouteGroup();
        assertThat(inspectionRouteGroup1).isNotEqualTo(inspectionRouteGroup2);

        inspectionRouteGroup2.setId(inspectionRouteGroup1.getId());
        assertThat(inspectionRouteGroup1).isEqualTo(inspectionRouteGroup2);

        inspectionRouteGroup2 = getInspectionRouteGroupSample2();
        assertThat(inspectionRouteGroup1).isNotEqualTo(inspectionRouteGroup2);
    }

    @Test
    void inspectionRouteTest() {
        InspectionRouteGroup inspectionRouteGroup = getInspectionRouteGroupRandomSampleGenerator();
        InspectionRoute inspectionRouteBack = getInspectionRouteRandomSampleGenerator();

        inspectionRouteGroup.setInspectionRoute(inspectionRouteBack);
        assertThat(inspectionRouteGroup.getInspectionRoute()).isEqualTo(inspectionRouteBack);

        inspectionRouteGroup.inspectionRoute(null);
        assertThat(inspectionRouteGroup.getInspectionRoute()).isNull();
    }

    @Test
    void subGroupTest() {
        InspectionRouteGroup inspectionRouteGroup = getInspectionRouteGroupRandomSampleGenerator();
        InspectionRouteGroup inspectionRouteGroupBack = getInspectionRouteGroupRandomSampleGenerator();

        inspectionRouteGroup.setSubGroup(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroup.getSubGroup()).isEqualTo(inspectionRouteGroupBack);

        inspectionRouteGroup.subGroup(null);
        assertThat(inspectionRouteGroup.getSubGroup()).isNull();
    }

    @Test
    void parentGroupTest() {
        InspectionRouteGroup inspectionRouteGroup = getInspectionRouteGroupRandomSampleGenerator();
        InspectionRouteGroup inspectionRouteGroupBack = getInspectionRouteGroupRandomSampleGenerator();

        inspectionRouteGroup.addParentGroup(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroup.getParentGroups()).containsOnly(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroupBack.getSubGroup()).isEqualTo(inspectionRouteGroup);

        inspectionRouteGroup.removeParentGroup(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroup.getParentGroups()).doesNotContain(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroupBack.getSubGroup()).isNull();

        inspectionRouteGroup.parentGroups(new HashSet<>(Set.of(inspectionRouteGroupBack)));
        assertThat(inspectionRouteGroup.getParentGroups()).containsOnly(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroupBack.getSubGroup()).isEqualTo(inspectionRouteGroup);

        inspectionRouteGroup.setParentGroups(new HashSet<>());
        assertThat(inspectionRouteGroup.getParentGroups()).doesNotContain(inspectionRouteGroupBack);
        assertThat(inspectionRouteGroupBack.getSubGroup()).isNull();
    }
}
