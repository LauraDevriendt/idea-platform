package com.coaching.salesplatform.customers.address;

import com.coaching.salesplatform.customers.customers.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "addresses")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STREETNAME", nullable = false)
    @NotNull
    @NotBlank
    private String streetName;

    @Column(name = "CITY", nullable = false)
    @NotNull
    @NotBlank
    private String city;

    @Column(name = "NUMBER", nullable = false)
    @NotNull
    private Integer number;

    @Column(name = "ZIPCODE", nullable = false)
    @NotNull
    @NotBlank
    private String zipcode;

    @ManyToMany(mappedBy = "addresses", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("addresses")
    private List<Customer> customers;

}
