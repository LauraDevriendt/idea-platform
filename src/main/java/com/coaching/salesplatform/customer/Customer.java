package com.coaching.salesplatform.customer;

import com.coaching.salesplatform.sales.Sale;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
public
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @ManyToMany
    @JoinTable(name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private Set<Address> addresses;


    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;
}
