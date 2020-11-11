package com.coaching.salesplatform.customer;

import com.coaching.salesplatform.sales.Sale;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "id",
        scope     = Long.class)
public
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRSTNAME")
    @NotNull
    @NotBlank
    private String firstName;

    @Column(name = "LASTNAME")
    @NotNull
    @NotBlank
    private String lastName;


    // BIDIRECTIONAL RELATIONSHIP => CHECK IDENTITY INFO TO AVOID STACKOVERFLOW AND USE Lists instead of sets
    @ManyToMany
    @JoinTable(name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses;

    /*
    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;
*/

}
