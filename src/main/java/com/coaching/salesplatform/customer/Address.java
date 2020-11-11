package com.coaching.salesplatform.customer;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "addresses")
@Data
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
    private Set<Customer> customers;
}
