package com.tech.thermography.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class InspectionRouteGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static InspectionRouteGroup getInspectionRouteGroupSample1() {
        return new InspectionRouteGroup()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .description("description1")
            .orderIndex(1);
    }

    public static InspectionRouteGroup getInspectionRouteGroupSample2() {
        return new InspectionRouteGroup()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .description("description2")
            .orderIndex(2);
    }

    public static InspectionRouteGroup getInspectionRouteGroupRandomSampleGenerator() {
        return new InspectionRouteGroup()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .orderIndex(intCount.incrementAndGet());
    }
}
