package com.tech.thermography.domain;

import java.util.UUID;

public class ThermogramTestSamples {

    public static Thermogram getThermogramSample1() {
        return new Thermogram()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .imagePath("imagePath1")
            .audioPath("audioPath1")
            .imageRefPath("imageRefPath1")
            .cameraLens("cameraLens1")
            .cameraModel("cameraModel1")
            .imageResolution("imageResolution1")
            .selectedRoiId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Thermogram getThermogramSample2() {
        return new Thermogram()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .imagePath("imagePath2")
            .audioPath("audioPath2")
            .imageRefPath("imageRefPath2")
            .cameraLens("cameraLens2")
            .cameraModel("cameraModel2")
            .imageResolution("imageResolution2")
            .selectedRoiId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Thermogram getThermogramRandomSampleGenerator() {
        return new Thermogram()
            .id(UUID.randomUUID())
            .imagePath(UUID.randomUUID().toString())
            .audioPath(UUID.randomUUID().toString())
            .imageRefPath(UUID.randomUUID().toString())
            .cameraLens(UUID.randomUUID().toString())
            .cameraModel(UUID.randomUUID().toString())
            .imageResolution(UUID.randomUUID().toString())
            .selectedRoiId(UUID.randomUUID());
    }
}
