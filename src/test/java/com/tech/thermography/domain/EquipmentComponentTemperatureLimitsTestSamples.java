package com.tech.thermography.domain;

import java.util.UUID;

public class EquipmentComponentTemperatureLimitsTestSamples {

    public static EquipmentComponentTemperatureLimits getEquipmentComponentTemperatureLimitsSample1() {
        return new EquipmentComponentTemperatureLimits()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .normal("normal1")
            .lowRisk("lowRisk1")
            .mediumRisk("mediumRisk1")
            .highRisk("highRisk1")
            .imminentHighRisk("imminentHighRisk1");
    }

    public static EquipmentComponentTemperatureLimits getEquipmentComponentTemperatureLimitsSample2() {
        return new EquipmentComponentTemperatureLimits()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .normal("normal2")
            .lowRisk("lowRisk2")
            .mediumRisk("mediumRisk2")
            .highRisk("highRisk2")
            .imminentHighRisk("imminentHighRisk2");
    }

    public static EquipmentComponentTemperatureLimits getEquipmentComponentTemperatureLimitsRandomSampleGenerator() {
        return new EquipmentComponentTemperatureLimits()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .normal(UUID.randomUUID().toString())
            .lowRisk(UUID.randomUUID().toString())
            .mediumRisk(UUID.randomUUID().toString())
            .highRisk(UUID.randomUUID().toString())
            .imminentHighRisk(UUID.randomUUID().toString());
    }
}
