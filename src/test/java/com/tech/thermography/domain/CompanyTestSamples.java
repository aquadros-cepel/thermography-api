package com.tech.thermography.domain;

import java.util.UUID;

public class CompanyTestSamples {

    public static Company getCompanySample1() {
        return new Company()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .description("description1")
            .address("address1")
            .zipCode("zipCode1")
            .street("street1")
            .number("number1")
            .complement("complement1")
            .neighborhood("neighborhood1")
            .city("city1")
            .state("state1")
            .country("country1")
            .primaryPhoneNumber("primaryPhoneNumber1")
            .secondaryPhoneNumber("secondaryPhoneNumber1")
            .taxIdNumber("taxIdNumber1");
    }

    public static Company getCompanySample2() {
        return new Company()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .description("description2")
            .address("address2")
            .zipCode("zipCode2")
            .street("street2")
            .number("number2")
            .complement("complement2")
            .neighborhood("neighborhood2")
            .city("city2")
            .state("state2")
            .country("country2")
            .primaryPhoneNumber("primaryPhoneNumber2")
            .secondaryPhoneNumber("secondaryPhoneNumber2")
            .taxIdNumber("taxIdNumber2");
    }

    public static Company getCompanyRandomSampleGenerator() {
        return new Company()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .zipCode(UUID.randomUUID().toString())
            .street(UUID.randomUUID().toString())
            .number(UUID.randomUUID().toString())
            .complement(UUID.randomUUID().toString())
            .neighborhood(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .state(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .primaryPhoneNumber(UUID.randomUUID().toString())
            .secondaryPhoneNumber(UUID.randomUUID().toString())
            .taxIdNumber(UUID.randomUUID().toString());
    }
}
