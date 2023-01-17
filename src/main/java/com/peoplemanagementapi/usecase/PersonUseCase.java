package com.peoplemanagementapi.usecase;

import com.peoplemanagementapi.entiy.Address;
import com.peoplemanagementapi.entiy.Person;
import com.peoplemanagementapi.framework.AddressDTO;
import com.peoplemanagementapi.framework.PersonDTO;
import com.peoplemanagementapi.framework.exception.NotFoundException;
import com.peoplemanagementapi.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

import static com.peoplemanagementapi.framework.exception.ErrorMessage.PERSON_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class PersonUseCase {

    @NonNull
    private final PersonRepository personRepository;

    public Person createPerson(PersonDTO personDTO) {
        Person person = new Person(personDTO);
        return personRepository.save(person);
    }

    public Person updatePerson(PersonDTO personDTO, long personId) {
        Person person = getPersonById(personId);
        person.updateEntityFromDTO(personDTO);
        return personRepository.save(person);
    }

    public Person getPersonById(long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException(String.format(PERSON_NOT_FOUND, personId)));
    }

    public Page<Person> getAllPeople(int pageNumber, int pageSize) {
        Pageable sortedById = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        return personRepository.findAll(sortedById);
    }

    public Set<Address> getAllAddress(long personId) {
        Person person = getPersonById(personId);
        return person.getAddresses();
    }

    public void updateMainAddress(long personId, long addressId) {
        Person person = getPersonById(personId);
        for (Address address : person.getAddresses()) {
            if (address.isMainAddress()) {
                address.setMainAddress(false);
            }
            if (address.getId() == addressId) {
                address.setMainAddress(true);
            }
        }
        personRepository.save(person);
    }

    @Transactional
    public void createPersonAddress(AddressDTO addressDTO, long personId) {
        Person person = getPersonById(personId);
        Address address = new Address(addressDTO);
        person.getAddresses().add(address);
        personRepository.save(person);
    }
}
