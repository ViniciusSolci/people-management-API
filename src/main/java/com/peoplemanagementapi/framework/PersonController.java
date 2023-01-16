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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Person updatePerson(@Valid @RequestBody PersonDTO personDTO, @PathVariable(value = "id") long personId) {
        return personUseCase.updatePerson(personDTO, personId);
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable(value = "id") long personId) {
        return personUseCase.getPersonById(personId);
    }

    @GetMapping("")
    public Slice<Person> getAllPeople(@RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            return personUseCase.getAllPeople(pageNumber.get(), pageSize.get());
        } else return personUseCase.getAllPeople();
    }

    @PostMapping("/{id}/address")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPersonAddress(@Valid @RequestBody PersonDTO personDTO) {
        return personUseCase.createPerson(personDTO);
    }

    @GetMapping("/{id}/address")
    public Set<Address> getAllAddressForPerson(@PathVariable(value = "id") long personId) {
        return personUseCase.getAllAddress(personId);
    }

    @PutMapping("/{id}/address/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePersonMainAddress(@PathVariable(value = "id") long personId, @PathVariable(value = "id") long addressId) {
        personUseCase.updateMainAddress(personId, addressId);
    }
}
