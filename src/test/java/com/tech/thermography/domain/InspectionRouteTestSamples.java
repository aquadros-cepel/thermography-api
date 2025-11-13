package com.tech.thermography.domain;

import java.util.UUID;

public class InspectionRouteTestSamples {

    public static InspectionRoute getInspectionRouteSample1() {
        return new InspectionRoute()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .title("title1")
            .description("description1")
            .planNote("planNote1");
    }

    public static InspectionRoute getInspectionRouteSample2() {
        return new InspectionRoute()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .title("title2")
            .description("description2")
            .planNote("planNote2");
    }

    public static InspectionRoute getInspectionRouteRandomSampleGenerator() {
        return new InspectionRoute()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .planNote(UUID.randomUUID().toString());
    }
}
