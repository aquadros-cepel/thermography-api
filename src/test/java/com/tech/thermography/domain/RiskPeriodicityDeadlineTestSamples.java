package com.tech.thermography.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RiskPeriodicityDeadlineTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RiskPeriodicityDeadline getRiskPeriodicityDeadlineSample1() {
        return new RiskPeriodicityDeadline()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .deadline(1)
            .periodicity(1)
            .recommendations("recommendations1");
    }

    public static RiskPeriodicityDeadline getRiskPeriodicityDeadlineSample2() {
        return new RiskPeriodicityDeadline()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .deadline(2)
            .periodicity(2)
            .recommendations("recommendations2");
    }

    public static RiskPeriodicityDeadline getRiskPeriodicityDeadlineRandomSampleGenerator() {
        return new RiskPeriodicityDeadline()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .deadline(intCount.incrementAndGet())
            .periodicity(intCount.incrementAndGet())
            .recommendations(UUID.randomUUID().toString());
    }
}
