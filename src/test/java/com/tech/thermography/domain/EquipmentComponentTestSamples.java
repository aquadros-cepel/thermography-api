package com.tech.thermography.domain;

import java.util.UUID;

public class EquipmentComponentTestSamples {

    public static EquipmentComponent getEquipmentComponentSample1() {
        return new EquipmentComponent()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .title("title1")
            .description("description1");
    }

    public static EquipmentComponent getEquipmentComponentSample2() {
        return new EquipmentComponent()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .title("title2")
            .description("description2");
    }

    public static EquipmentComponent getEquipmentComponentRandomSampleGenerator() {
        return new EquipmentComponent()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
