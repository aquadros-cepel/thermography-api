package com.tech.thermography.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ThermographicInspectionRecordTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ThermographicInspectionRecord getThermographicInspectionRecordSample1() {
        return new ThermographicInspectionRecord()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .serviceOrder("serviceOrder1")
            .analysisDescription("analysisDescription1")
            .periodicity(1)
            .recommendations("recommendations1");
    }

    public static ThermographicInspectionRecord getThermographicInspectionRecordSample2() {
        return new ThermographicInspectionRecord()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .serviceOrder("serviceOrder2")
            .analysisDescription("analysisDescription2")
            .periodicity(2)
            .recommendations("recommendations2");
    }

    public static ThermographicInspectionRecord getThermographicInspectionRecordRandomSampleGenerator() {
        return new ThermographicInspectionRecord()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .serviceOrder(UUID.randomUUID().toString())
            .analysisDescription(UUID.randomUUID().toString())
            .periodicity(intCount.incrementAndGet())
            .recommendations(UUID.randomUUID().toString());
    }
}
