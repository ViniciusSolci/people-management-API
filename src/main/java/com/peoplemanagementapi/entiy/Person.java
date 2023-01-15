package com.peoplemanagementapi.entiy;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Person {
    private String name;

    private Date birthDate;

    private List<Address> addressList;

    @Data
    public static class Address {
        private String streetName;

        private long streetNumber;

        private String zipCode;

        private String cityName;

        private String stateName;

        private boolean mainAddress;
    }
}
