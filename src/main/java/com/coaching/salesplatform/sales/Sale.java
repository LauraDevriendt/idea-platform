package com.coaching.salesplatform.sales;


import com.coaching.salesplatform.customer.Customer;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name="sales")
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @Column(name="DATE")
    private Date date;

    @OneToMany(mappedBy = "sale")
    private Set<SaleLine> saleLines;
}

