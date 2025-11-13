package com.tech.thermography.domain;

import java.util.UUID;

public class CompanyTestSamples {

    public static Company getCompanySample1() {
        return new Company()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .title("title1")
            .description("description1")
            .address("address1")
            .primaryPhoneNumber("primaryPhoneNumber1")
            .secondaryPhoneNumber("secondaryPhoneNumber1")
            .taxIdNumber("taxIdNumber1");
    }

    public static Company getCompanySample2() {
        return new Company()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .title("title2")
            .description("description2")
            .address("address2")
            .primaryPhoneNumber("primaryPhoneNumber2")
            .secondaryPhoneNumber("secondaryPhoneNumber2")
            .taxIdNumber("taxIdNumber2");
    }

    public static Company getCompanyRandomSampleGenerator() {
        return new Company()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .primaryPhoneNumber(UUID.randomUUID().toString())
            .secondaryPhoneNumber(UUID.randomUUID().toString())
            .taxIdNumber(UUID.randomUUID().toString());
    }
}
