package com.tech.thermography.domain;

import java.util.UUID;

public class InspectionRouteRecordTestSamples {

    public static InspectionRouteRecord getInspectionRouteRecordSample1() {
        return new InspectionRouteRecord()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .description("description1")
            .maintenanceDocument("maintenanceDocument1");
    }

    public static InspectionRouteRecord getInspectionRouteRecordSample2() {
        return new InspectionRouteRecord()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .description("description2")
            .maintenanceDocument("maintenanceDocument2");
    }

    public static InspectionRouteRecord getInspectionRouteRecordRandomSampleGenerator() {
        return new InspectionRouteRecord()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .maintenanceDocument(UUID.randomUUID().toString());
    }
}
