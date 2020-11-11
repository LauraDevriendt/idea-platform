package com.coaching.salesplatform.products;

import com.coaching.salesplatform.sales.SaleLine;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
public
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Integer price;

    @ManyToMany(mappedBy = "products")
    private Set<SaleLine> salesLines;
}
