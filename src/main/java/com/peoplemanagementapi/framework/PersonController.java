package com.peoplemanagementapi.framework;

import com.peoplemanagementapi.entiy.Address;
import com.peoplemanagementapi.entiy.Person;
import com.peoplemanagementapi.usecase.PersonUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/person")
public class PersonController {

    private final @NonNull PersonUseCase personUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@Valid @RequestBody PersonDTO personDTO) {
        return personUseCase.createPerson(personDTO);
    }

    @PutMapping("/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public Person updatePerson(@Valid @RequestBody PersonDTO personDTO, @PathVariable(value = "personId") long personId) {
        return personUseCase.updatePerson(personDTO, personId);
    }

    @GetMapping("/{personId}")
    public Person getPersonById(@PathVariable(value = "personId") long personId) {
        return personUseCase.getPersonById(personId);
    }

    @GetMapping("")
    public Slice<Person> getAllPeople(@RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            return personUseCase.getAllPeople(pageNumber.get(), pageSize.get());
        } else return personUseCase.getAllPeople();
    }

    @PostMapping("/{personId}/address")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPersonAddress(@Valid @RequestBody AddressDTO addressDTO, @PathVariable(value = "personId") long personId) {
        personUseCase.createPersonAddress(addressDTO, personId);
    }

    @GetMapping("/{personId}/address")
    public Set<Address> getAllAddressForPerson(@PathVariable(value = "personId") long personId) {
        return personUseCase.getAllAddress(personId);
    }

    @PutMapping("/{personId}/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePersonMainAddress(@PathVariable(value = "personId") long personId, @PathVariable(value = "addressId") long addressId) {
        personUseCase.updateMainAddress(personId, addressId);
    }
}
