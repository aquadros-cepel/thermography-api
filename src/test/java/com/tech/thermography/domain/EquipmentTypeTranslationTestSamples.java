package com.tech.thermography.domain;

import java.util.UUID;

public class EquipmentTypeTranslationTestSamples {

    public static EquipmentTypeTranslation getEquipmentTypeTranslationSample1() {
        return new EquipmentTypeTranslation()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .language("language1")
            .name("name1");
    }

    public static EquipmentTypeTranslation getEquipmentTypeTranslationSample2() {
        return new EquipmentTypeTranslation()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .language("language2")
            .name("name2");
    }

    public static EquipmentTypeTranslation getEquipmentTypeTranslationRandomSampleGenerator() {
        return new EquipmentTypeTranslation()
            .id(UUID.randomUUID())
            .language(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString());
    }
}
