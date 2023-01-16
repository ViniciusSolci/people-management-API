package com.peoplemanagementapi.framework;

import lombok.Value;

@Value
public class AddressDTO {
    String streetName;
    String streetNumber;
    String zipCode;
    String cityName;
    String stateName;
}
