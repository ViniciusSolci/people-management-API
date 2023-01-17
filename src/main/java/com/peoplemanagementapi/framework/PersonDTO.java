package com.peoplemanagementapi.framework;

import com.peoplemanagementapi.entiy.Address;
import lombok.Value;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Value
public class PersonDTO {
    String name;
    LocalDate birthDate;
    Set<Address> addresses = new HashSet<>();
}
