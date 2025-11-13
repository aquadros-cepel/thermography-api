package com.tech.thermography.domain;

import java.util.UUID;

public class UserInfoTestSamples {

    public static UserInfo getUserInfoSample1() {
        return new UserInfo().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).position("position1").phoneNumber("phoneNumber1");
    }

    public static UserInfo getUserInfoSample2() {
        return new UserInfo().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).position("position2").phoneNumber("phoneNumber2");
    }

    public static UserInfo getUserInfoRandomSampleGenerator() {
        return new UserInfo().id(UUID.randomUUID()).position(UUID.randomUUID().toString()).phoneNumber(UUID.randomUUID().toString());
    }
}
