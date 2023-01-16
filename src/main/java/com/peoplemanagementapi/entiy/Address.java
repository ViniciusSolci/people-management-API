package com.peoplemanagementapi.entiy;

import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private long streetNumber;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "main_address")
    private boolean mainAddress;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

}
