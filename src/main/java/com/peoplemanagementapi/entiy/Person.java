package com.peoplemanagementapi.entiy;

import com.peoplemanagementapi.framework.PersonDTO;
import javax.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
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
    private Date birthDate;

    @OneToMany(mappedBy = "person", orphanRemoval = true)
    @ToString.Exclude
    private Set<Address> addresses = new LinkedHashSet<>();

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
