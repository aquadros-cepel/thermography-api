package com.tech.thermography.domain;

import java.util.UUID;

public class ROITestSamples {

    public static ROI getROISample1() {
        return new ROI().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).type("type1").label("label1");
    }

    public static ROI getROISample2() {
        return new ROI().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).type("type2").label("label2");
    }

    public static ROI getROIRandomSampleGenerator() {
        return new ROI().id(UUID.randomUUID()).type(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
