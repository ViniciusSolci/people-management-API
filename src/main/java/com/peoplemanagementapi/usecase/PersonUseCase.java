package com.peoplemanagementapi.usecase;

import com.peoplemanagementapi.entiy.Address;
import com.peoplemanagementapi.entiy.Person;
import com.peoplemanagementapi.framework.PersonDTO;
import com.peoplemanagementapi.framework.exception.NotFoundException;
import com.peoplemanagementapi.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PersonUseCase {

    private final @NonNull PersonRepository personRepository;

    public Person createPerson(PersonDTO personDTO) {
        Person person = new Person(personDTO);
        return personRepository.save(person);
    }

    public Person updatePerson(@Valid PersonDTO personDTO, long personId) {
        Person person = getPersonById(personId);
        person.updateEntityFromDTO(personDTO);
        return personRepository.save(person);
    }

    public Person getPersonById(long personId) {
        try {
            return personRepository.findById(personId).orElseThrow();
        } catch (NoSuchElementException exception) {
            throw new NotFoundException("Person with id: " + personId + " does not exists");
        }
    }

    public Slice<Person> getAllPeople(int pageNumber, int pageSize) {
        Pageable sortedById = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        return personRepository.findAll(sortedById);
    }

    public Slice<Person> getAllPeople() {
        return personRepository.findAll(Pageable.unpaged());
    }

    public Set<Address> getAllAddress(long personId) {
        Person person = getPersonById(personId);
        return person.getAddresses();

    }

    public void updateMainAddress(long personId, long addressId) {
        Person person = getPersonById(personId);
        person.setAddresses(person.getAddresses().stream().filter(address -> Boolean.TRUE.equals(address.isMainAddress())).peek(address -> address.setMainAddress(false)).collect(Collectors.toSet()));
        person.setAddresses(person.getAddresses().stream().filter(address -> Long.valueOf(address.getId()).equals(addressId)).peek(address -> address.setMainAddress(true)).collect(Collectors.toSet()));

    }
}
