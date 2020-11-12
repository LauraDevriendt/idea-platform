package com.coaching.salesplatform.customers.customers;

import com.coaching.salesplatform.customers.address.Address;
import com.coaching.salesplatform.sales.sale.Sale;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
public
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRSTNAME",  nullable = false)
    @NotNull
    @NotBlank
    private String firstName;

    @Column(name = "LASTNAME",  nullable = false)
    @NotNull
    @NotBlank
    private String lastName;

    // BIDIRECTIONAL RELATIONSHIP => CHECK IDENTITY INFO TO AVOID STACKOVERFLOW AND USE Lists instead of sets
    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties("customers")
    private List<Address> addresses;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("customer")
    private List<Sale> sales;
}
