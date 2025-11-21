package com.tech.thermography.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class InspectionRouteTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static InspectionRoute getInspectionRouteSample1() {
        return new InspectionRoute()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .description("description1")
            .maintenancePlan("maintenancePlan1")
            .duration(1);
    }

    public static InspectionRoute getInspectionRouteSample2() {
        return new InspectionRoute()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .description("description2")
            .maintenancePlan("maintenancePlan2")
            .duration(2);
    }

    public static InspectionRoute getInspectionRouteRandomSampleGenerator() {
        return new InspectionRoute()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .maintenancePlan(UUID.randomUUID().toString())
            .duration(intCount.incrementAndGet());
    }
}
