package com.tech.thermography.domain;

import java.util.UUID;

public class EquipmentTestSamples {

    public static Equipment getEquipmentSample1() {
        return new Equipment()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .title("title1")
            .description("description1")
            .manufacturer("manufacturer1")
            .model("model1")
            .serialNumber("serialNumber1");
    }

    public static Equipment getEquipmentSample2() {
        return new Equipment()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .title("title2")
            .description("description2")
            .manufacturer("manufacturer2")
            .model("model2")
            .serialNumber("serialNumber2");
    }

    public static Equipment getEquipmentRandomSampleGenerator() {
        return new Equipment()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .manufacturer(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .serialNumber(UUID.randomUUID().toString());
    }
}
