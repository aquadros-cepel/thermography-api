package com.tech.thermography.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class InspectionRouteGroupEquipmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static InspectionRouteGroupEquipment getInspectionRouteGroupEquipmentSample1() {
        return new InspectionRouteGroupEquipment().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).orderIndex(1);
    }

    public static InspectionRouteGroupEquipment getInspectionRouteGroupEquipmentSample2() {
        return new InspectionRouteGroupEquipment().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).orderIndex(2);
    }

    public static InspectionRouteGroupEquipment getInspectionRouteGroupEquipmentRandomSampleGenerator() {
        return new InspectionRouteGroupEquipment().id(UUID.randomUUID()).orderIndex(intCount.incrementAndGet());
    }
}
