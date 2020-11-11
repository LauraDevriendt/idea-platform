package com.coaching.salesplatform.customer;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "addresses")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "id",
        scope     = Long.class)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STREETNAME")
    private String streetName;

    @Column(name = "CITY")
    private String city;

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "ZIPCODE")
    private String zipcode;

    @ManyToMany(mappedBy = "addresses")
    private List<Customer> customers;
}
