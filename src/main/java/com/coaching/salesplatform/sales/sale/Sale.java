package com.coaching.salesplatform.sales.sale;


import com.coaching.salesplatform.customers.customers.Customer;
import com.coaching.salesplatform.sales.saleLine.SaleLine;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="sales")
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("sales")
    private Customer customer;

    @Column(name="DATE", nullable = false)
    private Date date;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    @Column(nullable = false)
    @JsonIgnoreProperties("sale")
    private List<SaleLine> saleLines;
}

