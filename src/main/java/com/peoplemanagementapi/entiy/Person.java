package com.peoplemanagementapi.entiy;

import com.peoplemanagementapi.framework.PersonDTO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "person")
@EntityListeners(AuditingEntityListener.class)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "personId")
    @ToString.Exclude
    private Set<Address> addresses = new HashSet<>();

    public Person(PersonDTO personDTO) {
        this.setName(personDTO.getName());
        this.setBirthDate(personDTO.getBirthDate());
        this.setAddresses(personDTO.getAddresses());
    }

    public void updateEntityFromDTO(PersonDTO personDTO) {
        if (!this.getName().equalsIgnoreCase(personDTO.getName())){
            this.setName(personDTO.getName());
        }
        if (!this.getBirthDate().equals(personDTO.getBirthDate())){
            this.setBirthDate(personDTO.getBirthDate());
        }
        if (!this.getAddresses().equals(personDTO.getAddresses())){
            this.setAddresses(personDTO.getAddresses());
        }
    }
}
